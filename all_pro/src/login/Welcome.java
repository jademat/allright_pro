package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import header.Header;
import jdbc.JDBC;

public class Welcome extends JFrame {

    public Welcome(JDBC jdbc,String mem_id) {
        
    	setTitle("Welcome Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // 상단에 헤더 추가
        Header header = new Header(jdbc,mem_id);
        getContentPane().add(header, BorderLayout.NORTH);

        // 메인 콘텐츠 영역
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("WELCOME ALL RIGHT");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe Script", Font.BOLD, 50));
        welcomeLabel.setBounds(65, 68, 618, 92);
        contentPanel.add(welcomeLabel);

        JLabel back = new JLabel();
		back.setIcon(new ImageIcon("backimage/dumbbell.jpg"));
		back.setBounds(0, 0, 1200, 800);
		contentPanel.add(back);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        
    }


}