package board;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import dto.Member;
import header.Header;
import jdbc.JDBC;

public class BoardMain extends JFrame {
   static String mem_id;
   JDBC jdbc = new JDBC();
   CRUD crud = new CRUD(jdbc);
   
   public BoardMain() {
              
        setTitle("운동관리시스템");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Header 패널
        Header headerPanel = new Header(jdbc, mem_id);
        headerPanel.setBounds(0, 0, 1200, 100); // 상단 고정 위치   
        headerPanel.setLayout(new BorderLayout(0, 0));
        
        // 패널 초기화
        Board boardPanel = new Board(jdbc, crud);
        boardPanel.setBounds(0, 100, 1186, 663);
        boardPanel.setLayout(new BorderLayout(0, 0));

        DetailPanel detailPanel = new DetailPanel(jdbc, crud);
        detailPanel.setBounds(0, 100, 1186, 663);
        detailPanel.setLayout(new BorderLayout(0, 0));
        
        InsertPanel insertPanel = new InsertPanel(jdbc, crud);
        insertPanel.setBounds(0, 100, 1186, 663);
        insertPanel.setLayout(new BorderLayout(0, 0));
        
        // 패널 추가
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(detailPanel, BorderLayout.CENTER);
        getContentPane().add(insertPanel, BorderLayout.CENTER);
        
        // 기본 화면 설정
        boardPanel.setVisible(true);
        detailPanel.setVisible(false);
        insertPanel.setVisible(false);

        // Board2에서 DetailPanel에 접근할 수 있도록 연결
        boardPanel.setBoardPanel(detailPanel, insertPanel, boardPanel);        
        detailPanel.setDetailPanel(boardPanel, detailPanel);        
        insertPanel.setInsertPanel(boardPanel, insertPanel);       

        setVisible(true);
   }
   
   public BoardMain(JDBC jdbc, String mem_id) {
      this.mem_id = mem_id;
   
   }
   
    public static void main(String[] args) {
       
       new BoardMain();
    }
}
