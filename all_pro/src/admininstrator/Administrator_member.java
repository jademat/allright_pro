package admininstrator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

public class Administrator_member extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    static String mem_id;
    
    JDBC jdbc = new JDBC();

    JTextField jtf1;

    JTable table;

    DefaultTableModel model;

    public Administrator_member() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기 설정
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        setTitle("회원정보 수정");

        JPanel container1 = new JPanel();
        JPanel container2 = new JPanel();

        // 레이아웃 설정
        container1.setLayout(null); // container1에 null 레이아웃 적용
        container2.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // FlowLayout을 LEFT로 설정하여 버튼을 수평으로 배치

        JLabel jl1 = new JLabel("아이디");
        jl1.setBounds(30, 20, 80, 30);
        jtf1 = new JTextField(10);
        jtf1.setBounds(120, 20, 150, 30);

        // 테이블 헤더 설정
        String[] header = {"아이디", "비밀번호", "이름", "나이", "전화번호", "주소", "직업", "생성일", "등급"};

        model = new DefaultTableModel(header, 0);

        table = new JTable(model);

        JScrollPane jsp = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // 버튼 설정
        JButton button1 = new JButton("전체 목록");
        JButton button2 = new JButton("회원 수정");
        JButton button3 = new JButton("회원 삭제");
        JButton btnBack = new JButton("돌아가기");  // 돌아가기 버튼 추가

        // 컨테이너에 버튼들 배치
        container2.add(button1);
        container2.add(button2);
        container2.add(button3);
        container2.add(btnBack);  // 돌아가기 버튼 추가

        // 컨테이너에 컴포넌트 추가
        container1.add(jl1);
        container1.add(jtf1);

        // 레이아웃 설정 및 컴포넌트 배치
        contentPane.setLayout(null);
        contentPane.add(container1);
        contentPane.add(container2);  // 버튼 컨테이너
        contentPane.add(jsp);  // 테이블

        container1.setBounds(20, 20, 500, 200);
        container2.setBounds(20, 230, 1140, 50); // 버튼 컨테이너 위치 왼쪽으로 설정
        jsp.setBounds(20, 290, 1140, 500); // 테이블 크기 조정

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

        // "회원 수정" 버튼 클릭 시
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.connect();
                update();
                jtf1.setText("");  // 입력 필드 초기화
                jtf1.requestFocus();  // 포커스를 음식 이름으로 이동
                model.setRowCount(0);  // 기존 테이블 데이터 초기화
                select();  // 최신 데이터로 테이블 다시 채우기
            }
        });

        // "회원 삭제" 버튼 클릭 시
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null, "정말로 삭제 하시겠습니까?",
                        "확인", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    jdbc.connect();
                    delete();
                    jtf1.setText("");
                    jtf1.requestFocus();
                    model.setRowCount(0);
                    select();
                }
            }
        });

        // "돌아가기" 버튼 클릭 시
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrator_main().setVisible(true);  // Administrator_main 창을 새로 띄움
                dispose();  // 현재 창을 닫음
            }
        });
    }

    public static void main(String[] args) {
        new Administrator_member();
    }

    // 데이터 조회 메서드
    void select() {
       

        try {
        	
        	jdbc.sql = "SELECT * FROM member ORDER BY mem_id";
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.res = jdbc.pstmt.executeQuery();

            while (jdbc.res.next()) {
                
                String id = jdbc.res.getString("mem_id");
                String pass = jdbc.res.getString("mem_pass");
                String name = jdbc.res.getString("mem_name");
                int age = jdbc.res.getInt("mem_age");
                String ph = jdbc.res.getString("mem_ph");
                String addr = jdbc.res.getString("mem_addr");
                String job = jdbc.res.getString("mem_job");
                String date = jdbc.res.getString("mem_date"); // DATE를 String으로 가져옴
                int rank = jdbc.res.getInt("mem_rank");

                Object[] data = { id, pass, name, age, ph, addr, job, date, rank};
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

    // 회원 수정 메서드
    void update() {
        jdbc.sql = "UPDATE member SET mem_rank = ? WHERE mem_id = ?";

        try {
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            int rank = Integer.parseInt(JOptionPane.showInputDialog("새로운 등급을 입력하세요:"));
            String id = jtf1.getText();

            jdbc.pstmt.setInt(1, rank);
            jdbc.pstmt.setString(2, id);

            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "회원 등급 수정 성공");
                select();
            } else {
                JOptionPane.showMessageDialog(null, "회원 등급 수정 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "유효한 숫자를 입력하세요.");
        } finally {
            try {
                if (jdbc.pstmt != null) jdbc.pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 회원 삭제 메서드
    void delete() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 회원을 선택하세요.");
                return;
            }

            Object memno = model.getValueAt(row, 0);

            jdbc.sql = "DELETE FROM member WHERE mem_id = ?";

            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.pstmt.setString(1,  (String) memno);

            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "회원 삭제 성공");
                model.removeRow(row);
                select();
            } else {
                JOptionPane.showMessageDialog(null, "회원 삭제 실패");
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
