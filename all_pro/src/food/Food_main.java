package food;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import header.Header;
import jdbc.JDBC;

public class Food_main extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchField; // 음식 이름 입력 필드
    private JLabel resultLabel; // 검색 결과를 출력할 라벨

    // 데이터베이스 연결 관련 변수
    JDBC jdbc = new JDBC();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Food_main frame = new Food_main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Food_main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기 변경 (1200x800)
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Header 컴포넌트 생성 후 위치 설정
        Header header = new Header();
        header.setBounds(0, 0, 1200, 100); // 헤더 위치와 크기 설정 (위쪽에 고정)
        contentPane.add(header); // 헤더를 contentPane에 추가

        setContentPane(contentPane);
        contentPane.setLayout(null); // 컴포넌트들의 위치를 수동으로 설정

        // 제목 라벨 설정
        JLabel titleLabel = new JLabel("음식 페이지");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        titleLabel.setBounds(450, 50, 300, 50);
        contentPane.add(titleLabel);

        // 음식 검색 라벨 설정
        JLabel searchLabel = new JLabel("음식 이름 검색:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        searchLabel.setBounds(100, 150, 200, 30);
        contentPane.add(searchLabel);

        // 음식 이름 입력 필드 설정
        searchField = new JTextField();
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchField.setBounds(300, 150, 400, 40);
        contentPane.add(searchField);
        searchField.setColumns(10);

        // 검색 버튼 설정
        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        searchButton.setBounds(750, 150, 150, 40);
        contentPane.add(searchButton);

        // 검색 결과 라벨 설정
        resultLabel = new JLabel("칼로리 결과가 여기에 나타납니다.");
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
        resultLabel.setBounds(100, 250, 1000, 40);
        contentPane.add(resultLabel);

        // 검색 버튼 클릭 시 이벤트 처리
        searchButton.addActionListener(e -> {
            jdbc.connect();
            searchFoodCalories();
        });
    }

    public void searchFoodCalories() {
        String foodName = searchField.getText().trim();  // 입력한 음식 이름

        if (foodName.isEmpty()) {
            resultLabel.setText("음식 이름을 입력해주세요.");
        } else {
            try {
                // SQL 쿼리 실행
                jdbc.sql = "SELECT food_name, food_kal FROM food WHERE food_name = ?";
                jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
                jdbc.pstmt.setString(1, foodName);
                jdbc.res = jdbc.pstmt.executeQuery();

                if (jdbc.res.next()) {
                    String name = jdbc.res.getString("food_name");
                    int calories = jdbc.res.getInt("food_kal");
                    resultLabel.setText(name + "의 100g당 칼로리: " + calories + "kcal");
                } else {
                    resultLabel.setText("해당 음식이 존재하지 않습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resultLabel.setText("검색 중 오류 발생.");
            } finally {
                jdbc.close(jdbc.con, jdbc.pstmt); // DB 연결 닫기
            }
        }
    }
}
