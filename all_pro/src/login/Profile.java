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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import header.Header;
import jdbc.JDBC;

public class Profile extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel container_pro, proForm;
	private JLabel back;
	private JLabel title, idField, passField, nameField, ageField, phField, addrField, jobField;
	private JTextField idText, nameText, ageText, phText, addrText, jobText;
	private JPasswordField passText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Profile() {

	}

	/**
	 * Create the frame.
	 */
	public Profile(JDBC jdbc, String mem_id) {

		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 553);

		container_pro = new JPanel();
		container_pro.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(container_pro);
		container_pro.setLayout(null);

		proForm = new JPanel();
		proForm.setBackground(Color.BLACK);
		proForm.setBounds(31, 10, 358, 496);
		container_pro.add(proForm);
		proForm.setLayout(null);

		title = new JLabel("My Profile");
		title.setBounds(91, 10, 133, 32);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("굴림", Font.BOLD, 20));
		proForm.add(title);

		// id
		idField = new JLabel("ID");
		idField.setForeground(Color.WHITE);
		idField.setFont(new Font("굴림", Font.BOLD, 17));
		idField.setBounds(23, 80, 63, 24);

		proForm.add(idField);

		idText = new JTextField(10);
		idText.setFont(new Font("굴림", Font.PLAIN, 15));
		idText.setBounds(137, 80, 133, 21);
		idText.setEditable(false);
		proForm.add(idText);

		passField = new JLabel("PASSWORD");
		passField.setForeground(Color.WHITE);
		passField.setFont(new Font("굴림", Font.BOLD, 17));
		passField.setBounds(23, 120, 115, 24);
		proForm.add(passField);

		passText = new JPasswordField();
		passText.setBounds(137, 120, 133, 21);
		proForm.add(passText);

		nameField = new JLabel("NAME");
		nameField.setForeground(Color.WHITE);
		nameField.setFont(new Font("굴림", Font.BOLD, 17));
		nameField.setBounds(23, 160, 63, 24);
		proForm.add(nameField);

		nameText = new JTextField();
		nameText.setBounds(137, 160, 133, 21);
		proForm.add(nameText);
		nameText.setColumns(10);

		ageField = new JLabel("AGE");
		ageField.setForeground(Color.WHITE);
		ageField.setFont(new Font("굴림", Font.BOLD, 17));
		ageField.setBounds(23, 200, 63, 24);
		proForm.add(ageField);

		ageText = new JTextField();
		ageText.setBounds(137, 200, 133, 21);
		proForm.add(ageText);
		ageText.setColumns(10);

		phField = new JLabel("PHONE");
		phField.setForeground(Color.WHITE);
		phField.setFont(new Font("굴림", Font.BOLD, 17));
		phField.setBounds(23, 240, 63, 24);
		proForm.add(phField);

		phText = new JTextField();
		phText.setBounds(137, 240, 133, 21);
		proForm.add(phText);
		phText.setColumns(10);

		addrField = new JLabel("ADDRESS");
		addrField.setForeground(Color.WHITE);
		addrField.setFont(new Font("굴림", Font.BOLD, 17));
		addrField.setBounds(23, 280, 100, 24);
		proForm.add(addrField);

		addrText = new JTextField();
		addrText.setBounds(137, 280, 133, 21);
		proForm.add(addrText);
		addrText.setColumns(10);

		jobField = new JLabel("JOB");
		jobField.setForeground(Color.WHITE);
		jobField.setFont(new Font("굴림", Font.BOLD, 17));
		jobField.setBounds(23, 320, 100, 24);
		proForm.add(jobField);

		jobText = new JTextField();
		jobText.setBounds(137, 320, 133, 21);
		proForm.add(jobText);
		jobText.setColumns(10);

		JButton btnNewButton = new JButton("MODIFY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updpate(jdbc, mem_id);
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 15));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(51, 403, 107, 23);
		proForm.add(btnNewButton);

		JButton btnEnd = new JButton("END");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnEnd.setForeground(Color.WHITE);
		btnEnd.setFont(new Font("굴림", Font.BOLD, 15));
		btnEnd.setBackground(Color.BLACK);
		btnEnd.setBounds(181, 403, 107, 23);
		proForm.add(btnEnd);
		
		JButton btnDel = new JButton("DELETE");
		btnDel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					delete(jdbc,mem_id);
					
			}			
		});
		
		btnDel.setForeground(Color.WHITE);
		btnDel.setFont(new Font("굴림", Font.BOLD, 15));
		btnDel.setBackground(Color.BLACK);
		btnDel.setBounds(110, 450, 107, 23);
		proForm.add(btnDel);
		
		
		JLabel back = new JLabel();
		back.setIcon(new ImageIcon("backimage/young_mini.jpg"));
		back.setBackground(Color.BLACK);
		back.setBounds(0, 0, 1000, 520);
		container_pro.add(back);

		load(jdbc, mem_id);
	}

	private void load(JDBC jdbc, String mem_id) {
		jdbc.connect();

		try {
			jdbc.sql = "SELECT * FROM member WHERE mem_id = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
			jdbc.pstmt.setString(1, mem_id);
			jdbc.res = jdbc.pstmt.executeQuery();

			if (jdbc.res.next()) {
				idText.setText(jdbc.res.getString("mem_id"));
				passText.setText(jdbc.res.getString("mem_pass"));
				nameText.setText(jdbc.res.getString("mem_name"));
				ageText.setText(String.valueOf(jdbc.res.getInt("mem_age")));
				phText.setText(jdbc.res.getString("mem_ph"));
				addrText.setText(jdbc.res.getString("mem_addr"));
				jobText.setText(jdbc.res.getString("mem_job"));
			}
			if (!jdbc.res.next()) {
				System.out.println("No data found for mem_id: " + mem_id);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updpate(JDBC jdbc, String mem_id) {
		jdbc.connect();
		try {
			if (passText.getPassword().length == 0 || nameText.getText().isEmpty() || ageText.getText().isEmpty()
					|| phText.getText().isEmpty() || addrText.getText().isEmpty() || jobText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.");
				return;
			}

			jdbc.sql = "update member set mem_pass = ? , mem_name = ?, mem_age = ? , mem_ph = ?, mem_addr = ? , mem_job =?"
					+ "where mem_id = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

			jdbc.pstmt.setString(1, new String(passText.getPassword())); // 비밀번호
			jdbc.pstmt.setString(2, nameText.getText());
			jdbc.pstmt.setString(3, ageText.getText());
			jdbc.pstmt.setString(4, phText.getText());
			jdbc.pstmt.setString(5, addrText.getText());
			jdbc.pstmt.setString(6, jobText.getText());
			jdbc.pstmt.setString(7, mem_id);

			int res = jdbc.pstmt.executeUpdate();
			if (res > 0) {
				JOptionPane.showMessageDialog(null, "회원정보수정 성공");
			} else {
				JOptionPane.showMessageDialog(null, "회원정보수정 실패");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void delete(JDBC jdbc, String mem_id) {
		jdbc.connect();
		try {
			jdbc.sql = "delete from member where mem_id = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
			jdbc.pstmt.setInt(1, Integer.parseInt(mem_id));
			int res = jdbc.pstmt.executeUpdate();
			if(res > 0) {
				JOptionPane.showMessageDialog(null, "삭제 완료");
				Login l = new Login();
				l.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "삭제 실패");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}