package workout;

import javax.swing.*;

import board.BoardMain;
import header.Header;
import jdbc.JDBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Workout_main extends JFrame {
   private static final long serialVersionUID = 1L;
   static String mem_id;
   JDBC jdbc = new JDBC();
   
   

   public Workout_main() {
      getContentPane().setBackground(new Color(255, 255, 255));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(0, 0, 1200, 800); // 창 크기 변경
      setLocationRelativeTo(null);
      getContentPane().setBackground(Color.BLACK);
      setTitle("운동 메인");

      getContentPane().setLayout(null);

      Header header = new Header(jdbc, mem_id);
      header.setBounds(0, 0, 1200, 100); // Header 위치 설정
      getContentPane().add(header);

      JLabel logoLabel2 = new JLabel(new ImageIcon("image/work.png"));
      logoLabel2.setBounds(192, 120, 1000, 600); // 위치 및 크기 조정
      getContentPane().add(logoLabel2);


      JLabel chestLabel = new JLabel("CHEST");
      chestLabel.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e){
      		Workout_chest wm = new Workout_chest();
      		wm.setVisible(true);
      		dispose();
      		
      	}
      });
      
      JLabel backLabel = new JLabel("BACK");
      backLabel.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e){
      		Workout_back wm = new Workout_back();
      		wm.setVisible(true);
      		dispose();
      	}
      });
      
      JLabel shoulderLabel = new JLabel("SHOULDER");
      shoulderLabel.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e){
      		Workout_shoulder wm = new Workout_shoulder();
      		wm.setVisible(true);
      		dispose();
      	}
      });
      
      JLabel armLabel = new JLabel("ARM");
      armLabel.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e){
      		Workout_arm wm = new Workout_arm();
      		wm.setVisible(true);
      		dispose();
      	}
      });
      
      JLabel legsLabel = new JLabel("lEGS");
      legsLabel.addMouseListener(new MouseAdapter() {
      	@Override
      	public void mouseClicked(MouseEvent e){
      		Workout_legs wm = new Workout_legs();
      		wm.setVisible(true);
      		dispose();
      	}
      });
      
      
      chestLabel.setForeground(Color.WHITE);
      chestLabel.setFont(new Font("굴림", Font.BOLD, 25));
      chestLabel.setBounds(40, 200, 150, 50);
      add(chestLabel);
      
     
      backLabel.setForeground(Color.WHITE);
      backLabel.setFont(new Font("굴림", Font.BOLD, 25));
      backLabel.setBounds(40, 270, 150, 50); // 위치 및 크기 조정
      add(backLabel);

      shoulderLabel.setForeground(Color.WHITE);
      shoulderLabel.setFont(new Font("굴림", Font.BOLD, 25));
      shoulderLabel.setBounds(40, 340, 150, 50); // 위치 및 크기 조정
      add(shoulderLabel);

      legsLabel.setForeground(Color.WHITE);
      legsLabel.setFont(new Font("굴림", Font.BOLD, 25));
      legsLabel.setBounds(40, 410, 150, 50); // 위치 및 크기 조정
      getContentPane().add(legsLabel);

      armLabel.setForeground(Color.WHITE);
      armLabel.setFont(new Font("굴림", Font.BOLD, 25));
      armLabel.setBounds(40, 480, 150, 50); // 위치 및 크기 조정
      getContentPane().add(armLabel);

      

   }

   public Workout_main(JDBC jdbc, String mem_id) {
      this.mem_id = mem_id;

   }

   public static void main(String[] args) {

      new Workout_main();
   }
}