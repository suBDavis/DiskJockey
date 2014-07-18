package net.redspin.Diskjockey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.*;

public class DBwrangler {
	
	private static DBwrangler current;
	private static Connection conn;
	private static int index;
	private static int highest;
	  
	  public static DBwrangler getDB(){
		  if (current == null){
			  current = new DBwrangler();
			  return current;
		  }
		  else return current;
	  }
	  
	  public void testShit() throws SQLException{
		  Statement st = conn.createStatement();
		  ResultSet res = st.executeQuery("SELECT * FROM  data");
		  while (res.next()) {
			  String artist = res.getString("artist");
			  String title = res.getString("title");
			  System.out.println(artist + "\t" + title);
		  }
	  }
	  
	  public String[] getNext(){
		  if (index == highest) index = 0;
		  try {
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM  data WHERE approved = 1 AND id > " + index + " LIMIT 1");
			res.next();
			index = res.getInt("id");
			update(index);
			return new String[]{res.getString("title"),res.getString("artist"),res.getString("url")};
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	  }
	  
	  private DBwrangler(){
		  init();
	  }
	  
	  private static void init(){
		  String url = "jdbc:mysql://localhost:3306/";
		  String dbName = "DiskJockey";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "DiskJockey"; 
		  String password = "________";
		  
		  try {
			  Class.forName(driver).newInstance();
			  conn = DriverManager.getConnection(url+dbName,userName,password);
			  Statement st = conn.createStatement();
			  ResultSet res = st.executeQuery("SELECT * FROM  data ORDER BY id DESC LIMIT 1");
			  res.next();
			  highest = res.getInt("id");
			  Statement st2 = conn.createStatement();
			  ResultSet res2 = st2.executeQuery("SELECT * FROM  data ORDER BY playedlast DESC LIMIT 1");
			  res2.next();
			  index = res2.getInt("id");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }

	private static void update(int i) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate("UPDATE DiskJockey.data SET playedlast = " + System.currentTimeMillis()/1000 + " WHERE data.id = " + i);
	}
	  
	  
}
