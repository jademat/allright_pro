package login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import board.BoardMain;
import header.Header;
import jdbc.JDBC;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private JLabel idField, passField;
	private JTextField idText;
	private JPasswordField passText;
	JDBC jdbc = new JDBC();
	Welcome wel = new Welcome(jdbc,getName());
	BoardMain boardmain = new BoardMain(jdbc,getName());
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("login");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 553);
		container = new JPanel();
		container.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(container);
		container.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(31, 70, 295, 391);
		container.add(panel);
		panel.setLayout(null);

		idField = new JLabel("ID");
		idField.setFont(new Font("굴림", Font.BOLD, 17));
		idField.setForeground(Color.WHITE);
		idField.setBounds(12, 72, 63, 24);
		panel.add(idField);

		idText = new JTextField(20);
		idText.setFont(new Font("굴림", Font.PLAIN, 15));
		idText.setBounds(12, 106, 133, 21);
		panel.add(idText);
		idText.setColumns(10);

		passField = new JLabel("PASSWORD");
		passField.setForeground(Color.WHITE);
		passField.setBackground(Color.WHITE);
		passField.setFont(new Font("굴림", Font.BOLD, 17));
		passField.setBounds(12, 168, 108, 15);
		panel.add(passField);

		passText = new JPasswordField(20);
		passText.setFont(new Font("굴림", Font.PLAIN, 15));
		passText.setBounds(12, 193, 133, 21);
		panel.add(passText);

		JLabel jl3 = new JLabel("ALL RIGHT");
		jl3.setFont(new Font("굴림", Font.BOLD, 20));
		jl3.setForeground(Color.WHITE);
		jl3.setBounds(67, 10, 133, 32);
		panel.add(jl3);

		JButton logB = new JButton("LOGIN");
		logB.setBackground(Color.BLACK);
		logB.setForeground(Color.WHITE);

		logB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jdbc.connect();
				log();
			}
		});
		logB.setBounds(12, 291, 91, 23);
		panel.add(logB);

		JButton upB = new JButton("SIGN UP");
		upB.setBackground(Color.BLACK);
		upB.setForeground(Color.WHITE);

		upB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SignUp s = new SignUp();
				setVisible(false);
				s.setVisible(true);

			}
		});

		upB.setBounds(142, 291, 91, 23);
		panel.add(upB);

		JLabel back = new JLabel();
		back.setIcon(new ImageIcon("backimage/young_mini.jpg"));
		back.setBounds(0, 0, 1000, 520);
		container.add(back);
	}

	void log() {
		String mem_id = idText.getText();
		char[] pass = passText.getPassword();
		String mem_pass = new String(pass);

		try {
			jdbc.sql = "SELECT mem_id, mem_pass FROM member WHERE mem_id = ? AND mem_pass = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
			jdbc.pstmt.setString(1, mem_id);
			jdbc.pstmt.setString(2, mem_pass);

			jdbc.res = jdbc.pstmt.executeQuery();

			if (jdbc.res.next()) {
				// 로그인 성공
				JOptionPane.showMessageDialog(this, "Login Success", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);

				// 로그인한 사용자 ID를 Welcome 화면으로 전달
				Welcome wel = new Welcome(jdbc,mem_id);
				wel.setVisible(true);
				Profile pro = new Profile(jdbc,mem_id);
				Header header = new Header(jdbc, mem_id); // JDBC 객체 전달
				BoardMain bm = new BoardMain(jdbc, mem_id);
			} else {
				// 로그인 실패
				JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.close(jdbc.con, jdbc.pstmt);
		}
	}
}