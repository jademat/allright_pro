package workout;

import javax.swing.*;

import board.BoardMain;
import header.Header;
import jdbc.JDBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Workout_main extends JFrame {
	private static final long serialVersionUID = 1L;
	static String mem_id;
	JDBC jdbc = new JDBC();
	
	

	public Workout_main() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 800); // 창 크기 변경
		setBackground(Color.BLACK);
		setTitle("운동 메인");

		getContentPane().setLayout(null);

		Header header = new Header(jdbc, mem_id);
		header.setBounds(0, 0, 1200, 100); // Header 위치 설정
		getContentPane().add(header);

		JLabel logoLabel2 = new JLabel(new ImageIcon("image/work.png"));
		logoLabel2.setBounds(192, 120, 1000, 500); // 위치 및 크기 조정
		getContentPane().add(logoLabel2);

		// 운동 버튼들
		JButton chestButton = new JButton("가슴");
		chestButton.setBounds(30, 200, 150, 50); // 위치 및 크기 조정
		getContentPane().add(chestButton);

		JButton backButton = new JButton("등");
		backButton.setBounds(30, 270, 150, 50); // 위치 및 크기 조정
		getContentPane().add(backButton);

		JButton shoulderButton = new JButton("어깨");
		shoulderButton.setBounds(30, 340, 150, 50); // 위치 및 크기 조정
		getContentPane().add(shoulderButton);

		JButton legsButton = new JButton("하체");
		legsButton.setBounds(30, 410, 150, 50); // 위치 및 크기 조정
		getContentPane().add(legsButton);

		JButton armButton = new JButton("팔");
		armButton.setBounds(30, 480, 150, 50); // 위치 및 크기 조정
		getContentPane().add(armButton);

		legsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Manaager_board 창을 열도록 처리

				Workout_legs legs = new Workout_legs();
				legs.setVisible(true);

			}
		});
		// 팔 버튼 클릭 시 Workout_arm 페이지 열기
		armButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 팔 운동 페이지 창 열기
				Workout_arm arm = new Workout_arm();
				arm.setVisible(true); // 팔 운동 페이지 보이기
				setVisible(false); // 현재 창을 닫음
			}
		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Workout_back back = new Workout_back();
				back.setVisible(true);
				dispose();

			}
		});

		chestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Manaager_board 창을 열도록 처리

				Workout_chest chest = new Workout_chest();
				chest.setVisible(true);
				dispose();

			}
		});

		shoulderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Manaager_board 창을 열도록 처리

				Workout_shoulder shoulder = new Workout_shoulder();
				shoulder.setVisible(true);
				dispose();

			}
		});
	}

	public Workout_main(JDBC jdbc, String mem_id) {
		this.mem_id = mem_id;

	}

	public static void main(String[] args) {

		new Workout_main();
	}
}