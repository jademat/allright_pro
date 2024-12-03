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
    private JTextField noticeField; // 관리자 넘버
    private JTable table;
    private DefaultTableModel model;
    JDBC jdbc = new JDBC();

    public Administrator_board() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기 설정
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
        String[] header = {"게시판 번호", "제목", "게시판 내용", "좋아요 갯수","생성일", "관리자","회원 아이디"};
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
                    // mem_id 값 가져오기 (7번째 열)
                    String memId = (String) table.getValueAt(row, 6);

                    // mem_id가 "admin"인 경우에만 빨간색으로 설정
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
        JButton button4= new JButton("돌아가기");

        // "조회하기" 버튼 클릭 시
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbc.connect();
                model.setRowCount(0);  // 기존 데이터 초기화
                select();  // 데이터 조회
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
                    model.setRowCount(0);  // 테이블 초기화
                    select();
                }
            }
        });

        // "돌아가기" 버튼 클릭 시
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Administrator_main().setVisible(true);  // Main 화면으로 돌아가기
                dispose();  // 현재 창 닫기
            }
        });

        leftBottomPanel.add(button1); // 조회 버튼 추가
        leftBottomPanel.add(button2); // 삭제 버튼 추가
        leftBottomPanel.add(button4);

        // 왼쪽 패널에 테이블과 버튼 추가
        leftPanel.add(jsp);            // 테이블 추가
        leftPanel.add(leftBottomPanel); // 버튼 패널을 테이블 아래에 배치

        // 공지사항 제목과 내용 입력 필드
        JLabel titleLabel = new JLabel("공지사항 제목");
        titleLabel.setBounds(12, 13, 76, 15);
        titleField = new JTextField(30);
        titleField.setBounds(121, 10, 167, 21);

        JLabel contentLabel = new JLabel("공지사항 내용");
        contentLabel.setBounds(12, 43, 76, 15);
        contentArea = new JTextArea(5, 7);  // 내용 영역의 높이를 줄였음
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBounds(121, 37, 167, 480);
        scrollPane.setPreferredSize(new Dimension(200, 30));  // 내용 영역의 크기를 줄임

        JLabel noticeLabel = new JLabel("관리자 넘버");
        noticeLabel.setBounds(12, 530, 100, 15);
        noticeField = new JTextField(5);  // 관리자 넘버 값을 입력받는 텍스트 필드
        noticeField.setBounds(121, 530, 50, 21); // 공지사항 넙버 입력란 위치 설정

        // 공지사항 등록 버튼
        JButton button3 = new JButton("공지사항 등록");
        button3.setBounds(151, 550, 109, 23);
       // "공지사항 등록" 버튼 클릭 시
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = titleField.getText();  // 공지사항 제목
                String write = contentArea.getText();  // 공지사항 내용
                String notice = noticeField.getText();  // 관리자 넘버 (문자열로 입력됨)

                // 제목, 내용, 관리자 넘버  필드가 비어있는지 확인
                if (name.isEmpty() || write.isEmpty() || notice.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "제목, 내용, 관리자를 입력하세요.");
                    return;
                }

                // 관리자 넘버 값이 숫자인지 체크
                try {
                    int noticeValue = Integer.parseInt(notice);  // 관리자 넘버 를 숫자로 변환
                    if (noticeValue != 1) {
                        JOptionPane.showMessageDialog(null, "공지사항의 관리자는 1이어야 합니다.");
                        return;
                    }

                    // 데이터베이스에 공지사항을 삽입
                    jdbc.connect();
                    // boa_notice가 1이면 공지사항 등록
                    insertNotice(1, name, write, noticeValue, noticeValue, "admin");  // 관리자 id는 "admin"으로 가정

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "관리자 넘버 는 숫자여야 합니다.");
                }
            }
        });
        // 컨텐츠 패널에 왼쪽, 오른쪽 패널 추가
        contentPane.add(leftPanel, BorderLayout.CENTER);  // 왼쪽 (테이블과 버튼)
        contentPane.add(rightPanel, BorderLayout.EAST);   // 오른쪽 (공지사항 등록)

        // 크기 조정
        leftPanel.setPreferredSize(new Dimension(600, 700)); // 왼쪽 전체 패널 크기
        rightPanel.setPreferredSize(new Dimension(300, 100)); // 오른쪽 공지사항 등록 크기
        rightPanel.setLayout(null);
        rightPanel.add(titleLabel);
        rightPanel.add(contentLabel);
        rightPanel.add(noticeLabel);  // 관리자 넘버 레이블 추가
        rightPanel.add(button3);
        rightPanel.add(titleField);
        rightPanel.add(scrollPane);
        rightPanel.add(noticeField);  // 관리자 넘버  필드 추가

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Administrator_board();
    }

    // 데이터 조회 메서드
    void select() {
        jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_like, boa_date, boa_notice, mem_id FROM board ORDER BY boa_notice DESC, boa_no ASC";
        try {
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.res = jdbc.pstmt.executeQuery();

            while (jdbc.res.next()) {
                int no = jdbc.res.getInt("boa_no"); // NUMBER -> INT로 처리
                String name = jdbc.res.getString("boa_name"); // VARCHAR -> String으로 처리
                String write = jdbc.res.getString("boa_write"); // VARCHAR -> String으로 처리
                int like = jdbc.res.getInt("boa_like"); // NUMBER -> INT로 처리

                // boa_date가 DATE 타입이라 getDate()로 가져오기
                Date date = jdbc.res.getDate("boa_date");
                String dateString = (date != null) ? date.toString() : " "; // DATE를 String으로 변환

                int notice = jdbc.res.getInt("boa_notice"); // NUMBER -> INT로 처리
                String memId = jdbc.res.getString("mem_id"); // VARCHAR2 -> String으로 처리

                // 테이블에 추가할 데이터
                Object[] data = {no, name, write, like, dateString, notice, memId};
                model.addRow(data);  // 테이블에 데이터 추가
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (jdbc.res != null) jdbc.res.close();
                if (jdbc.pstmt != null) jdbc.pstmt.close();
                if (jdbc.con != null) jdbc.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        }
    }

    // 공지사항 등록 메서드
    void insertNotice(int no, String name, String write, int notice, int noticeValue, String id) {
        try {
            // 1. 공지사항 제목, 내용, 관리자 넘버가 1인 경우만 삽입
            if (notice != 1) {
                JOptionPane.showMessageDialog(null, "공지사항 관리자 넘버 1이어야 합니다.");
                return;
            }

            // 2. 공지사항을 삽입하는 SQL
            jdbc.sql = "INSERT INTO board ( boa_name, boa_write, boa_like, boa_date, boa_notice, mem_id) "
                     + "VALUES ( ?, ?, default, SYSDATE, ?, ?)";

            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);

            // 공지사항 데이터 삽입
         
            jdbc.pstmt.setString(1, name);  // 공지사항 제목
            jdbc.pstmt.setString(2, write);  // 공지사항 내용
            jdbc.pstmt.setInt(3, notice);  // 관리자 넘버
            jdbc.pstmt.setString(4, id);  // 관리자 ID (mem_id)

            // SQL 실행 후 결과 확인
            int result = jdbc.pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "공지사항 등록 성공");
                titleField.setText("");  // 제목 필드 초기화
                contentArea.setText("");  // 내용 필드 초기화
                noticeField.setText("");  // 괸라자 넘버 필드 초기화
                model.setRowCount(0);  // 테이블 초기화
                select();  // 새로 추가된 게시물 조회
            } else {
                JOptionPane.showMessageDialog(null, "공지사항 등록 실패");
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


