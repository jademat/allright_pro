package admininstrator;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import jdbc.JDBC;
import login.Login;
import login.Profile;

public class Icon {
    
	private String mem_id;
	private JDBC jdbc;

	
    // 사용자 아이콘 및 팝업 메뉴를 생성할 JFrame의 컨텍스트 필요
    private JLabel userIcon;
    private JPopupMenu userMenu;
  
    private JMenuItem logout;
    
    public Icon(JDBC jdbc, String mem_id) {
    	this.jdbc = jdbc;
		this.mem_id = mem_id;
        
        // 사용자 아이콘 생성
        userIcon = new JLabel(new ImageIcon("backimage/user.png"));
        userIcon.setBounds(950, 40, 100, 100);
        userIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 팝업 메뉴 생성
        userMenu = new JPopupMenu();
      
        logout = new JMenuItem("Logout");

        // 메뉴 항목들 추가
      
        userMenu.add(logout);

        // 마우스 리스너 추가 (사용자 아이콘 클릭 시 메뉴 표시)
        userIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userMenu.show(userIcon, e.getX(), e.getY());
            }
        });

      
        // 로그아웃 메뉴 클릭 시
        logout.addActionListener(e -> {
            // 현재 창 닫기
            SwingUtilities.getWindowAncestor(userIcon).dispose();
            
            // 로그인 화면 열기
            new Login().setVisible(true);
        });
    }

    // 사용자 아이콘을 JFrame이나 JPanel에 추가하는 메서드
    public void addToPanel(JPanel panel) {
        panel.add(userIcon);  // 사용자 아이콘을 패널에 추가
    }

	
}
