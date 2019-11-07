package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import authentication.Member;
import book.Book;

public class MemberLogManager extends DBManager {
	
	public static List<Member> getAllMemberFromDB() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Member> memberList = new ArrayList<>();
		String [] MemberInfo=new String[7];
		try {

			conn = getConn();

			state = conn.createStatement();// conn연결정보를 state로 생성.

			String sql;
			sql = "SELECT *FROM MEMBERLoG";
			
			rs = state.executeQuery(sql);

			
			while (rs.next()) {
				MemberInfo[0] = rs.getString("ID");
				MemberInfo[1] = rs.getString("PassWord");
				MemberInfo[2] = rs.getString("Name");
				MemberInfo[3] = rs.getString("Phone");
				MemberInfo[4] = rs.getString("LateFee");
				MemberInfo[5] = rs.getString("Rental_Book_Num");
				MemberInfo[6]=rs.getString("is_connected");//연결되면 1, 아니면 0
				memberList.add(new Member(MemberInfo));//빌린 책 등은 아직 안나오게 해둠, Member에서 생성자 추가로 만들어!

				// RentalBookRegistrationNum
			}
			return memberList;

			
		
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {	
			try {
				if (state != null)
					state.close();
			} catch (SQLException sqle) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException sqle1) {
				sqle1.printStackTrace();
			}
			try {if(rs!=null)
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}


	public static Member getMemberInfoFromDB(String ID) {
	
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		String[] MemberInfo = new String[7];
		
		try {

			conn = getConn();

			state = conn.createStatement();// conn연결정보를 state로 생성.
			
			String sql;
			sql = "SELECT *FROM MemberLog WHERE ID='" + ID + "'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//존재 X
			}

			if (rs.next()) {

				MemberInfo[0] = rs.getString("ID");
				MemberInfo[1] = rs.getString("PassWord");
				MemberInfo[2] = rs.getString("Name");
				MemberInfo[3] = rs.getString("Phone");
				MemberInfo[4] = rs.getString("LateFee");
				MemberInfo[5] = rs.getString("Rental_Book_Num");
				MemberInfo[6]=rs.getString("is_connected");//연결되면 1, 아니면 0
			

				// RentalBookRegistrationNum
			}
			Member retrunMem=new Member(MemberInfo);
			
			return retrunMem;

			
		
		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)
					state.close();
			} catch (SQLException sqle) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException sqle1) {
				sqle1.printStackTrace();
			}
			try {if(rs!=null)
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}



	
	public static void insertMember(Member changeMember) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO memberlog (ID, PassWord, Name, Phone, LateFee, Rental_Book_Num)VALUES (?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, changeMember.getID());
			pstmt.setString(2, changeMember.getPassword());
			pstmt.setString(3, changeMember.getName());
			pstmt.setString(4, changeMember.getPhone());
			pstmt.setString(5, changeMember.getLateFee()+ "");
			pstmt.setString(6,  changeMember.getRentalBookNum()+ "");
			

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void memberLogIn(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE memberlog SET is_connected = '1' WHERE id= '"+id+"'";
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void memberLogOut(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE memberlog SET is_connected='0' WHERE id='"+id+"'";
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void allMemberLogOut() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE memberlog SET is_connected='0'" ;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void insertMember(String ID, String PassWord, String Name, String Phone, int LateFee, int Rental_Book_Num, int is_connected) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO memberlog (ID, PassWord, Name, Phone, LateFee, Rental_Book_Num, is_connected)VALUES (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.setString(2, PassWord);
			pstmt.setString(3, Name);
			pstmt.setString(4, Phone);
			pstmt.setString(5, LateFee + "");
			pstmt.setString(6,  Rental_Book_Num+ "");
			pstmt.setString(7,  is_connected+ "");
			
			

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleateMember(String ID) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "DELETE FROM memberlog WHERE ID=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.execute();

			pstmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {if (conn != null)conn.close();
			if (pstmt != null)pstmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}

}
