package login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import board.CRUD;
import board.DetailPanel;
import board.InsertPanel;
import board.ViewPanel;
import jdbc.JDBC;

public class My_board extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel mbContainer;
    private JTable mbtable;
    private DefaultTableModel mbmodel;
    private JDBC jdbc;
    private CRUD crud;
    private DetailPanel detailPanel; // DetailPanel 객체 참조
    private My_board my_boardPanel; // 현재 Board2 패널 참조
    private String mem_id;
    
    int boa_no;
	String boa_name;
	String boa_write;
	int boa_like;
	String boa_date;
	int mem_rank;
    
    public My_board() {
		// TODO Auto-generated constructor stub
	}

    public My_board(JDBC jdbc, String mem_id) {
        this.jdbc = jdbc; // JDBC 객체 초기화
        this.mem_id = mem_id; // 사용자 ID 저장
        
        // 기본 패널 초기화
    	this.detailPanel = new DetailPanel(jdbc, crud);
        this.my_boardPanel = this;

        // JFrame 기본 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setTitle("My Board");

        // Content Pane 초기화
        mbContainer = new JPanel();
        mbContainer.setBorder(new EmptyBorder(5, 5, 5, 5));
        mbContainer.setLayout(null);
        setContentPane(mbContainer);

        // 라벨 추가
        JLabel mbLabel = new JLabel("MY BOARD");
        mbLabel.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 25));
        mbLabel.setBounds(307, 20, 153, 42);
        mbContainer.add(mbLabel);

        // 테이블 데이터 및 열 제목 설정
        String[] columnNames = {"번호", "제목", "아이디", "등급", "좋아요 수", "작성일자"};
        mbmodel = new DefaultTableModel(null, columnNames); // 테이블 모델 초기화
        mbtable = new JTable(mbmodel);
        mbtable.setFillsViewportHeight(true);

        // JScrollPane으로 테이블 감싸기
        JScrollPane scrollPane = new JScrollPane(mbtable);
        scrollPane.setBounds(20, 92, 740, 400);
        mbContainer.add(scrollPane);

        // 닫기 버튼 추가
        JButton closeButton = new JButton("CLOSE");
        closeButton.setBounds(315, 502, 105, 39);
        mbContainer.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // DetailPanel 초기화
        detailPanel = new DetailPanel(jdbc, crud); 
        //detailPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        detailPanel.setBounds(0, 0, 786, 563);
        detailPanel.setLayout(null);
        detailPanel.setVisible(false); // 초기에는 숨김

        // DetailPanel을 JFrame에 추가
        //getContentPane().add(detailPanel);

        // 데이터 로드 및 이벤트 추가
        loadData();
        selectRow();
    }

    private List<Object[]> loadData() {
        List<Object[]> dataList = new ArrayList<>();
        try {
            // JDBC 연결
            jdbc.connect();

            // SQL 쿼리 작성
            jdbc.sql = "SELECT b.boa_no, b.boa_name, boa_write, m.mem_id, m.mem_rank, b.boa_like, b.boa_date "
                    + "FROM board b "
                    + "JOIN member m ON b.mem_id = m.mem_id "
                    + "WHERE m.mem_id = ? "
                    + "ORDER BY b.boa_no DESC";
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.pstmt.setString(1, mem_id);

            // 쿼리 실행 및 결과 처리
            jdbc.res = jdbc.pstmt.executeQuery();
            while (jdbc.res.next()) {
                Object[] fullData = {
                    jdbc.res.getInt("boa_no"),
                    jdbc.res.getString("boa_name"),
                    jdbc.res.getString("boa_write"),
                    jdbc.res.getInt("boa_like"),
                    jdbc.res.getString("boa_date").substring(0, 10),
                    jdbc.res.getString("mem_id"),
                    jdbc.res.getInt("mem_rank")
                };
                Object[] tableData = {
                    jdbc.res.getInt("boa_no"),       // 번호
                    jdbc.res.getString("boa_name"), // 제목
                    jdbc.res.getString("mem_id"),   // 아이디
                    jdbc.res.getInt("mem_rank"),    // 등급
                    jdbc.res.getInt("boa_like"),    // 좋아요 수
                    jdbc.res.getString("boa_date").substring(0, 10) // 작성일자
                };

                // JTable 모델에 간단한 데이터 추가
                mbmodel.addRow(tableData);
                // 전체 데이터를 리스트에 추가
                dataList.add(fullData);
            }
            jdbc.res.close(); // ResultSet 닫기
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            jdbc.close(jdbc.pstmt, jdbc.res);
        }
        return dataList; // 조회된 데이터 반환
    }

    // 레코드 선택 메서드
    public void selectRow() {
        // 레코드 선택 시 이벤트 처리
        mbtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 값이 변경되었을 때 처리
                int selectedRow = mbtable.getSelectedRow(); // 선택된 행
                if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
                    jdbc.connect(); // JDBC 연결 초기화
                    List<Object[]> list = loadData(); // 새로 데이터 로드

                    // 새로 로드된 데이터에서 선택된 행에 접근
                    if (selectedRow < list.size()) { // 선택된 행이 유효할 경우에만 처리
                        Object[] rowData = list.get(selectedRow);
                        int boa_no = (Integer) rowData[0];
                        String boa_name = (String) rowData[1];
                        String boa_write = (String) rowData[2];
                        int boa_like = (Integer) rowData[3];
                        String boa_date = (String) rowData[4];
                        String mem_id = (String) rowData[5];
                        int mem_rank = (Integer) rowData[6];

                        // DetailPanel에 데이터 전달
                        detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

                        // 화면 전환
                        //mbContainer.setVisible(false); // My_Board 숨김
                        detailPanel.setVisible(true);  // DetailPanel 표시
                    }
                    jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기
                }
            }
        });
    }
}
