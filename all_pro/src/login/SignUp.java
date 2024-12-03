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

import jdbc.JDBC;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel container_Si, siForm;
	private JLabel back;
	private JLabel title, idField, passField, nameField, ageField, phField, addrField, jobField;
	private JTextField idText, nameText, ageText, phText, addrText, jobText;
	private JPasswordField passText;
	private JButton comButton, endButton;
	JDBC jdbc = new JDBC();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 553);

		container_Si = new JPanel();
		container_Si.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(container_Si);
		container_Si.setLayout(null);

		siForm = new JPanel();
		siForm.setBackground(Color.BLACK);
		siForm.setBounds(31, 10, 358, 496);
		container_Si.add(siForm);
		siForm.setLayout(null);

		// title
		title = new JLabel("ALL RIGHT");
		title.setBounds(91, 10, 133, 32);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("굴림", Font.BOLD, 20));
		siForm.add(title);

		// id
		idField = new JLabel("ID");
		idField.setForeground(Color.WHITE);
		idField.setFont(new Font("굴림", Font.BOLD, 17));
		idField.setBounds(23, 80, 63, 24);
		siForm.add(idField);

		idText = new JTextField(10);
		idText.setFont(new Font("굴림", Font.PLAIN, 15));
		idText.setBounds(137, 80, 133, 21);
		siForm.add(idText);

		passField = new JLabel("PASSWORD");
		passField.setForeground(Color.WHITE);
		passField.setFont(new Font("굴림", Font.BOLD, 17));
		passField.setBounds(23, 120, 115, 24);
		siForm.add(passField);

		passText = new JPasswordField();
		passText.setBounds(137, 120, 133, 21);
		siForm.add(passText);

		nameField = new JLabel("NAME");
		nameField.setForeground(Color.WHITE);
		nameField.setFont(new Font("굴림", Font.BOLD, 17));
		nameField.setBounds(23, 160, 63, 24);
		siForm.add(nameField);

		nameText = new JTextField();
		nameText.setBounds(137, 160, 133, 21);
		siForm.add(nameText);
		nameText.setColumns(10);

		ageField = new JLabel("AGE");
		ageField.setForeground(Color.WHITE);
		ageField.setFont(new Font("굴림", Font.BOLD, 17));
		ageField.setBounds(23, 200, 63, 24);
		siForm.add(ageField);

		ageText = new JTextField();
		ageText.setBounds(137, 200, 133, 21);
		siForm.add(ageText);
		ageText.setColumns(10);

		phField = new JLabel("PHONE");
		phField.setForeground(Color.WHITE);
		phField.setFont(new Font("굴림", Font.BOLD, 17));
		phField.setBounds(23, 240, 63, 24);
		siForm.add(phField);

		phText = new JTextField();
		phText.setBounds(137, 240, 133, 21);
		siForm.add(phText);
		phText.setColumns(10);

		addrField = new JLabel("ADDRESS");
		addrField.setForeground(Color.WHITE);
		addrField.setFont(new Font("굴림", Font.BOLD, 17));
		addrField.setBounds(23, 280, 100, 24);
		siForm.add(addrField);

		addrText = new JTextField();
		addrText.setBounds(137, 280, 133, 21);
		siForm.add(addrText);
		addrText.setColumns(10);

		jobField = new JLabel("JOB");
		jobField.setForeground(Color.WHITE);
		jobField.setFont(new Font("굴림", Font.BOLD, 17));
		jobField.setBounds(23, 320, 100, 24);
		siForm.add(jobField);

		jobText = new JTextField();
		jobText.setBounds(137, 320, 133, 21);
		siForm.add(jobText);
		jobText.setColumns(10);

		comButton = new JButton("COMPLETE");
		comButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jdbc.connect();
				insert();

				idText.setText("");
				passText.setText("");
				nameText.setText("");
				ageText.setText("");
				phText.setText("");
				addrText.setText("");
				jobText.setText("");
				idText.requestFocus();

			}
		});
		comButton.setFont(new Font("굴림", Font.BOLD, 14));
		comButton.setForeground(Color.WHITE);
		comButton.setBackground(Color.BLACK);
		comButton.setBounds(33, 425, 115, 23);
		siForm.add(comButton);

		endButton = new JButton("END");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Login n = new Login();
				setVisible(false);
				n.setVisible(true);

			}
		});
		endButton.setFont(new Font("굴림", Font.BOLD, 14));
		endButton.setForeground(Color.WHITE);
		endButton.setBackground(Color.BLACK);
		endButton.setBounds(191, 425, 115, 23);
		siForm.add(endButton);

		// background
		JLabel back = new JLabel();
		back.setIcon(new ImageIcon("backimage/young1.jpg"));
		back.setBackground(Color.BLACK);
		back.setBounds(0, 0, 1000, 520);
		container_Si.add(back);
	}

	void insert() {

		try {
			// 입력값 검증
			if (idText.getText().isEmpty() || passText.getPassword().length == 0 || nameText.getText().isEmpty()
					|| ageText.getText().isEmpty() || phText.getText().isEmpty() || addrText.getText().isEmpty()
					|| jobText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.");
				return;
			}
			// 숫자 확인 (AGE 필드)
			int age;
			try {
				age = Integer.parseInt(ageText.getText());
				if (age < 0) {
					throw new NumberFormatException("나이는 음수가 될 수 없습니다.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "유효한 나이를 입력해주세요.");
				return;
			}
			jdbc.sql = "SELECT COUNT(*) FROM member WHERE mem_id = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
			jdbc.pstmt.setString(1, idText.getText());
			jdbc.res = jdbc.pstmt.executeQuery();

			if (jdbc.res.next()) {
				int count = jdbc.res.getInt(1);
				if (count > 0) {
					JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.");
					return; // 아이디 중복 시 종료
				}
		
			}
			
			jdbc.sql = "SELECT COUNT(*) FROM member WHERE mem_ph = ?";
	        jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	        jdbc.pstmt.setString(1, phText.getText());
	        jdbc.res = jdbc.pstmt.executeQuery();

	        if (jdbc.res.next()) {
	            int count = jdbc.res.getInt(1);
	            if (count > 0) {
	                JOptionPane.showMessageDialog(null, "이미 존재하는 연락처입니다.");
	                return; // 연락처 중복 시 종료
	            }
	        }

			jdbc.sql = "insert into member values(?,?,?,?,?,?,?,sysdate,default)";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

			jdbc.pstmt.setString(1, idText.getText());
			jdbc.pstmt.setString(2, new String(passText.getPassword())); // 비밀번호
			jdbc.pstmt.setString(3, nameText.getText());
			jdbc.pstmt.setString(4, ageText.getText());
			jdbc.pstmt.setString(5, phText.getText());
			jdbc.pstmt.setString(6, addrText.getText());
			jdbc.pstmt.setString(7, jobText.getText());

			int res = jdbc.pstmt.executeUpdate();
			if (res > 0) {
				JOptionPane.showMessageDialog(null, "회원가입 성공");
			} else {
				JOptionPane.showMessageDialog(null, "회원가입 실패");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "데이터베이스 오류: " + e.getMessage());
		}
		jdbc.close(jdbc.con, jdbc.pstmt);
	}

}
