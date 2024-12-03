package header;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import board.BoardMain;
import jdbc.JDBC;
import login.Login;
import login.Profile;

public class Header extends JPanel {
	private String mem_id;
	private JDBC jdbc;

	public Header(JDBC jdbc, String mem_id) {
		this.jdbc = jdbc;
		this.mem_id = mem_id;
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1200, 100));
        setLayout(null);

        // 로고 추가
        JLabel logoLabel = new JLabel(new ImageIcon("backimage/logo.png"));
        logoLabel.setBounds(10, 10, 150, 80); // 크기 및 위치 설정
        add(logoLabel);

        JLabel lblNewLabel = new JLabel("EXECRISE");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(250, 28, 99, 42);
        add(lblNewLabel);
        
        JLabel lblFood = new JLabel("FOOD");
        lblFood.setForeground(Color.WHITE);
        lblFood.setFont(new Font("굴림", Font.BOLD, 18));
        lblFood.setBounds(500, 28, 99, 42);
        add(lblFood);
        
        JLabel boardLabel = new JLabel("BOARD");
        boardLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		SwingUtilities.getWindowAncestor(Header.this).dispose();
        		BoardMain bm = new BoardMain();
        		bm.setVisible(true);
        		
        	}
        });
        boardLabel.setForeground(Color.WHITE);
        boardLabel.setFont(new Font("굴림", Font.BOLD, 18));
        boardLabel.setBounds(750, 28, 99, 42);
        add(boardLabel);
        
        
        


        
        // 사용자 아이콘 및 팝업 메뉴
        JLabel userIcon = new JLabel(new ImageIcon("backimage/user.png"));
        userIcon.setBounds(1050, 10, 80, 80);
        userIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(userIcon);

        JPopupMenu userMenu = new JPopupMenu();
        JMenuItem profile = new JMenuItem("Profile");
        JMenuItem myBoard = new JMenuItem("MY Board");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem logout = new JMenuItem("Logout");	

        userMenu.add(profile);
        userMenu.add(myBoard);
        userMenu.add(logout);

        userIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userMenu.show(userIcon, e.getX(), e.getY());
            }
        });
        
        profile.addActionListener(e -> {
        	new Profile(jdbc,mem_id).setVisible(true);
        });

        // 로그아웃 버튼 클릭 시 행동
        logout.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(Header.this).dispose(); // 현재 창 닫기
            
            
            
            new Login().setVisible(true); // 로그인 화면 열기
        });
    }
}