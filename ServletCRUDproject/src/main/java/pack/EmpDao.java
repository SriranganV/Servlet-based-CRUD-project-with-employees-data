package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.sql.ResultSet;

public class EmpDao extends HttpServlet {
public static Connection getConnection() {
	Connection conn=null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/crudregister","root","");
	}catch(Exception e) {
		System.out.println(e);
	}
	return conn;
}
	public static int save(Emp e) {
		int status=0;
		try {
			Connection conn=EmpDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(
					"insert into usercrud(id,name,password,email,city) values(?,?,?,?,?)"
					);
			ps.setString(1, e.getId());
			ps.setString(2, e.getName());
			ps.setString(3, e.getPassword());
			ps.setString(4, e.getEmail());
			ps.setString(5, e.getCity());
			status=ps.executeUpdate();
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return status;
	}
	public static List<Emp> getAllEmployees(){
		List<Emp> list=new ArrayList<Emp>();
		try {
			Connection conn=EmpDao.getConnection();
			PreparedStatement ps=conn.prepareStatement("select * from usercrud");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Emp e=new Emp();
				e.setId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setPassword(rs.getString(3));
				e.setEmail(rs.getString(4));
				e.setCity(rs.getString(5));
				list.add(e);
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static int delete(int id) {
		int status=0;
		try {
			Connection conn=EmpDao.getConnection();
			PreparedStatement ps=conn.prepareStatement("delete from usercrud where id=?");
			ps.setInt(1, id);
			status=ps.executeUpdate();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	public static Emp getEmployeeById(int id) {
		Emp e=new Emp();
		try {
			Connection conn=EmpDao.getConnection();
			PreparedStatement ps=conn.prepareStatement(" select * from usercrud where id=?");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				e.setId(rs.getString(1));
				e.setName(rs.getString(2));
				e.setPassword(rs.getString(3));
				e.setEmail(rs.getString(4));
				e.setCity(rs.getString(5));
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return e;
	}
	public static int update(Emp e) {
		int status=0;
		try {
			Connection conn=EmpDao.getConnection();
			PreparedStatement ps=conn.prepareStatement("update usercrud set name=?,password=?,email=?,city=? where id=?");
			ps.setString(1, e.getName());
			ps.setString(2, e.getPassword());
			ps.setString(3, e.getEmail());
			ps.setString(4, e.getCity());
			ps.setString(5, e.getId());
			status=ps.executeUpdate();
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return status;
	}
}


