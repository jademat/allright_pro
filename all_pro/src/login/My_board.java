package login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

public class My_board extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel mbContainer;
    private JTable mbtable;
    private DefaultTableModel mbmodel;
    private JDBC jdbc;
    private String mem_id;

    public My_board(JDBC jdbc, String mem_id) {
        this.jdbc = jdbc; // JDBC 객체 초기화
        this.mem_id = mem_id; // 사용자 ID 저장

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

        // 데이터 로드
        loadData();
    }

    private void loadData() {
        try {
            // JDBC 연결
            jdbc.connect();

            // SQL 쿼리 작성
            jdbc.sql = "SELECT b.boa_no, b.boa_name, m.mem_id, m.mem_rank, b.boa_like, b.boa_date "
                    + "FROM board b "
                    + "JOIN member m ON b.mem_id = m.mem_id "
                    + "WHERE m.mem_id = ? "
                    + "ORDER BY b.boa_no DESC";
           jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
           jdbc.pstmt.setString(1, mem_id);
            // 쿼리 실행 및 결과 처리
            jdbc.res = jdbc.pstmt.executeQuery();
            while (jdbc.res.next()) {
                int number = jdbc.res.getInt("boa_no"); // 번호
                String title = jdbc.res.getString("boa_name"); // 제목
                String id = jdbc.res.getString("mem_id"); // 아이디
                String rank = jdbc.res.getString("mem_rank"); // 등급
                int likes = jdbc.res.getInt("boa_like"); // 좋아요 수
                String date = jdbc.res.getString("boa_date"); // 작성일자

                // 데이터를 테이블 모델에 추가
                mbmodel.addRow(new Object[]{number, title, id, rank, likes, date});
            }
            jdbc.res.close(); // ResultSet 닫기
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            jdbc.close(jdbc.pstmt, jdbc.res);
        }
    }
}
