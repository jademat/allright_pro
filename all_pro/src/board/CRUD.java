package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBC;

public class CRUD {
	public JDBC jdbc;	// connect 메소드 호출을 위한 초기화
	
	public CRUD(JDBC jdbc) {
		this.jdbc = jdbc;
	}
	
	// Board 테이블 데이터를 조회하여 JTable 모델에 추가하고 데이터를 반환하는 메서드
	// 기본(default) 정렬
	List<Object[]> loadTable(DefaultTableModel model) {
		List<Object[]> dataList = new ArrayList<>();
		try {
    	    jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, b.mem_id, mem_rank"
    	    		+ " FROM board b JOIN member m ON b.mem_id = m.mem_id ORDER BY boa_notice DESC, boa_no DESC";
	    	jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	    	jdbc.res = jdbc.pstmt.executeQuery();
	          
	    		// ResultSet을 반복하여 데이터를 추출하고 처리
	    		while (jdbc.res.next()) {
	    			Object[] fullData = {
	    					jdbc.res.getInt("boa_no"),
	    					jdbc.res.getString("boa_name"),
	    					jdbc.res.getString("boa_write"),
	    					jdbc.res.getInt("boa_notice"),
	    					jdbc.res.getInt("boa_like"),
	    					jdbc.res.getString("boa_date").substring(0, 10),
	    					jdbc.res.getString("mem_id"),
	    					jdbc.res.getInt("mem_rank")
	   	             };
	   	             Object[] tableData = { 
	   	            		jdbc.res.getInt("boa_no"),
	   	            		jdbc.res.getString("boa_name"),
	   	            		jdbc.res.getString("mem_id"),
	   	            		jdbc.res.getInt("mem_rank"),
	   	            		jdbc.res.getInt("boa_like"),
	   	            		jdbc.res.getString("boa_date").substring(0, 10)
	   	             };
					
					 // JTable 모델에 간단한 데이터 추가
					 model.addRow(tableData);
					 // 전체 데이터를 리스트에 추가
		             dataList.add(fullData);
	    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList; // 조회된 데이터 반환
	} // loadTable() 메서드 end
	
	
	// 번호 오름차순 정렬
	void loadTable2(DefaultTableModel model) {
		try {
    	    jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, b.mem_id, mem_rank"
    	    		+ " FROM board b JOIN member m ON b.mem_id = m.mem_id ORDER BY boa_notice DESC, boa_no";
	    	jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	    	jdbc.res = jdbc.pstmt.executeQuery();
	          
	    		// ResultSet을 반복하여 데이터를 추출하고 처리
	    		while (jdbc.res.next()) {
	    			int boa_no = jdbc.res.getInt("boa_no");
	            	String boa_name = jdbc.res.getString("boa_name");
	            	String boa_write = jdbc.res.getString("boa_write");
	            	int boa_notice = jdbc.res.getInt("boa_notice");
	            	int boa_like = jdbc.res.getInt("boa_like");
	            	String boa_date = jdbc.res.getString("boa_date").substring(0, 10);
	            	String mem_id = jdbc.res.getString("mem_id");
	            	int mem_rank =  jdbc.res.getInt("mem_rank");
					
	            	Object[] tableData = {boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, mem_id, mem_rank};
					
					 // JTable 모델에 간단한 데이터 추가
					 model.addRow(tableData);					
	    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // loadTable2() 메서드 end
	
	
	
	// 인기글순 정렬
	void loadTable3(DefaultTableModel model) {
		try {
    	    jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, b.mem_id, mem_rank"
    	    		+ " FROM board b JOIN member m ON b.mem_id = m.mem_id ORDER BY boa_notice DESC, boa_like DESC";;
	    	jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	    	jdbc.res = jdbc.pstmt.executeQuery();
	          
	    		// ResultSet을 반복하여 데이터를 추출하고 처리
	    		while (jdbc.res.next()) {
	    			int boa_no = jdbc.res.getInt("boa_no");
	            	String boa_name = jdbc.res.getString("boa_name");
	            	String boa_write = jdbc.res.getString("boa_write");
	            	int boa_notice = jdbc.res.getInt("boa_notice");
	            	int boa_like = jdbc.res.getInt("boa_like");
	            	String boa_date = jdbc.res.getString("boa_date").substring(0, 10);
	            	String mem_id = jdbc.res.getString("mem_id");
	            	int mem_rank =  jdbc.res.getInt("mem_rank");
					
	            	Object[] tableData = {boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, mem_id, mem_rank};
					
					 // JTable 모델에 간단한 데이터 추가
					 model.addRow(tableData);					
	    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // loadTable3() 메서드 end
	
	
	
	// 등급순 정렬
	void loadTable4(DefaultTableModel model) {
		try {
    	    jdbc.sql = "SELECT boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, b.mem_id, mem_rank"
    	    		+ " FROM board b JOIN member m ON b.mem_id = m.mem_id ORDER BY boa_notice DESC, mem_rank DESC";
	    	jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	    	jdbc.res = jdbc.pstmt.executeQuery();
	          
	    		// ResultSet을 반복하여 데이터를 추출하고 처리
	    		while (jdbc.res.next()) {
	    			int boa_no = jdbc.res.getInt("boa_no");
	            	String boa_name = jdbc.res.getString("boa_name");
	            	String boa_write = jdbc.res.getString("boa_write");
	            	int boa_notice = jdbc.res.getInt("boa_notice");
	            	int boa_like = jdbc.res.getInt("boa_like");
	            	String boa_date = jdbc.res.getString("boa_date").substring(0, 10);
	            	String mem_id = jdbc.res.getString("mem_id");
	            	int mem_rank =  jdbc.res.getInt("mem_rank");
					
	            	Object[] tableData = {boa_no, boa_name, boa_write, boa_notice, boa_like, boa_date, mem_id, mem_rank};
					
					 // JTable 모델에 간단한 데이터 추가
					 model.addRow(tableData);					
	    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // loadTable4() 메서드 end
	 
	
	// update 메서드
	void updateBoard(int boa_no, String boa_na, String boa_write) {		    
		try {
			jdbc.sql = "UPDATE board SET boa_name = ?, boa_write = ? WHERE boa_no = ?"; 
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
			
			jdbc.pstmt.setString(1, boa_na);
			jdbc.pstmt.setString(2, boa_write);
			jdbc.pstmt.setInt(3, boa_no);
			
			int result = jdbc.pstmt.executeUpdate();
			if (result > 0) {
				JOptionPane.showMessageDialog(null, "게시글 수정 완료");
			} else {
				JOptionPane.showMessageDialog(null, "게시글 수정 실패");
			}
		} catch (SQLException e) {
			        e.printStackTrace();
		}
	} // update 메서드

	 
	// updateLikes 메서드
	int updateLikes(int boardNo) {		    
	    try {
	    	jdbc.sql = "UPDATE board SET boa_like = boa_like + 1 WHERE boa_no = ?"; 
	    	jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);
	        jdbc.pstmt.setInt(1, boardNo);
	        jdbc.pstmt.executeUpdate();

	        // 좋아요 수 반환
	        String fetchSql = "SELECT boa_like FROM board WHERE boa_no = ?";
	        try (
	        	PreparedStatement fetchPstmt = jdbc.con.prepareStatement(fetchSql)) {
	            fetchPstmt.setInt(1, boardNo);
	            jdbc.res = fetchPstmt.executeQuery();
	            if (jdbc.res.next()) {
	                return jdbc.res.getInt("boa_like");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1;
	} // updateLikes 메서드 end
	 
	 
	// delete 메서드
	void deleteBoard(int boardNo) {		    
		try {
			jdbc.sql = "DELETE FROM board WHERE boa_no = ?";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);			
		    jdbc.pstmt.setInt(1, boardNo);
		    jdbc.pstmt.executeUpdate();
		} catch (SQLException e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(null, "게시글 삭제 실패: " + e.getMessage());
		}
	} // delete 메서드 end
	 
	
	// insert 메서드
	void insertBoard(String boa_name, String boa_write) {
		try {
			// 게시판번호(최신화) 구하는 코드
			String maxNo = "select max(boa_no) from board";
			jdbc.pstmt = jdbc.con.prepareStatement(maxNo);
			jdbc.res = jdbc.pstmt.executeQuery();
			int count = 0;
			if (jdbc.res.next()) {
				count = jdbc.res.getInt(1);
			}
			
			jdbc.sql = "insert into board values(?, ?, ?, 0, 0, sysdate, 'kang')";
			jdbc.pstmt = jdbc.con.prepareStatement(jdbc.sql);					
			jdbc.pstmt.setInt(1, count + 1);					// 글 번호(최신화)
			jdbc.pstmt.setString(2, boa_name);	// 글 제목
			jdbc.pstmt.setString(3, boa_write);	// 글 내용
			//pstmt.setInt(4, '0');
			//pstmt.setInt(5, '0');
			
			int result = jdbc.pstmt.executeUpdate();
			if (result > 0) {
				JOptionPane.showMessageDialog(null, "글 등록 완료");
			} else {
				JOptionPane.showMessageDialog(null, "글 등록 실패");
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
					e1.printStackTrace();
		}
	} // insert 메서드 end
}
