package admininstrator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import jdbc.JDBC;

import java.sql.*;

public class Administrator_main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_1;
    private JPasswordField passwordField;
    public JTextArea textArea;

    private JDBC jdbc = new JDBC(); // JDBC 객체 생성

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Administrator_main frame = new Administrator_main();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Administrator_main() {
        setTitle("관리자");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // 창 크기 설정
        setResizable(false); // 창 크기 변경 불가
        setLocationRelativeTo(null); // 화면 중앙에 창 표시
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 버튼 크기와 간격 설정
        int buttonWidth = 200;
        int buttonHeight = 100;
        int buttonSpacing = 50; // 버튼 간격
        int startX = (1200 - (buttonWidth * 4 + buttonSpacing * 3)) / 2; // 창 너비 기준으로 중앙 정렬
        int yPosition = 500; // 버튼의 y 좌표

        // 회원 수정 버튼
        JButton btnNewButton = new JButton("회원 수정");
        btnNewButton.setBounds(startX, yPosition, buttonWidth, buttonHeight);
        contentPane.add(btnNewButton);

        // 게시판 수정 버튼
        JButton btnNewButton_1 = new JButton("게시판 수정");
        btnNewButton_1.setBounds(startX + buttonWidth + buttonSpacing, yPosition, buttonWidth, buttonHeight);
        contentPane.add(btnNewButton_1);

        // 운동 수정 버튼
        JButton btnNewButton_3 = new JButton("운동 수정");
        btnNewButton_3.setBounds(startX + 2 * (buttonWidth + buttonSpacing), yPosition, buttonWidth, buttonHeight);
        contentPane.add(btnNewButton_3);

        // 음식 수정 버튼
        JButton btnnewbutton_4 = new JButton("음식 수정");
        btnnewbutton_4.setBounds(startX + 3 * (buttonWidth + buttonSpacing), yPosition, buttonWidth, buttonHeight);
        contentPane.add(btnnewbutton_4);

        // 아이디와 비밀번호 입력 필드 및 레이블 설정
        JLabel lblNewLabel = new JLabel("아이디");
        lblNewLabel.setBounds(800, 100, 50, 25);
        JLabel lblNewLabel_1 = new JLabel("비밀번호");
        lblNewLabel_1.setBounds(800, 150, 70, 25);
        textField_1 = new JTextField();
        textField_1.setBounds(880, 100, 150, 25);
        textField_1.setColumns(10);
        passwordField = new JPasswordField();
        passwordField.setBounds(880, 150, 150, 25);

        // 관리자 화면 텍스트
        textArea = new JTextArea();
        textArea.setBounds(400, 30, 400, 60);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 30));
        textArea.setText("관리자 화면");
        textArea.setEditable(false);
       
        textArea.setWrapStyleWord(true);
       

        // 로그인 버튼
        JButton btnNewButton_2 = new JButton("로그인");
        btnNewButton_2.setBounds(880, 200, 150, 30);
        btnNewButton_2.setForeground(new Color(0, 128, 64));

        contentPane.add(lblNewLabel);
        contentPane.add(lblNewLabel_1);
        contentPane.add(textField_1);
        contentPane.add(passwordField);
        contentPane.add(btnNewButton_2);
        contentPane.add(textArea);

        // 버튼 동작 추가
        btnNewButton.addActionListener(e -> {
            Administrator_member managerMemberFrame = new Administrator_member();
            managerMemberFrame.setVisible(true);
        });

        btnNewButton_1.addActionListener(e -> {
            Administrator_board managerBoardFrame = new Administrator_board();
            managerBoardFrame.setVisible(true);
        });

        btnNewButton_3.addActionListener(e -> {
            Administrator_workout workoutFrame = new Administrator_workout();
            workoutFrame.setVisible(true);
        });

        btnnewbutton_4.addActionListener(e -> {
            Administrator_food foodFrame = new Administrator_food();
            foodFrame.setVisible(true);
        });

        btnNewButton_2.addActionListener(e -> {
            String enteredId = textField_1.getText().trim();
            String enteredPassword = new String(passwordField.getPassword()).trim();

            if (enteredId.isEmpty() || enteredPassword.isEmpty()) {
                textArea.setText("아이디와 비밀번호를 입력해주세요.");
                textArea.setForeground(Color.RED);
                return;
            }

            boolean isValidLogin = validateLogin(enteredId, enteredPassword);

            if (isValidLogin) {
                textArea.setText("로그인 성공! 환영합니다.");
                textArea.setForeground(new Color(0, 128, 0));
                textField_1.setText("");       // 아이디 필드 초기화
                passwordField.setText("");    // 비밀번호 필드 초기화
            } else {
                textArea.setText("아이디 또는 비밀번호를 다시 확인하세요.");
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
                textArea.setForeground(Color.RED);
            }
        });
    }

    // 아이디와 비밀번호로 로그인 검증 메서드
    public boolean validateLogin(String enteredId, String enteredPassword) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        boolean isValid = false;
        try {
            // DB 연결
            jdbc.connect();  // JDBC 객체로 연결을 먼저 시도
            con = jdbc.con; // JDBC 객체에 연결된 con 사용

            if (con == null) {
                textArea.setText("DB 연결 실패!");
                return false;
            }

            String query = "SELECT * FROM administrator WHERE admin_id = ? AND admin_pwd = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, enteredId);
            pstmt.setString(2, enteredPassword);
            res = pstmt.executeQuery();

            if (res.next()) {
                isValid = true;  // 로그인 성공
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(con, pstmt, res); // 리소스 반환
        }
        return isValid;
    }
}
