package top.book.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import top.book.pojo.*;

public class bookdao {

	private static Gson gson = new Gson();
	private static Connection connection = null;
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybook", "debian-sys-maint", "mCQERsvDck2RibGQ");
	}
	
	private static void getClose(Connection connection, Statement st, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (st != null) {
			st.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println(Query());
	}
	
	public static String Query() throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		List<book> booksList = new ArrayList<>();
		getConnection();
		
		String sql = "SELECT * FROM mybook;";
		System.out.println(sql);
		ps = connection.prepareStatement(sql);
		// 获得查询出来的结果集合
		ResultSet rs = ps.executeQuery();
		// 如果结果集合不为空 do while
		while (rs.next()) {
			// 声明一本书的类，并且往里添加数据，一一对应
		book book = new book();
		book.setId(rs.getInt("Id"));
		book.setName(rs.getString("Name"));
		book.setprice(rs.getInt("price"));
		//bookList.add(book);
			// 最后把这本书添加到书的集合列表 booksList
//			booksList.add(book);
			System.out.println(book.getId()+" "+book.getName()
			+" "+book.getprice()+" ");
		}
		
		bookbead bookbead = new bookbead();
		// 图书的列表
		bookbead.setRows(booksList);
		// 图书的总数
		bookbead.setTotal(String.valueOf(booksList.size()));
		
		getClose(connection, null, ps, rs);
		return gson.toJson(bookbead);
	}
	public static String DELETE(String Id) throws ClassNotFoundException, SQLException {
		Statement st = null;
		getConnection();
		
		st = connection.createStatement();
		String sql = "DELETE FROM mybook WHERE \"Id\" in (" + Id + ");";
		System.out.println(sql);
		// executeUpdate 不同于 executeQuery
		// executeUpdate 执行更新操作，不返回任何结果
		st.executeUpdate(sql);
		
		getClose(connection, st, null, null);
		return "true";
	}
	public static String UPDATE(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		getConnection();
		
		book book = gson.fromJson(json, book.class);
		String sql = "UPDATE mybook SET \"\"Name\" = ?,\"price\" = ? WHERE \"Id\" = ?;";
		System.out.println(sql);
		
		ps = connection.prepareStatement(sql);
		ps.setInt(1, book.getId());
		ps.setString(2, book.getName());
		ps.setInt(3, book.getprice());
		ps.executeUpdate();
		
		getClose(connection, null, ps, null);
		return "true";
	}
	public static String INSERT(String json) throws ClassNotFoundException, SQLException {
		PreparedStatement ps = null;
		getConnection();

		book book = gson.fromJson(json, book.class);
		String sql = "INSERT INTO mybook (\"Id\", \"Name\", \"price\") VALUES (?, ?, ?);";
		 System.out.println(sql);

			
		ps = connection.prepareStatement(sql);
		ps.setInt(1, book.getId());
		ps.setString(2, book.getName());
		ps.setInt(3, book.getprice());
		ps.executeUpdate();
		
		getClose(connection, null, ps, null);
		return "true";
	}
}

