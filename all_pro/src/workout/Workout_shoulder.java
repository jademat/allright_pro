package workout;

import javax.swing.*;

import board.BoardMain;
import header.Header;
import jdbc.JDBC;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Workout_shoulder extends JFrame {
   private static final long serialVersionUID = 1L;
   private CardLayout cardLayout;
   private JPanel imagePanel;
   private JLabel descriptionLabel; // 설명 라벨
   private ArrayList<Exercise> exercises; // 운동 데이터 리스트
   private JLabel titleLabel; // 운동 제목 라벨
   private int currentIndex = 0; // 현재 인덱스
   private JDBC jdbc; // JDBC 객체 추가
   static String mem_id;

   // Exercise 클래스
   private static class Exercise {
      private String name;
      private String description;
      private String img;

      public Exercise(String name, String description, String img) {
         this.name = name;
         this.description = description;
         this.img = img;
      }

      public String getName() {
         return name;
      }

      public String getDescription() {
         return description;
      }

      public String getImg() {
         return img;
      }

   }

   // 데이터베이스 헬퍼 메서드
   private ArrayList<Exercise> getExercisesFromDatabase() {
      ArrayList<Exercise> exercises = new ArrayList<>();
      jdbc.connect(); // JDBC 연결

      String query = "SELECT ex_name, ex_txt, ex_img FROM workout WHERE ex_category LIKE '%어깨%'";
      try {
         jdbc.pstmt = jdbc.con.prepareStatement(query);
         jdbc.res = jdbc.pstmt.executeQuery();

         while (jdbc.res.next()) {
            String name = jdbc.res.getString("ex_name");
            String description = jdbc.res.getString("ex_txt");
            String img = jdbc.res.getString("ex_img");
            exercises.add(new Exercise(name, description, img));
         }
      } catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "데이터베이스 연결 실패: " + e.getMessage());
      } finally {
         jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // 자원 해제
      }

      return exercises;
   }

   public Workout_shoulder() {

      jdbc = new JDBC(); // JDBC 객체 생성
      setTitle("어깨 운동");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(0, 0, 1200, 800);
      getContentPane().setBackground(Color.WHITE);
      getContentPane().setLayout(null);

      Header header = new Header(jdbc, mem_id);
      header.setBounds(0, 0, 1200, 100); // Header 위치 설정
      getContentPane().add(header);

      // 운동 버튼들
      JButton chestButton = new JButton("가슴");
      chestButton.setBounds(30, 150, 150, 50); // 위치 및 크기 조정
      getContentPane().add(chestButton);

      JButton backButton = new JButton("등");
      backButton.setBounds(30, 220, 150, 50); // 위치 및 크기 조정
      getContentPane().add(backButton);

      JButton shoulderButton = new JButton("어깨");
      shoulderButton.setBounds(30, 290, 150, 50); // 위치 및 크기 조정
      getContentPane().add(shoulderButton);

      JButton legsButton = new JButton("하체");
      legsButton.setBounds(30, 360, 150, 50); // 위치 및 크기 조정
      getContentPane().add(legsButton);

      JButton armButton = new JButton("팔");
      armButton.setBounds(30, 430, 150, 50); // 위치 및 크기 조정
      getContentPane().add(armButton);

      JButton returnButton = new JButton("돌아가기");
      returnButton.setBounds(30, 500, 150, 50);
      getContentPane().add(returnButton);

      JButton prevButton = new JButton("이전");
      prevButton.setBounds(250, 600, 100, 30); // 버튼 위치 설정
      getContentPane().add(prevButton);

      JButton nextButton = new JButton("다음");
      nextButton.setBounds(360, 600, 100, 30); // 버튼 위치 설정
      getContentPane().add(nextButton);

      // 이미지 슬라이드 패널
      cardLayout = new CardLayout();
      imagePanel = new JPanel(cardLayout);
      imagePanel.setBounds(220, 150, 800, 400); // 이미지 패널 위치 변경
      imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      getContentPane().add(imagePanel);

      // 데이터베이스에서 운동 데이터 가져오기
      exercises = getExercisesFromDatabase();
      if (exercises.isEmpty()) {
         JOptionPane.showMessageDialog(this, "운동 데이터가 없습니다.");
         return;
      }

      // 운동 제목과 이미지를 추가
      for (Exercise exercise : exercises) {
         addImageToCardPanel(exercise.getImg(), exercise.getName());
      }

      // 제목 라벨을 이미지 바로 위에 배치
      titleLabel = new JLabel(exercises.get(0).getName(), SwingConstants.CENTER);
      titleLabel.setBounds(220, 120, 800, 30); // 제목 라벨 위치를 이미지 바로 위로 이동
      titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
      getContentPane().add(titleLabel);

      // 설명 라벨
      descriptionLabel = new JLabel(exercises.get(0).getDescription(), SwingConstants.CENTER);
      descriptionLabel.setBounds(220, 560, 800, 30);
      descriptionLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
      getContentPane().add(descriptionLabel);

      // 이벤트 설정
      prevButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cardLayout.previous(imagePanel);
            updateContent(-1); // 수정된 부분
         }
      });

      nextButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cardLayout.next(imagePanel);
            updateContent(1); // 수정된 부분
         }
      });

      // 운동 버튼 이벤트 설정
      legsButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_legs legs = new Workout_legs(); // 하체 운동 페이지로 이동
            legs.setVisible(true);
            dispose(); // 현재 페이지 종료
         }
      });

      armButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_arm arm = new Workout_arm(); // 팔 운동 페이지로 이동
            arm.setVisible(true);
            dispose(); // 현재 페이지 종료
         }
      });

      backButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_back back = new Workout_back(); // 등 운동 페이지로 이동
            back.setVisible(true);
            dispose(); // 현재 페이지 종료
         }
      });

      chestButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_chest chest = new Workout_chest(); // 가슴 운동 페이지로 이동
            chest.setVisible(true);
            dispose(); // 현재 페이지 종료
         }
      });

      shoulderButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_shoulder shoulder = new Workout_shoulder();
            shoulder.setVisible(true);
            dispose();
         }
      });

      returnButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Workout_main mainPage = new Workout_main();
            mainPage.setVisible(true);
            dispose();
         }
      });
   }

   private void addImageToCardPanel(String imagePath, String title) {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());

      ImageIcon imageIcon = new ImageIcon(imagePath);
      JLabel imageLabel = new JLabel(imageIcon);

      panel.add(imageLabel, BorderLayout.CENTER);

      imagePanel.add(panel, title);
   }

   private void updateContent(int direction) {
      currentIndex = (currentIndex + direction + exercises.size()) % exercises.size();
      titleLabel.setText(exercises.get(currentIndex).getName());
      descriptionLabel.setText(exercises.get(currentIndex).getDescription());
   }

   public Workout_shoulder(JDBC jdbc, String mem_id) {
       this.mem_id = mem_id;
    
    }

   public static void main(String[] args) {

      new Workout_shoulder();
   }
}
