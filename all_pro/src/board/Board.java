package board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Board extends JPanel {
	private JDBC jdbc;
	private CRUD crud;
	private JTable table;
	private DetailPanel detailPanel; // DetailPanel 참조
	private InsertPanel insertPanel; // InsertPanel 참조
	private JPanel boardPanel; // 현재 Board2 패널 참조
	private DefaultTableModel model;
	private JTextField searchField;

	int boa_no;
	String boa_name;
	String boa_write;
	int boa_like;
	String boa_date;
	String mem_id;
	int mem_rank;

	public Board() {
		// TODO Auto-generated constructor stub
	}

	public Board(JDBC jdbc, CRUD crud) {
		this.jdbc = jdbc;
		this.crud = crud;

		// 테이블 초기화
		String[] header = { "번호", "제목", "아이디", "등급", "좋아요수", "작성일자" };
		model = new DefaultTableModel(header, 0);
		table = new JTable(model);
		table.setBackground(new Color(255, 255, 255));

		// 레이아웃 설정
		setLayout(null);
		setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(150, 100, 900, 450);
		add(scrollPane);

		// insertButton(글작성) 버튼
		JButton insertButton = new JButton("WRITE");
		insertButton.setFont(new Font("굴림", Font.BOLD, 14));
		insertButton.setForeground(new Color(238, 57, 8));
		insertButton.setBackground(new Color(0, 0, 0));
		insertButton.setBounds(938, 585, 112, 35);
		add(insertButton);

		// 정렬 comboBox
		String[] sort = { "----- 기본 -----", "번호 오름차순", "인기글순", "등급순" };
		JComboBox<String> comboBox = new JComboBox<String>(sort);
		comboBox.setBounds(917, 62, 133, 27);
		add(comboBox);

		// 검색(search) textField, button, jcomboBox
		searchField = new JTextField();
		searchField.setBounds(234, 63, 324, 25);
		add(searchField);
		JButton searchBtn = new JButton("검색");
		searchBtn.setBounds(581, 62, 99, 28);
		add(searchBtn);
		String[] searchSort = { "제목", "본문", "작성자", "작성일" };
		JComboBox<String> searchBox = new JComboBox<String>(searchSort);
		searchBox.setBounds(150, 63, 83, 25);
		add(searchBox);

		// insertButton(글작성) 이벤트 처리
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardPanel.setVisible(false); // Board2 숨김
				insertPanel.setVisible(true); // InsertPanel 표시
			}
		});

		// comboBox(정렬) 이벤트 처리
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> selectComboBox = (JComboBox<String>) e.getSource();
				// System.out.println(selectComboBox.getSelectedIndex());
				switch (selectComboBox.getSelectedIndex()) {
				// 기본(default) 정렬
				case 0:
					jdbc.connect();
					model.setRowCount(0);
					List<Object[]> list = crud.loadTable(model);
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
					selectTable1();
					break;

				// 번호 오름차순 정렬
				case 1:
					jdbc.connect();
					model.setRowCount(0);
					List<Object[]> list2 = crud.loadTable2(model);
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
					selectTable2();
					break;

				// 인기글순 정렬
				case 2:
					jdbc.connect();
					model.setRowCount(0);
					List<Object[]> list3 = crud.loadTable3(model);
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
					selectTable3();
					break;

				// 등급순 정렬
				case 3:
					jdbc.connect();
					model.setRowCount(0);
					List<Object[]> list4 = crud.loadTable4(model);
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
					selectTable4();
					break;
				} // switch 문 end
			}
		}); // comboBox(정렬) 이벤트 처리 end

		// searchBox(검색정렬) 이벤트 처리
		searchBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> selectComboBox = (JComboBox<String>) e.getSource();
				// System.out.println(selectComboBox.getSelectedIndex());
				switch (selectComboBox.getSelectedIndex()) {
				// 제목 검색
				case 0:
					jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_like, boa_date, b.mem_id, mem_rank"
							+ " FROM board b JOIN member m ON b.mem_id = m.mem_id WHERE boa_name LIKE ?"
							+ " ORDER BY boa_no DESC";
					break;

				// 본문 검색
				case 1:
					jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_like, boa_date, b.mem_id, mem_rank"
							+ " FROM board b JOIN member m ON b.mem_id = m.mem_id WHERE boa_write LIKE ?"
							+ "ORDER BY boa_no DESC";
					break;

				// 작성자 검색
				case 2:
					jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_like, boa_date, b.mem_id, mem_rank"
							+ " FROM board b JOIN member m ON b.mem_id = m.mem_id WHERE m.mem_id LIKE ?"
							+ "ORDER BY boa_no DESC";
					break;

				// 작성일 검색
				case 3:
					jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_like, boa_date, b.mem_id, mem_rank"
							+ " FROM board b JOIN member m ON b.mem_id = m.mem_id WHERE boa_date LIKE ?"
							+ "ORDER BY boa_no DESC";
					break;
				} // switch 문 end
			}
		});

		// searchBtn(검색) 이벤트 처리
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jdbc.connect();
				String searchText = "%" + searchField.getText() + "%";
				model.setRowCount(0);
				List<Object[]> list5 = crud.searchBoard(searchText, model);
				jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
				selectTable5(searchText);
			}
		});

		// 첫 화면 데이터 로드
		jdbc.connect();
		model.setRowCount(0);
		List<Object[]> list = crud.loadTable(model);
		jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res);
		selectTable1();
	}

	// 데이터를 설정하는 메서드
	public void setTexts(int boa_no, String boa_name, String boa_write, int boa_like, String boa_date, String mem_id,
			int mem_rank) {
		this.boa_no = boa_no;
		this.boa_name = boa_name;
		this.boa_write = boa_write;
		this.boa_date = boa_date;
		this.boa_like = boa_like;
		this.mem_id = mem_id;
		this.mem_rank = mem_rank;
	}

	// DetailPanel과 Board2 참조 설정 메서드
	public void setBoardPanel(DetailPanel detailPanel, InsertPanel insertPanel, JPanel boardPanel) {
		this.detailPanel = detailPanel;
		this.insertPanel = insertPanel;
		this.boardPanel = boardPanel;
	}

	// 기본(default) 정렬시 레코드 선택 메서드
	void selectTable1() {
		// 레코드 선택 시 이벤트 처리
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 값이 변경되었을 때 처리
				int selectedRow = table.getSelectedRow(); // 선택된 행
				if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
					jdbc.connect(); // JDBC 연결 초기화
					model.setRowCount(0); // 테이블 초기화
					List<Object[]> list = crud.loadTable(model); // 새로 데이터 로드
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기

					// 새로 로드된 데이터에서 선택된 행에 접근
					if (selectedRow < list.size()) { // 선택된 행이 유효할 경우에만 처리
						Object[] rowData = list.get(selectedRow);
						int boa_no = (Integer) rowData[0];
						String boa_name = (String) rowData[1];
						String boa_write = (String) rowData[2];
						int boa_like = (Integer) rowData[3];
						String boa_date = (String) rowData[4];
						String mem_id = (String) rowData[5];
						int mem_rank = (Integer) rowData[6];

						// DetailPanel에 데이터 전달
						detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);
						setTexts(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

						// 화면 전환
						boardPanel.setVisible(false); // Board2 숨김
						detailPanel.setVisible(true); // DetailPanel 표시
					}
				}
			}
		});
	}

	// 번호 오름차순 정렬시 레코드 선택 메서드
	void selectTable2() {
		// 레코드 선택 시 이벤트 처리
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 값이 변경되었을 때 처리
				int selectedRow = table.getSelectedRow(); // 선택된 행
				if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
					jdbc.connect(); // JDBC 연결 초기화
					model.setRowCount(0); // 테이블 초기화
					List<Object[]> list2 = crud.loadTable2(model); // 새로 데이터 로드
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기

					// 새로 로드된 데이터에서 선택된 행에 접근
					if (selectedRow < list2.size()) { // 선택된 행이 유효할 경우에만 처리
						Object[] rowData = list2.get(selectedRow);
						int boa_no = (Integer) rowData[0];
						String boa_name = (String) rowData[1];
						String boa_write = (String) rowData[2];
						int boa_like = (Integer) rowData[3];
						String boa_date = (String) rowData[4];
						String mem_id = (String) rowData[5];
						int mem_rank = (Integer) rowData[6];

						// DetailPanel에 데이터 전달
						detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);
						setTexts(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

						// 화면 전환
						boardPanel.setVisible(false); // Board2 숨김
						detailPanel.setVisible(true); // DetailPanel 표시
					}
				}
			}
		});
	}

	// 인기글순 정렬시 레코드 선택 메서드
	void selectTable3() {
		// 레코드 선택 시 이벤트 처리
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 값이 변경되었을 때 처리
				int selectedRow = table.getSelectedRow(); // 선택된 행
				if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
					jdbc.connect(); // JDBC 연결 초기화
					model.setRowCount(0); // 테이블 초기화
					List<Object[]> list3 = crud.loadTable3(model); // 새로 데이터 로드
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기

					// 새로 로드된 데이터에서 선택된 행에 접근
					if (selectedRow < list3.size()) { // 선택된 행이 유효할 경우에만 처리
						Object[] rowData = list3.get(selectedRow);
						int boa_no = (Integer) rowData[0];
						String boa_name = (String) rowData[1];
						String boa_write = (String) rowData[2];
						int boa_notice = (Integer) rowData[3];
						int boa_like = (Integer) rowData[4];
						String boa_date = (String) rowData[5];
						String mem_id = (String) rowData[6];
						int mem_rank = (Integer) rowData[7];

						// DetailPanel에 데이터 전달
						detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);
						setTexts(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

						// 화면 전환
						boardPanel.setVisible(false); // Board2 숨김
						detailPanel.setVisible(true); // DetailPanel 표시
					}
				}
			}
		});
	}

	// 등급순 정렬시 레코드 선택 메서드
	void selectTable4() {
		// 레코드 선택 시 이벤트 처리
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 값이 변경되었을 때 처리
				int selectedRow = table.getSelectedRow(); // 선택된 행
				if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
					jdbc.connect(); // JDBC 연결 초기화
					model.setRowCount(0); // 테이블 초기화
					List<Object[]> list4 = crud.loadTable4(model); // 새로 데이터 로드
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기

					// 새로 로드된 데이터에서 선택된 행에 접근
					if (selectedRow < list4.size()) { // 선택된 행이 유효할 경우에만 처리
						Object[] rowData = list4.get(selectedRow);
						int boa_no = (Integer) rowData[0];
						String boa_name = (String) rowData[1];
						String boa_write = (String) rowData[2];
						int boa_notice = (Integer) rowData[3];
						int boa_like = (Integer) rowData[4];
						String boa_date = (String) rowData[5];
						String mem_id = (String) rowData[6];
						int mem_rank = (Integer) rowData[7];

						// DetailPanel에 데이터 전달
						detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);
						setTexts(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

						// 화면 전환
						boardPanel.setVisible(false); // Board2 숨김
						detailPanel.setVisible(true); // DetailPanel 표시
					}
				}
			}
		});
	}

	// 등급순 정렬시 레코드 선택 메서드
	void selectTable5(String searchText) {
		// 레코드 선택 시 이벤트 처리
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// 값이 변경되었을 때 처리
				int selectedRow = table.getSelectedRow(); // 선택된 행
				if (!e.getValueIsAdjusting() && selectedRow != -1) { // 이벤트가 최종 선택 시 실행
					jdbc.connect(); // JDBC 연결 초기화
					model.setRowCount(0); // 테이블 초기화
					List<Object[]> list5 = crud.searchBoard(searchText, model); // 새로 데이터 로드
					jdbc.close(jdbc.con, jdbc.pstmt, jdbc.res); // JDBC 자원 닫기

					// 새로 로드된 데이터에서 선택된 행에 접근
					if (selectedRow < list5.size()) { // 선택된 행이 유효할 경우에만 처리
						Object[] rowData = list5.get(selectedRow);
						int boa_no = (Integer) rowData[0];
						String boa_name = (String) rowData[1];
						String boa_write = (String) rowData[2];
						int boa_notice = (Integer) rowData[3];
						int boa_like = (Integer) rowData[4];
						String boa_date = (String) rowData[5];
						String mem_id = (String) rowData[6];
						int mem_rank = (Integer) rowData[7];

						// DetailPanel에 데이터 전달
						detailPanel.setDetails(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);
						setTexts(boa_no, boa_name, boa_write, boa_like, boa_date, mem_id, mem_rank);

						// 화면 전환
						boardPanel.setVisible(false); // Board2 숨김
						detailPanel.setVisible(true); // DetailPanel 표시
					}
				}
			}
		});
	}
}
