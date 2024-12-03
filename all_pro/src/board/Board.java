package board;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;
import board.CRUD;

import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Board extends JPanel {
	private JDBC jdbc;
	private CRUD crud;
    private JTable table;
    private DetailPanel detailPanel; // DetailPanel 참조
    private InsertPanel insertPanel; // InsertPanel 참조
    private JPanel boardPanel;       // 현재 Board2 패널 참조
    private DefaultTableModel model;
    private JTextField textField;

    public Board(JDBC jdbc, CRUD crud) {
    	this.jdbc = jdbc;
    	this.crud = crud;
    	
        // 테이블 초기화
        String[] header = {"번호", "제목", "아이디", "등급", "좋아요수", "작성일자"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);
        table.setBackground(new Color(255, 255, 255));

        // 레이아웃 설정
        setLayout(null);
        setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBounds(150, 100, 900, 450);
        add(scrollPane);
        
        // refreshButton(새로고침) 버튼 
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setForeground(new Color(238, 57, 8));
        refreshButton.setFont(new Font("굴림", Font.BOLD, 14));
        refreshButton.setBackground(new Color(0, 0, 0));
        refreshButton.setBounds(150, 585, 112, 35);
        add(refreshButton);
        
        // insertButton(글작성) 버튼
        JButton insertButton = new JButton("WRITE");
        insertButton.setFont(new Font("굴림", Font.BOLD, 14));
        insertButton.setForeground(new Color(238, 57, 8));
        insertButton.setBackground(new Color(0, 0, 0));
        insertButton.setBounds(938, 585, 112, 35);
        add(insertButton);
        
        // "BOARD" 라벨
        JLabel lblNewLabel = new JLabel("BOARD");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel.setForeground(new Color(238, 57, 8));
        lblNewLabel.setBounds(150, 21, 419, 57);
        add(lblNewLabel);
        
        // 정렬 comboBox
        String[] sort = {"----- 기본 -----", "번호 오름차순", "인기글순", "등급순"};
        JComboBox<String> comboBox = new JComboBox<String>(sort);
        comboBox.setBounds(917, 62, 133, 27);
        add(comboBox);
        
        // 검색(search) textField, button
        textField = new JTextField();
        textField.setBounds(371, 590, 324, 25);
        add(textField);
       
        JButton searchBtn = new JButton("검색");
        searchBtn.setBounds(701, 587, 99, 28);
        add(searchBtn);
        
        // refreshButton(새로고침) 이벤트 처리
        refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jdbc.connect();
		        model.setRowCount(0);
		        crud.loadTable(model);
		        jdbc.close(null, null, null);
			}
		});
        
        
        // insertButton(글작성) 이벤트 처리
        insertButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.setVisible(false); // Board2 숨김
                insertPanel.setVisible(true);  // InsertPanel 표시				
			}
		});
        
        // comboBox(정렬) 이벤트 처리
        comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> selectComboBox =(JComboBox<String>)e.getSource();
				//System.out.println(selectComboBox.getSelectedIndex()); 
				switch (selectComboBox.getSelectedIndex()) {
					// 기본(default) 정렬
					case 0 :
						jdbc.connect();
				        model.setRowCount(0);
				        crud.loadTable(model);
				        jdbc.close(null, null, null);
				        break;
				    // 번호 오름차순 정렬
					case 1 :
						jdbc.connect();
				        model.setRowCount(0);
				        crud.loadTable2(model);
				        jdbc.close(null, null, null);
				        break;
					// 인기글순 정렬
					case 2 :
						jdbc.connect();
				        model.setRowCount(0);
				        crud.loadTable3(model);
				        jdbc.close(null, null, null);
				        break;
					// 등급순 정렬
					case 3 :
						jdbc.connect();
				        model.setRowCount(0);
				        crud.loadTable4(model);
				        jdbc.close(null, null, null);
				        break;
				} // switch 문 end
			}
		});
        
        
        // searchBtn(검색) 이벤트 처리
        searchBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});

        // 첫 화면 데이터 로드        
        jdbc.connect();
        model.setRowCount(0);
        List<Object[]> list = crud.loadTable(model);
        jdbc.close(null, null, null);        

        // 레코드 선택 이벤트
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                int selectedRow = table.getSelectedRow(); // 선택된 행
                if (selectedRow != -1) {
                	
                    // JTable 모델로부터 데이터 가져오기
                	Object[] rowData = list.get(selectedRow);
                    int boa_no = (Integer) rowData[0];
                    String boa_name = (String) rowData[1];
                    String boa_write = (String) rowData[2];
                    int boa_notice = (Integer) rowData[3];
                    int boa_like = (Integer) rowData[4];
                    String boa_date = (String) rowData[5];
                    String mem_id = (String) rowData[6];
                    int mem_rank = (Integer) rowData[7];

                    // DetailPanel에 데이터 전달
                    detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

                    // 화면 전환
                    boardPanel.setVisible(false); // Board2 숨김
                    detailPanel.setVisible(true); // DetailPanel 표시                	
                }
            }
        });        
    }

    
    // DetailPanel과 Board2 참조 설정 메서드
    public void setBoardPanel(DetailPanel detailPanel, InsertPanel insertPanel, JPanel boardPanel) {
        this.detailPanel = detailPanel;
        this.insertPanel = insertPanel;
        this.boardPanel = boardPanel;
    }
}
