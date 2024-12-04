package admininstrator;
import java.awt.*;
import javax.swing.*;

public class Administrator_main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public JTextArea textArea;

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
        
        // 아이콘 로그아웃 버튼
        Icon icon= new Icon();
        icon.addToPanel(contentPane);

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

        // 관리자 화면 텍스트
        textArea = new JTextArea();
        textArea.setBounds(500, 70, 400, 60);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 30));
        textArea.setText("관리자 화면");
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        contentPane.add(textArea);

        // 버튼 동작 추가
        btnNewButton.addActionListener(e -> {
            Administrator_member managerMemberFrame = new Administrator_member();
            managerMemberFrame.setVisible(true);
            dispose();
        });

        btnNewButton_1.addActionListener(e -> {
            Administrator_board managerBoardFrame = new Administrator_board();
            managerBoardFrame.setVisible(true);
            dispose();
        });

        btnNewButton_3.addActionListener(e -> {
            Administrator_workout workoutFrame = new Administrator_workout();
            workoutFrame.setVisible(true);
            dispose();
        });

        btnnewbutton_4.addActionListener(e -> {
            Administrator_food foodFrame = new Administrator_food();
            foodFrame.setVisible(true);
            dispose();
        });
    }
}
