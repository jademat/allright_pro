package admininstrator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import jdbc.JDBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.sql.*;

public class Administrator_workout extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    static String mem_id;
  JDBC jdbc = new JDBC();  // JDBC 객체를 클래스 멤버로 선언

    JTextField jtf1, jtf2, jtf3, jtfImagePath;  // 이미지 경로를 위한 텍스트 필드 추가
    JTable table;
    DefaultTableModel model;

    JRadioButton jrb1, jrb2, jrb3, jrb4, jrb5; // 라디오 버튼 선언
    ButtonGroup group;

    public Administrator_workout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800); // 창 크기 설정
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        setTitle("운동정보 수정");
        contentPane.setLayout(null);

        // ** 상단 입력 패널 ** 
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBounds(20, 20, 1140, 150);  // 크기 조정

        JLabel jl1 = new JLabel("운동 번호:");
        jl1.setBounds(20, 20, 80, 30);
        inputPanel.add(jl1);

        jtf1 = new JTextField();
        jtf1.setBounds(100, 20, 150, 30);
        inputPanel.add(jtf1);

        JLabel jl2 = new JLabel("운동 이름:");
        jl2.setBounds(280, 20, 80, 30);
        inputPanel.add(jl2);

        jtf2 = new JTextField();
        jtf2.setBounds(360, 20, 150, 30);
        inputPanel.add(jtf2);

        JLabel jl3 = new JLabel("운동 설명:");
        jl3.setBounds(540, 20, 80, 30);
        inputPanel.add(jl3);

        jtf3 = new JTextField();
        jtf3.setBounds(620, 20, 400, 30);
        inputPanel.add(jtf3);

        // ** 이미지 경로 입력 필드 ** 
        JLabel jlImagePath = new JLabel("이미지 경로:");
        jlImagePath.setBounds(20, 70, 80, 30);
        inputPanel.add(jlImagePath);

        jtfImagePath = new JTextField();
        jtfImagePath.setBounds(100, 70, 400, 30);
        jtfImagePath.setEditable(false);  // 사용자가 수정할 수 없도록 설정
        inputPanel.add(jtfImagePath);

        // ** 라디오 버튼 패널 ** 
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        radioPanel.setBounds(20, 170, 1140, 40);

        jrb1 = new JRadioButton("가슴");
        jrb2 = new JRadioButton("등");
        jrb3 = new JRadioButton("어깨");
        jrb4 = new JRadioButton("하체");
        jrb5 = new JRadioButton("팔");

        group = new ButtonGroup();
        group.add(jrb1);
        group.add(jrb2);
        group.add(jrb3);
        group.add(jrb4);
        group.add(jrb5);

        radioPanel.add(jrb1);
        radioPanel.add(jrb2);
        radioPanel.add(jrb3);
        radioPanel.add(jrb4);
        radioPanel.add(jrb5);

        // ** 테이블 패널 ** 
        JScrollPane jsp = new JScrollPane(
                table = new JTable(model = new DefaultTableModel(new String[]{"운동 번호", "운동 이름", "운동 설명", "카테고리", "이미지"}, 0)),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        jsp.setBounds(20, 210, 1140, 350);

        // ** 버튼 패널 ** 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBounds(20, 590, 1140, 50);

        JButton btnSearch = new JButton("전체 조회");
        JButton btnUpdate = new JButton("운동 수정");
        JButton btnDelete = new JButton("운동 삭제");
        JButton btnInsert = new JButton("운동 등록");
        JButton btnFile = new JButton("파일 찾기");
        JButton btnBack = new JButton("돌아가기");  // 돌아가기 버튼 추가

        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnFile);
        buttonPanel.add(btnBack);  // 버튼을 패널에 추가

        // 패널 추가
        contentPane.add(inputPanel);
        contentPane.add(radioPanel);
        contentPane.add(jsp);
        contentPane.add(buttonPanel);

        // ** 버튼 동작 ** 
        btnSearch.addActionListener(e -> {
            model.setRowCount(0);  // 기존 데이터 초기화
            select();  // 전체 조회 메서드 호출
        });

        btnUpdate.addActionListener(e -> {
            if (!validateInputs1()) return;  // 입력값 검증
            update();  // 수정 메서드 호출
            clearInputs();
            model.setRowCount(0);
            select();
        });
        btnDelete.addActionListener(e -> {
            jdbc.connect();
            int selectedRow = table.getSelectedRow(); // 선택된 행을 가져옴

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 운동을 선택하세요.");
                return;  // 선택된 행이 없으면 삭제 진행하지 않음
            }

            // 선택된 행의 운동 번호 가져오기
            int exNo = (int) model.getValueAt(selectedRow, 0);  // 첫 번째 열 (운동 번호)

            // 삭제 메서드 호출
            jdbc.sql = "DELETE FROM workout WHERE ex_no = ?";
            try {
                jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
                jdbc.pstmt.setInt(1, exNo);
                int result = jdbc.pstmt.executeUpdate();
                
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "운동 삭제 성공");
                    model.removeRow(selectedRow);  // 삭제된 행을 테이블에서 제거
                } else {
                    JOptionPane.showMessageDialog(null, "운동 삭제 실패");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                jdbc.close(jdbc.con, jdbc.pstmt);
            }
        });

        btnInsert.addActionListener(e -> {
            jdbc.connect();
            // 1. 입력값 검증
            if (!validateInputs1()) return;

            // 2. 이미지 경로가 비어있다면 경고 메시지
            String imagePath = jtfImagePath.getText();
            if (imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "이미지 파일을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 4. 운동 정보 및 선택된 카테고리 가져오기
            String exNo = jtf1.getText();
            String exName = jtf2.getText();
            String exText = jtf3.getText();
            String exCategory = getSelectedCategory();

            if (exCategory.isEmpty()) {
                JOptionPane.showMessageDialog(null, "운동 카테고리를 선택해주세요.");
                return;
            }

            // 5. DB에 운동 정보 저장
            jdbc.sql = "INSERT INTO workout (ex_no, ex_name, ex_txt, ex_category, ex_img) VALUES (?, ?, ?, ?, ?)";
            try {
                jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
                jdbc.pstmt.setInt(1, Integer.parseInt(exNo));
                jdbc.pstmt.setString(2, exName);
                jdbc.pstmt.setString(3, exText);
                jdbc.pstmt.setString(4, exCategory);
                jdbc.pstmt.setString(5, imagePath);  // 선택한 이미지 경로 DB에 저장

                int result = jdbc.pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "운동 등록 성공");
                } else {
                    JOptionPane.showMessageDialog(null, "운동 등록 실패");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnFile.addActionListener(e -> {
            // JFileChooser 객체 생성
            JFileChooser chooser = new JFileChooser();

            // 파일 필터 설정 (이미지 파일만 선택하도록)
            FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif");
            chooser.setFileFilter(filter);

            // 파일 선택 대화 상자 열기
            int ret = chooser.showOpenDialog(null);

            // 사용자가 파일을 선택했을 경우
            if (ret == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                jtfImagePath.setText(selectedFile.getAbsolutePath());  // 경로 표시
            }
        });

        // 돌아가기 버튼 동작
        btnBack.addActionListener(e -> {
            // 돌아가기 버튼을 클릭했을 때 Administrator_main 으로 돌아가기
            new Administrator_main().setVisible(true);  // Administrator_main 창을 새로 띄움
            this.dispose();  // 현재 창을 닫음
        });
    }

    // ** 조회 메서드 ** 
   private void select() {
        String query = "SELECT ex_no, ex_name, ex_txt, ex_category, ex_img FROM workout";
        try {
            jdbc.connect();
            jdbc.pstmt = jdbc.con.prepareStatement(query);
            jdbc.res = jdbc.pstmt.executeQuery();
            
            while (jdbc.res.next()) {
                Object[] row = {
                    jdbc.res.getInt("ex_no"),
                    jdbc.res.getString("ex_name"),
                    jdbc.res.getString("ex_txt"),
                    jdbc.res.getString("ex_category"),
                    jdbc.res.getString("ex_img")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
        }
    }

   // ** 수정 메서드 ** 
   private void update() {
       String query = "UPDATE workout SET ex_name = ?, ex_txt = ?, ex_category = ? WHERE ex_no = ?";
       try {
           jdbc.connect();
           jdbc.pstmt = jdbc.con.prepareStatement(query);
           jdbc.pstmt.setString(1, jtf2.getText());
           jdbc.pstmt.setString(2, jtf3.getText());
           jdbc.pstmt.setString(3, getSelectedCategory());
           jdbc.pstmt.setInt(4, Integer.parseInt(jtf1.getText()));

           int result = jdbc.pstmt.executeUpdate();
           JOptionPane.showMessageDialog(null, result > 0 ? "운동 수정 성공" : "운동 수정 실패");
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           jdbc.close(jdbc.con, jdbc.pstmt);
       }
   }

    // 운동 정보 삭제 메서드
    private void delete() {
        jdbc.connect();
        try {
            String exNo = jtf1.getText();
            jdbc.sql = "DELETE FROM workout WHERE ex_no = ?";
            jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
            jdbc.pstmt.setInt(1, Integer.parseInt(exNo));

            int result = jdbc.pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "운동 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(null, "운동 삭제 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 운동 카테고리 가져오기
    private String getSelectedCategory() {
        if (jrb1.isSelected()) {
            return "가슴";
        } else if (jrb2.isSelected()) {
            return "등";
        } else if (jrb3.isSelected()) {
            return "어깨";
        } else if (jrb4.isSelected()) {
            return "하체";
        } else if (jrb5.isSelected()) {
            return "팔";
        }
        return "";
    }

    // 입력 값 검증 메서드
    private boolean validateInputs1() {
        if (jtf1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "운동 번호를 입력하세요.");
            return false;
        }
        if (jtf2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "운동 이름을 입력하세요.");
            return false;
        }
        if (jtf3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "운동 설명을 입력하세요.");
            return false;
        }
        if (group.getSelection() == null) {
            JOptionPane.showMessageDialog(null, "카테고리를 선택하세요.");
            return false;
        }
        return true;
    }

    // 입력 값 초기화 메서드
    private void clearInputs() {
        jtf1.setText("");
        jtf2.setText("");
        jtf3.setText("");
        group.clearSelection();
        jtfImagePath.setText("");
    }
    public Administrator_workout(JDBC jdbc, String mem_id) {
        this.mem_id = mem_id;

     }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Administrator_workout().setVisible(true);
        });
    }
}
