package board;

import javax.swing.*;

import jdbc.JDBC;

import java.awt.*;

public class DetailPanel extends JPanel {
	private JDBC jdbc;
	private CRUD crud;
    private JTextField board_no;  // 게시물 번호
    private JTextField member_id;    // 작성자
    private JTextField member_rank;  // 작성자 랭크
    private JTextField board_name; // 게시글 제목
    private JTextField board_date; // 작성일
    private JTextField board_likes; // 좋아요
    private JTextArea board_write;  // 게시글 내용
    private Board boardPanel;     // Board2 참조
    private JPanel detailPanel;    // 현재 DetailPanel 참조

    public DetailPanel(JDBC jdbc, CRUD crud) {
    	this.jdbc = jdbc;
    	this.crud = crud;
    	
        setLayout(null);

        // 게시물번호(board_no) label, textField
        JLabel lblBoardNo = new JLabel("게시물 번호:");
        lblBoardNo.setBounds(84, 73, 73, 25);
        add(lblBoardNo);
        board_no = new JTextField();
        board_no.setBounds(170, 73, 100, 25);
        board_no.setEditable(false); // 읽기 전용
        add(board_no);

        // 멤버아이디(mem_id) label, textField
        JLabel lblMemId = new JLabel("작성자:");
        lblMemId.setBounds(84, 108, 54, 25);
        add(lblMemId);
        member_id = new JTextField();
        member_id.setBounds(150, 108, 200, 25);
        member_id.setEditable(false); // 읽기 전용
        add(member_id);
        
        // 멤버등급(mem_rank) textField
        member_rank = new JTextField();
        member_rank.setBounds(362, 108, 47, 25);
        member_rank.setEditable(false); // 읽기 전용
		add(member_rank);
        
		// 게시물제목(board_name) label, textField
        JLabel lblBoardName = new JLabel("제목:");
        lblBoardName.setBounds(84, 148, 54, 25);
        add(lblBoardName);
        board_name = new JTextField();
        board_name.setBounds(150, 148, 500, 25);
        add(board_name);

        // 게시물작성일(board_date) label, textField
        JLabel lblBoardDate = new JLabel("작성일:");
        lblBoardDate.setBounds(777, 148, 54, 25);
        add(lblBoardDate);
        board_date = new JTextField();
        board_date.setBounds(843, 148, 200, 25);
        board_date.setEditable(false);
        add(board_date);

        // 게시물좋아요(board_likes) textField
        board_likes = new JTextField();
        board_likes.setBounds(241, 587, 54, 25);
        board_likes.setEditable(false);
        add(board_likes);
        
        // 게시물내용(board_write) label, textField
        JLabel lblBoardWrite = new JLabel("내용:");
        lblBoardWrite.setBounds(84, 195, 54, 25);
        add(lblBoardWrite);
        board_write = new JTextArea();
        board_write.setBounds(147, 195, 900, 355);
        board_write.setLineWrap(true); // 자동 줄 바꿈
        board_write.setWrapStyleWord(true);
        add(board_write);

        // 수정 버튼
        JButton btnModify = new JButton("수정");
        btnModify.setBounds(627, 579, 120, 40);
        add(btnModify);

        // 삭제 버튼
        JButton btnDelete = new JButton("삭제");
        btnDelete.setBounds(777, 579, 120, 40);
        add(btnDelete);

        // 목록 버튼
        JButton btnList = new JButton("목록");
        btnList.setBounds(927, 579, 120, 40);
        add(btnList);
        
        // 좋아요 버튼
        JButton btnlikes = new JButton("좋아요");
        btnlikes.setFont(new Font("굴림", Font.PLAIN, 12));
        btnlikes.setBounds(150, 580, 79, 41);
        add(btnlikes);
        
        // 수정 버튼 이벤트 처리
        btnModify.addActionListener(e -> {
        	jdbc.connect();
            int boardNo = Integer.parseInt(board_no.getText());
            String title = board_name.getText();
            String content = board_write.getText();
            crud.updateBoard(boardNo, title, content);                     
            
            detailPanel.revalidate();
            detailPanel.repaint();
            
            detailPanel.setVisible(false); // DetailPanel 숨김
            boardPanel.setVisible(true);  // Board2 표시         
        });
        
        
        // 삭제 버튼 이벤트 처리
        btnDelete.addActionListener(e -> {
        	jdbc.connect();
            int result = JOptionPane.showConfirmDialog(null, "정말로 삭제 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                int boa_no = Integer.parseInt(board_no.getText());
                crud.deleteBoard(boa_no);

            detailPanel.setVisible(false); // DetailPanel 숨김
            boardPanel.setVisible(true);  // Board2 표시
            }
        });

        
        // 목록 버튼 이벤트 처리
        btnList.addActionListener(e -> {
            detailPanel.setVisible(false); // DetailPanel 숨김
            boardPanel.setVisible(true);  // Board2 표시
        });
        
        
        // 좋아요 버튼 이벤트 처리
        btnlikes.addActionListener(e -> {
        	int boardNo = Integer.parseInt(board_no.getText());
        	int likeCount = crud.updateLikes(boardNo);
        	board_likes.setText(String.valueOf(likeCount));
        	// 좋아요 버튼을 한 번만 클릭되게 하는 로직
        	if (btnlikes == btnlikes) {
        		btnlikes.setEnabled(false);
        	}
        });  
    }

    // 데이터를 설정하는 메서드
    public void setDetails(int boa_no, String boa_name, String boa_write, int boa_like, String boa_date, String mem_id, int mem_rank) {
        board_no.setText(String.valueOf(boa_no));
        board_name.setText(boa_name);
        board_write.setText(boa_write);
        board_date.setText(boa_date);
        board_likes.setText(String.valueOf(boa_like));
        member_id.setText(mem_id);
        member_rank.setText(String.valueOf(mem_rank));
    }

    // Board2와 DetailPanel 참조 설정 메서드
    public void setDetailPanel(Board boardPanel, JPanel detailPanel) {
        this.boardPanel = boardPanel;
        this.detailPanel = detailPanel;
    }
}
