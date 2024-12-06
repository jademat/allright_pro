package workout;
import javax.swing.*;

import board.BoardMain;
import header.Header;
import jdbc.JDBC;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Workout_legs extends JFrame {
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
            this.img= img;
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

        String query = "SELECT ex_name, ex_txt, ex_img FROM workout WHERE ex_category LIKE '%하체%'";
        try {
            jdbc.pstmt = jdbc.con.prepareStatement(query);
            jdbc.res = jdbc.pstmt.executeQuery();

            while (jdbc.res.next()) {
                String name = jdbc.res.getString("ex_name");
                String description = jdbc.res.getString("ex_txt");
                String img=jdbc.res.getString("ex_img");
                exercises.add(new Exercise(name, description,img));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 연결 실패: " + e.getMessage());
        } finally {
            jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // 자원 해제
        }

        return exercises;
    }


    public Workout_legs() {
        jdbc = new JDBC(); // JDBC 객체 생성
        setTitle("다리 운동");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        getContentPane().setLayout(null);
       

        Header header = new Header(jdbc,mem_id);
        header.setBounds(0, 0, 1200, 100); // Header 위치 설정
        getContentPane().add(header);
        
        
        JLabel chestLabel = new JLabel("CHEST");
        chestLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chestLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_chest wm = new Workout_chest();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        JLabel backLabel = new JLabel("BACK");
        backLabel.setHorizontalAlignment(SwingConstants.LEFT);
        backLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_back wm = new Workout_back();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        JLabel shoulderLabel = new JLabel("SHOULDER");
        shoulderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        shoulderLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_shoulder wm = new Workout_shoulder();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        JLabel armLabel = new JLabel("ARM");
        armLabel.setHorizontalAlignment(SwingConstants.LEFT);
        armLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_arm wm = new Workout_arm();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        JLabel legsLabel = new JLabel("lEGS");
        legsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        legsLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_legs wm = new Workout_legs();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        JLabel returnLabel = new JLabel("RETURN");
        returnLabel.setHorizontalAlignment(SwingConstants.LEFT);
        returnLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		Workout_main wm = new Workout_main();
        		wm.setVisible(true);
        		dispose();
        	}
        });
        
        
        
        
        chestLabel.setForeground(Color.WHITE);
        chestLabel.setFont(new Font("굴림", Font.BOLD, 25));
        chestLabel.setBounds(40, 150, 150, 50);
        getContentPane().add(chestLabel);
        
       
        backLabel.setForeground(Color.WHITE);
        backLabel.setFont(new Font("굴림", Font.BOLD, 25));
        backLabel.setBounds(40, 220, 150, 50); // 위치 및 크기 조정
        getContentPane().add(backLabel);

        shoulderLabel.setForeground(Color.WHITE);
        shoulderLabel.setFont(new Font("굴림", Font.BOLD, 25));
        shoulderLabel.setBounds(40, 290, 150, 50); // 위치 및 크기 조정
        getContentPane().add(shoulderLabel);

        legsLabel.setForeground(Color.WHITE);
        legsLabel.setFont(new Font("굴림", Font.BOLD, 25));
        legsLabel.setBounds(40, 360, 150, 50); // 위치 및 크기 조정
        getContentPane().add(legsLabel);

        armLabel.setForeground(Color.WHITE);
        armLabel.setFont(new Font("굴림", Font.BOLD, 25));
        armLabel.setBounds(40, 430, 150, 50); // 위치 및 크기 조정
        getContentPane().add(armLabel);
        
        returnLabel.setForeground(Color.WHITE);
        returnLabel.setFont(new Font("굴림", Font.BOLD, 25));
        returnLabel.setBounds(40, 500, 150, 50); // 위치 및 크기 조정
        getContentPane().add(returnLabel);
        

        JLabel prevLabel = new JLabel(new ImageIcon("image/left.png"));
        prevLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		cardLayout.previous(imagePanel);
                updateContent(-1); 
        	}
		});
        prevLabel.setBounds(250, 600, 80, 61);  // 버튼 위치 설정
        getContentPane().add(prevLabel);

        JLabel nextLabel = new JLabel(new ImageIcon("image/right.png"));
        nextLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e){
        		 cardLayout.next(imagePanel);
                 updateContent(1); 
        	}
		});
        nextLabel.setBounds(350, 600, 80,61);  // 버튼 위치 설정
        getContentPane().add(nextLabel);

        // 이미지 슬라이드 패널
        cardLayout = new CardLayout();
        imagePanel = new JPanel(cardLayout);
        imagePanel.setBounds(220, 150, 800, 400);  // 이미지 패널 위치 변경
        imagePanel.setBackground(new Color(0, 0, 0));  // 이미지 패널의 배경을 흰색으로 설정
        imagePanel.setBorder(BorderFactory.createEmptyBorder());  // 테두리 제거
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
        titleLabel.setBounds(220, 120, 800, 30);  // 제목 라벨 위치를 이미지 바로 위로 이동
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        titleLabel.setForeground(Color.white);
        getContentPane().add(titleLabel);

        // 설명 라벨
        descriptionLabel = new JLabel(exercises.get(0).getDescription(), SwingConstants.CENTER);
        descriptionLabel.setBounds(220, 550, 800, 30);
        descriptionLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
        descriptionLabel.setForeground(Color.white);
        getContentPane().add(descriptionLabel);


      
    }

    private void addImageToCardPanel(String imagePath, String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(null);  // 배경을 투명으로 설정
        panel.setBorder(null);
            
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
    
    
    public Workout_legs(JDBC jdbc, String mem_id) {
       this.mem_id = mem_id;
    
    }
    
     public static void main(String[] args) {
        
        new Workout_legs();
     }
}
