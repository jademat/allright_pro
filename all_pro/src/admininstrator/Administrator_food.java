package admininstrator;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

public class Administrator_food extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    JDBC jdbc = new JDBC();

    JTextField jtf1, jtf2, jtf3;

    JTable table;

    DefaultTableModel model;

    public Administrator_food() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기를 1200x800으로 설정
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        setTitle("음식 페이지 수정");

        JPanel container1 = new JPanel();
        JPanel container2 = new JPanel();
    
        
        // 레이아웃 설정
        container1.setLayout(null); // container1에 null 레이아웃 적용
        container2.setLayout(new GridLayout(2, 2, 10, 10)); // GridLayout으로 2x2 배치
        

        JLabel jl1 = new JLabel("음식이름");
        jl1.setBounds(30, 20, 80, 30);
        jtf1 = new JTextField(10);
        jtf1.setBounds(120, 20, 150, 30);

        JLabel jl2 = new JLabel("칼로리");
        jl2.setBounds(30, 60, 80, 30);
        jtf2 = new JTextField(15);
        jtf2.setBounds(120, 60, 150, 30);

        JLabel jl3 = new JLabel("음식번호");
        jl3.setBounds(30, 100, 80, 30);
        jtf3 = new JTextField(3);
        jtf3.setBounds(120, 100, 150, 30);

        String[] header = {"음식이름", "칼로리", "음식번호"};

        model = new DefaultTableModel(header, 0);

        table = new JTable(model);

        JScrollPane jsp = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // 버튼 설정
        JButton button1 = new JButton("전체 목록");
        JButton button2 = new JButton("음식 추가");
        JButton button3 = new JButton("음식 수정");
        JButton button4 = new JButton("음식 삭제");
        JButton btnBack = new JButton("돌아가기"); // 돌아가기 버튼 추가

        // 버튼 크기 조정
        button1.setPreferredSize(new java.awt.Dimension(10, 40)); // 크기 설정
        button2.setPreferredSize(new java.awt.Dimension(10, 40)); // 크기 설정
        button3.setPreferredSize(new java.awt.Dimension(10, 40)); // 크기 설정
        button4.setPreferredSize(new java.awt.Dimension(10, 40)); // 크기 설정
        btnBack.setPreferredSize(new java.awt.Dimension(10, 40)); // 크기 설정

        // 컨테이너에 버튼들 배치
        container2.add(button1);
        container2.add(button2);
        container2.add(button3);
        container2.add(button4);
        container2.add(btnBack); // 돌아가기 버튼 추가
        
        // 컨테이너에 컴포넌트 추가
        container1.add(jl1);
        container1.add(jtf1);
        container1.add(jl2);
        container1.add(jtf2);
        container1.add(jl3);
        container1.add(jtf3);

        // 레이아웃 설정 및 컴포넌트 배치
        contentPane.setLayout(null);
        contentPane.add(container1);
        contentPane.add(jsp);
        contentPane.add(container2);

        container1.setBounds(20, 20, 500, 200);
        jsp.setBounds(20, 230, 1140, 500); // 테이블 크기 조정
        container2.setBounds(530, 20, 640, 200); // 버튼 배치 영역

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // "전체 목록" 버튼 클릭 시
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                jdbc.connect();

                model.setRowCount(0);

                select();

            }
        });

        // "음식 추가" 버튼 클릭 시
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.connect();

                insert(); // 음식 추가 메서드 호출

                // 입력 필드 초기화
                jtf1.setText("");  // 음식 이름 초기화
                jtf2.setText("");  // 칼로리 초기화
                jtf3.setText("");  // 음식 번호 초기화
                jtf1.requestFocus();  // 포커스를 음식 이름으로 이동

                model.setRowCount(0);  // 기존 테이블 데이터 초기화
                select();  // 최신 데이터로 테이블 다시 채우기
            }
        });

        // "음식 수정" 버튼 클릭 시
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.connect();
                
                update();  // 수정 작업 수행
                
                // 입력 필드 초기화
                jtf1.setText("");  // 음식이름
                jtf2.setText("");  // 칼로리
                jtf3.setText("");  // 회원번호
                jtf1.requestFocus();  // 포커스를 음식이름 입력란으로
                
                model.setRowCount(0);  // 테이블 갱신
                select();  // 최신 데이터로 테이블 다시 채우기
            }
        });

        // "음식 삭제" 버튼 클릭 시
        button4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(
                        null, "정말로 삭제 하시겠습니까?",
                        "확인", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.CLOSED_OPTION) {
                    JOptionPane.showMessageDialog(null, "윈도우 창을 종료하셨습니다.");
                    model.setRowCount(0);

                    select();
                } else if (result == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "아니오 버튼을 클릭 하셨습니다.");
                } else {
                    jdbc.connect();

                    delete();

                    jtf1.setText("");
                    jtf2.setText("");
                    jtf3.setText(null);
                    jtf1.requestFocus();
                    model.setRowCount(0);

                }

            }
        });

        // "돌아가기" 버튼 클릭 시
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrator_main().setVisible(true);  // Main 화면으로 돌아가기
                dispose();  // 현재 창 닫기
            }
        });
    }

    public static void main(String[] args) {
        new Administrator_food();
    }
    
    void select() {

        jdbc.sql = "SELECT * FROM food ORDER BY food_num";

        try {
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            jdbc.res = jdbc.pstmt.executeQuery();

            while (jdbc.res.next()) {

                String name = jdbc.res.getString("food_name");
                int kal = jdbc.res.getInt("food_kal");
                int num = jdbc.res.getInt("food_num");

                // 데이터 배열의 순서를 올바르게 설정
                Object[] data = {name, kal, num};  // 칼로리와 회원번호 순서를 올바르게 수정

                model.addRow(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (jdbc.res != null) jdbc.res.close();
                if (jdbc.pstmt != null) jdbc.pstmt.close();
                if (jdbc.con != null) jdbc.con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void insert() {
        try {
            jdbc.sql = "INSERT INTO food (food_num, food_name, food_kal) VALUES (?, ?, ?)";

            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            // jtf3에서 음식 번호를 가져오고, jtf1에서 음식 이름, jtf2에서 칼로리를 가져옵니다.
            jdbc.pstmt.setInt(1, Integer.parseInt(jtf3.getText()));  // 음식 번호는 jtf3
            jdbc.pstmt.setString(2, jtf1.getText());  // 음식 이름은 jtf1
            jdbc.pstmt.setInt(3, Integer.parseInt(jtf2.getText()));  // 칼로리는 jtf2

            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "음식 등록 성공");
                model.setRowCount(0);
                select();
            } else {
                JOptionPane.showMessageDialog(null, "음식 등록 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "숫자를 입력해야 합니다.");
            e.printStackTrace();
        } finally {
            try {
                if (jdbc.pstmt != null) jdbc.pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    void update() {
        // 음식 수정 쿼리문: mem_no를 기준으로 food_name과 food_kal을 수정
        jdbc.sql = "UPDATE food SET food_name = ?, food_kal = ? WHERE food_num = ?";

        try {
            // PreparedStatement 객체 생성
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            // jtf1 (음식이름), jtf2 (칼로리), jtf3 (회원번호)에서 값을 가져와서 설정
            String foodName = jtf1.getText();
            int foodKal = Integer.parseInt(jtf2.getText());
            int foodNum = Integer.parseInt(jtf3.getText());

            // 쿼리 파라미터 출력 (디버깅용)
            System.out.println("업데이트 쿼리 실행: ");
            System.out.println("음식 이름: " + foodName);
            System.out.println("칼로리: " + foodKal);
            System.out.println("음식 번호: " + foodNum);

            jdbc.pstmt.setString(1, foodName);  // 음식 이름
            jdbc.pstmt.setInt(2, foodKal);      // 칼로리
            jdbc.pstmt.setInt(3, foodNum);        // 회원 번호

            // 쿼리 실행
            int result = jdbc.pstmt.executeUpdate();

            // 실행 결과에 따른 메시지 출력
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "음식 수정 성공");
                select();
            } else {
                JOptionPane.showMessageDialog(null, "음식 수정 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "입력된 칼로리와 회원번호가 숫자가 아닙니다.");
            e.printStackTrace();
        } finally {
            try {
                if (jdbc.pstmt != null) jdbc.pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    void delete() {
        try {
            jdbc.sql = "DELETE FROM food WHERE food_num = ?";  // 음식 번호로 삭제하는 쿼리

            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            // 선택된 행의 인덱스 가져오기
            int row = table.getSelectedRow();

            // 행이 선택되지 않았다면, 메시지를 출력하고 종료
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 음식을 선택하세요.");
                return;
            }

            // 선택된 행에서 음식 번호를 가져옵니다 (3번째 열에 음식 번호가 있다고 가정)
            int foodNum = (int) model.getValueAt(row, 2);  // food_num은 3번째 열 (index 2)

            // 음식 번호를 쿼리 파라미터로 설정
            jdbc.pstmt.setInt(1, foodNum);

            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "음식 삭제 성공");
                model.removeRow(row);  // 테이블에서 해당 행 삭제
                model.setRowCount(0);
                select();
            } else {
                JOptionPane.showMessageDialog(null, "음식 삭제 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (jdbc.pstmt != null) jdbc.pstmt.close();
                if (jdbc.con != null) jdbc.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       
    }

}