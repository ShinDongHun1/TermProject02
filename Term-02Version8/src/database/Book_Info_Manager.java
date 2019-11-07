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

public class Book_Info_Manager  extends DBManager{
	
	
	public static List<Book> getAllBookFromDB() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book > returnBookList = new ArrayList<>();
		String [] BookInfo=new String[9];
		try {
			conn = getConn();
			state = conn.createStatement();// conn���������� state�� ����.
			String sql;
			sql = "SELECT *FROM BOOK_INFO";	
			rs = state.executeQuery(sql);
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;
				
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}  finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	
	public static void deleateBookInfo(int Registration_Num) {//�����ϰ� 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();

			String sql;
			sql = "DELETE FROM Book_Info WHERE Registration_Num=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Registration_Num+"");
			pstmt.execute();

			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}  finally {	
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {}
		}
	}
	public static void insertBookInfo(int Registration_Num,String Title,String Auther, String Publisher, String Genre, Boolean rentalAvailability,String ID, String location, int remainingPeriod) {//���� 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String insertRentalAvailability="1";
			
			String sql;
			sql = "INSERT INTO Book_info (Registration_Num, Title, Auther, Publisher, Genre, rentalAvailability,ID,location,remainingPeriod)VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Registration_Num+"");
			pstmt.setString(2, Title);
			pstmt.setString(3, Auther);
			pstmt.setString(4, Publisher);
			pstmt.setString(5, Genre + "");
			if(rentalAvailability.equals("true")) {
				insertRentalAvailability="1";
			}else if(rentalAvailability.equals("false")){
				insertRentalAvailability="0";
			}
			pstmt.setString(6,  insertRentalAvailability);
			pstmt.setString(7,  ID);
			pstmt.setString(8,  location);
			pstmt.setString(9,  remainingPeriod+ "");
			
			

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  finally {	
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {}
		}
	}
	
	public static void changeBookInfo(int Registration_Num,String Title,String Auther, String Publisher, String Genre, Boolean rentalAvailability,String ID, String location, int remainingPeriod) {//���� 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			deleateBookInfo(Registration_Num);
			
			conn = getConn();

			String insertRentalAvailability="1";
			
			String sql;
			sql = "INSERT INTO Book_info (Registration_Num, Title, Auther, Publisher, Genre, rentalAvailability,ID,location,remainingPeriod)VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Registration_Num+"");
			pstmt.setString(2, Title);
			pstmt.setString(3, Auther);
			pstmt.setString(4, Publisher);
			pstmt.setString(5, Genre + "");
			if(rentalAvailability.equals("true")) {
				insertRentalAvailability="1";
			}else if(rentalAvailability.equals("false")){
				insertRentalAvailability="0";
			}
			pstmt.setString(6,  insertRentalAvailability);
			pstmt.setString(7,  ID);
			pstmt.setString(8,  location);
			pstmt.setString(9,  remainingPeriod+ "");

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  finally {	
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {}
		}
	}
	
	public static void changeBookInfo(Book changeBook) {//���� 
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			
			deleateBookInfo(changeBook.getRegistrationNum());
			
			conn = getConn();

			String insertRentalAvailability="1";
			
			String sql;
			sql = "INSERT INTO Book_info (Registration_Num, Title, Auther, Publisher, Genre, rentalAvailability,ID,location,remainingPeriod)VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, changeBook.getRegistrationNum()+"");
			pstmt.setString(2, changeBook.getTitle());
			pstmt.setString(3, changeBook.getAuther());
			pstmt.setString(4, changeBook.getPublisher());
			pstmt.setString(5, changeBook.getId());
			if(changeBook.canRental()) {//true
				insertRentalAvailability="1";
			}else{
				insertRentalAvailability="0";
			}
			pstmt.setString(6,  insertRentalAvailability);
			pstmt.setString(7,  changeBook.getId());
			pstmt.setString(8,  changeBook.getLocation());
			pstmt.setString(9,  changeBook.getRemainingPeriod()+ "");

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  finally {	
			try {
				if (pstmt != null)	pstmt.close();
				if (conn != null)	conn.close();
			} catch (SQLException e) {}
		}
	}
	

	
	
	
	public static Book searchBook(int Registration_Num) {//��Ϲ�ȣ�� ã��-�����ڰ� ����� �޼ҵ�
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		Book returnBook=null;
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			

			String sql;
			sql = "SELECT *FROM Book_info WHERE Registration_Num='" +Registration_Num+ "'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//���� X
			}
			if (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBook=new Book(BookInfo);

			}
			return returnBook;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	public static List<Book> searchBook(String info) {//�Ѱ��� ã�� ex)�����̸� Title-~~~, �۰��� Auther-~~~�̷������� �Է¹޾Ƽ� :�� ���ø� �ؼ� ��� ����
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book> returnBookList=new ArrayList<>();
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			
			String[] searchInfo=info.split("-");

			String sql;
			sql = "SELECT *FROM Book_info WHERE "+searchInfo[0]+" like '%" +searchInfo[1]+ "%'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				System.out.println("�������");
				return null;//���� X
			}
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}

	public static List<Book> searchBook(String info1, String info2) {//���� �ΰ�
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book> returnBookList=new ArrayList<>();
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			String[] searchInfo1=info1.split("-");
			String[] searchInfo2=info2.split("-");

			String sql;
			sql = "SELECT *FROM Book_info WHERE "+searchInfo1[0]+" like'%" +searchInfo1[1]+ "%' AND "+searchInfo2[0]+" like'%"+searchInfo2[1]+"%'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//���� X
			}
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	public static List<Book> searchBook(String info1, String info2, String info3) {//���� ����
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book> returnBookList=new ArrayList<>();
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			String[] searchInfo1=info1.split("-");
			String[] searchInfo2=info2.split("-");
			String[] searchInfo3=info3.split("-");
			String sql;
			sql = "SELECT *FROM Book_info WHERE "+searchInfo1[0]+" like '%"+searchInfo1[1]
																		 +"%' AND "+searchInfo2[0]+" like '%"+searchInfo2[1]
																		 +"%' AND "+searchInfo3[0]+" like '%"+searchInfo3[1]+"%'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//���� X
			}
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	
	public static List<Book> searchBook(String info1, String info2, String info3,String info4) {//���� �װ�
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book> returnBookList=new ArrayList<>();
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			String[] searchInfo1=info1.split("-");
			String[] searchInfo2=info2.split("-");
			String[] searchInfo3=info3.split("-");
			String[] searchInfo4=info4.split("-");
			String sql;
			sql = "SELECT *FROM Book_info WHERE "+searchInfo1[0]+" like '%"+searchInfo1[1]
																		 +"%' AND "+searchInfo2[0]+" like '%"+searchInfo2[1]
																		 +"%' AND "+searchInfo3[0]+" like '%"+searchInfo3[1]
																		 +"%' AND "+searchInfo4[0]+" like '%"+searchInfo4[1]+"%'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//���� X
			}
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	public static List<Book> searchBook(String info1, String info2, String info3,String info4,String info5) {//���� 5ro
		Connection conn = null;
		Statement state = null;
		ResultSet rs =null;
		List<Book> returnBookList=new ArrayList<>();
		String[] BookInfo = new String[9];
		try {

			conn = getConn();

			state = conn.createStatement();// conn���������� state�� ����.
			String[] searchInfo1=info1.split("-");
			String[] searchInfo2=info2.split("-");
			String[] searchInfo3=info3.split("-");
			String[] searchInfo4=info4.split("-");
			String[] searchInfo5=info5.split("-");
			String sql;
			sql = "SELECT *FROM Book_info WHERE "+searchInfo1[0]+" like '"+searchInfo1[1]
																		 +"' AND "+searchInfo2[0]+" like '"+searchInfo2[1]
																		 +"' AND "+searchInfo3[0]+" like '"+searchInfo3[1]
																		 +"' AND "+searchInfo4[0]+" like '"+searchInfo4[1]
																		 +"' AND "+searchInfo5[0]+" like '"+searchInfo5[1]+"'";
			
			rs = state.executeQuery(sql);
			if(rs==null) {
				return null;//���� X
			}
			while (rs.next()) {//���߿� �ٲ�
				BookInfo[0] = rs.getString("Registration_Num");
				BookInfo[1] = rs.getString("Title");
				BookInfo[2] = rs.getString("Auther");
				BookInfo[3] = rs.getString("Publisher");
				BookInfo[4] = rs.getString("Genre");
				BookInfo[5] = rs.getString("rentalAvailability");
				BookInfo[6] = rs.getString("ID");
				BookInfo[7] = rs.getString("location");
				BookInfo[8] = rs.getString("remainingPeriod");
				
				returnBookList.add(new Book(BookInfo));

			}
			return returnBookList;

		} catch (Exception e) {
			return null;
		} finally {	
			try {
				if (state != null)	state.close();
				if (conn != null)	conn.close();
				if(rs!=null)	rs.close();
			} catch (SQLException e) {}
		}
	}
	
	
	
}
