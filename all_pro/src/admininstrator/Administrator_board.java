package admininstrator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import jdbc.JDBC;
import java.sql.*;

public class Administrator_board extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField titleField; // 공지사항 제목
    private JTextArea contentArea; // 공지사항 내용
    private JTable table;
    private DefaultTableModel model;
    static String mem_id;
    JDBC jdbc = new JDBC();

    public Administrator_board() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기 설정
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setTitle("게시판 관리");
       
        
        		

        // 전체 화면 레이아웃 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 좌우 구분 레이아웃
        JPanel leftPanel = new JPanel(); // 왼쪽 패널 (게시판 테이블 및 버튼)
        JPanel rightPanel = new JPanel(); // 오른쪽 패널 (공지사항 등록)

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // 게시판 목록을 위한 테이블 설정
        String[] header = {"게시판 번호", "제목", "게시판 내용", "좋아요 갯수", "생성일", "작성자"};
        model = new DefaultTableModel(header, 0);
        table = new JTable(model);

        // 테이블 셀 렌더러 설정
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // "회원 아이디" 열(column index 6)에서 mem_id가 "admin"인 경우만 빨간색으로 설정
                if (column == 1) { // 제목 열(0-based index이므로 1은 제목 열)
                    String memId = (String) table.getValueAt(row, 5);
                    if ("admin".equals(memId)) {
                        cell.setForeground(Color.RED); // 빨간색 텍스트
                    } else {
                        cell.setForeground(Color.BLACK); // 기본 검은색 텍스트
                    }
                } else {
                    cell.setForeground(Color.BLACK); // 다른 열은 기본 검은색 텍스트
                }

                return cell;
            }
        });
        JScrollPane jsp = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

       jsp.setPreferredSize(new Dimension(600, 400)); // 테이블 세로 크기 줄이기

        // 하단 버튼 영역
        JPanel leftBottomPanel = new JPanel();
        leftBottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton button1 = new JButton("조회하기");
        JButton button2 = new JButton("삭제하기");
        JButton button4 = new JButton("돌아가기");

        // "조회하기" 버튼 클릭 시
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.connect();
                model.setRowCount(0); // 기존 데이터 초기화
                select(); // 데이터 조회
            }
        });

        // "삭제하기" 버튼 클릭 시
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null, "정말로 삭제 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    jdbc.connect();
                    delete(); // 데이터 삭제
                    model.setRowCount(0); // 테이블 초기화
                    select();
                }
            }
        });

        // "돌아가기" 버튼 클릭 시
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrator_main().setVisible(true); // Main 화면으로 돌아가기
                dispose(); // 현재 창 닫기
            }
        });

        leftBottomPanel.add(button1); // 조회 버튼 추가
        leftBottomPanel.add(button2); // 삭제 버튼 추가
        leftBottomPanel.add(button4);

        // 왼쪽 패널에 테이블과 버튼 추가
        leftPanel.add(jsp); // 테이블 추가
        leftPanel.add(leftBottomPanel); // 버튼 패널을 테이블 아래에 배치

        // 공지사항 제목과 내용 입력 필드
        JLabel titleLabel = new JLabel("공지사항 제목");
        titleField = new JTextField(30);

        JLabel contentLabel = new JLabel("공지사항 내용");
        contentArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(contentArea);

        // 공지사항 등록 버튼
        JButton button3 = new JButton("공지사항 등록");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = titleField.getText();
                String write = contentArea.getText();

                if (name.isEmpty() || write.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "제목과 내용을 입력하세요.");
                    return;
                }

                jdbc.connect();
                insertNotice(name, write, "admin"); // 관리자 ID는 "admin"으로 가정
            }
        });

        // 컨텐츠 패널에 왼쪽, 오른쪽 패널 추가
        contentPane.add(leftPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);

        leftPanel.setPreferredSize(new Dimension(600, 700)); // 왼쪽 전체 패널 크기
        rightPanel.setPreferredSize(new Dimension(300, 100)); // 오른쪽 공지사항 등록 크기
        rightPanel.setLayout(new FlowLayout());
        rightPanel.add(titleLabel);
        rightPanel.add(titleField);
        rightPanel.add(contentLabel);
        rightPanel.add(scrollPane);
        rightPanel.add(button3);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Administrator_board();
    }

 // 데이터 조회 메서드
    void select() {
        jdbc.sql = "SELECT * FROM board ORDER BY boa_no ASC"; // 모든 게시물을 가져오는 SQL 쿼리
        try {
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.res = jdbc.pstmt.executeQuery();
            
            // "admin"으로 작성된 게시물을 먼저 저장할 리스트
            java.util.List<Object[]> adminPosts = new java.util.ArrayList<>();
            java.util.List<Object[]> otherPosts = new java.util.ArrayList<>();
            
            // 데이터 처리
            while (jdbc.res.next()) {
                Object[] data = {
                    jdbc.res.getInt("boa_no"),
                    jdbc.res.getString("boa_name"),
                    jdbc.res.getString("boa_write"),
                    jdbc.res.getInt("boa_like"),
                    jdbc.res.getDate("boa_date"),
                    jdbc.res.getString("mem_id")
                };
                
                // "admin" 게시물은 adminPosts 리스트에 추가
                if ("admin".equals(data[5])) {
                    adminPosts.add(data);
                } else {
                    // 나머지 게시물은 otherPosts 리스트에 추가
                    otherPosts.add(data);
                }
            }

            // 테이블에 데이터 추가: 먼저 adminPosts, 그 다음에 otherPosts
            model.setRowCount(0);  // 기존 데이터 초기화
            for (Object[] post : adminPosts) {
                model.addRow(post);  // "admin" 게시물 추가
            }
            for (Object[] post : otherPosts) {
                model.addRow(post);  // 나머지 게시물 추가
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // 적절한 close 호출
        }
    }


    // 게시판 삭제 메서드
    void delete() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 게시판을 선택하세요.");
                return;
            }

            Object boaNo = model.getValueAt(row, 0);
            jdbc.sql = "DELETE FROM board WHERE boa_no = ?";
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.pstmt.setInt(1, Integer.parseInt(boaNo.toString()));
            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "게시판이 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "게시판 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(jdbc.con, jdbc.pstmt); // 적절한 close 호출
        }
    }

    // 공지사항 등록 메서드
    void insertNotice(String name, String write, String id) {
        try {
            jdbc.sql = "INSERT INTO board (boa_name, boa_write, boa_like, boa_date, mem_id) "
                     + "VALUES (?, ?, default, SYSDATE, ?)";
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            jdbc.pstmt.setString(1, name);
            jdbc.pstmt.setString(2, write);
            jdbc.pstmt.setString(3, id);

            int result = jdbc.pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "공지사항 등록 성공");
                titleField.setText("");
                contentArea.setText("");
                model.setRowCount(0);
                select();
            } else {
                JOptionPane.showMessageDialog(null, "공지사항 등록 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(jdbc.con, jdbc.pstmt); // 적절한 close 호출
        }
    }
}
