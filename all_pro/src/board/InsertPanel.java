package board;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

public class InsertPanel extends JPanel {
	private JDBC jdbc;
	private CRUD crud;
    private JTextField board_no;     // 게시물 번호
    private JTextField mem_id;  	 // 작성자
    private JTextField board_name;   // 게시글 제목
    private JTextField board_date;   // 작성일
    private JTextArea board_write;   // 게시글 내용
    private DefaultTableModel model;
    private JPanel insertPanel;		// 현재 InsertPanel 참조
    private Board boardPanel;			// Board2 참조

    public InsertPanel(JDBC jdbc, CRUD crud) { 
    	this.jdbc = jdbc;
    	this.crud = crud;
    	
        setLayout(null);

        // 게시글 내용
        board_write = new JTextArea();
        board_write.setBounds(150, 100, 900, 400);
        add(board_write);

        // 게시물 번호
        board_no = new JTextField();
        board_no.setBounds(150, 30, 96, 21);
        board_no.setEditable(false); // 수정 불가
        add(board_no);
        board_no.setText("번호");;

        // 작성자
        mem_id = new JTextField();
        mem_id.setBounds(291, 30, 96, 21);
        mem_id.setEditable(false); // 수정 불가
        add(mem_id);
        mem_id.setText("ID");;

        // 게시글 제목
        board_name = new JTextField();
        board_name.setBounds(150, 61, 606, 21);
        add(board_name);
        board_name.setColumns(10);

        // 작성일
        board_date = new JTextField();
        board_date.setBounds(422, 30, 96, 21);
        board_date.setEditable(false); // 수정 불가
        add(board_date);
        board_date.setText("날짜");;

        // 목록 버튼
        JButton boardButton = new JButton("목록");
        boardButton.setBounds(959, 60, 91, 23);
        add(boardButton);
        
        // 글 등록 버튼
        JButton insertButton = new JButton("글 등록");
        insertButton.setBounds(527, 549, 150, 59);
        add(insertButton);

        
        // 목록 버튼 이벤트 처리
        boardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	insertPanel.setVisible(false); // InsertPanel 숨김
                boardPanel.setVisible(true);  // Board2 표시
            }
        });        
        
        
        // 글 등록 버튼 이벤트 처리
        insertButton.addActionListener(e -> {
        	jdbc.connect();
            String name = board_name.getText();
            String write = board_write.getText();
            crud.insertBoard(name, write);
            
			board_name.setText("");
			board_write.setText("");
            jdbc.close(null, null, null);
            
			insertPanel.setVisible(false); // InsertPanel 숨김
            boardPanel.setVisible(true);  // Board2 표시
		});
    }
    
    
    // Board2와 InsertPanel 참조 설정 메서드
    public void setInsertPanel(Board boardPanel, JPanel insertPanel) {
        this.boardPanel = boardPanel;
        this.insertPanel = insertPanel;
    }
}
