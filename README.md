package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		EmpDao.delete(id);
		response.sendRedirect("ViewServlet1");
	}

}

package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out =response.getWriter();
		out.println("<h1>Update Employee</h1>");
		String sid=request.getParameter("id");
		int id=Integer.parseInt(sid);
		Emp e=EmpDao.getEmployeeById(id);
		out.print("<form action='EditServlet2' method='post'>");
		out.print("<table>");
		out.print("<tr><td></td><td><input type='hidden'name='id' value='"+e.getId()+"'/></td></tr>");
		out.print("<tr><td>Name:</td><td><input type='text' name='name'"+e.getName()+"'/></td><td>");
		out.print("<tr><td>PassWord</td><td><input type='password' name='password' value='"+e.getPassword()+"'/></td></tr>");
		out.print("<tr><td>Email:</td><td><input type='email' name='email' value='"+e.getEmail()+"'/></td></tr>");
		out.print("<tr><td>City:</td><td>");
		out.print("<select name='city' style='width:150px'>");
		out.print("<option>Chennai</option>");
		out.print("<option>Trichy</option>");
		out.print("<option>Hosur</option>");
		out.print("<option>Theni</option>");
		out.print("<option>Others</option>");
		out.print("</select>");
		out.print("</td></tr>");
		out.print("<tr><td colspan='2'><input type='submit' value='Edit & Save'/></td></tr>");
		out.print("</table>");
		out.print("</form>");
		out.close();
		
	}

}

package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/EditServlet2")
public class EditServlet2 extends HttpServlet {;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String country=request.getParameter("country");
		Emp e=new Emp();
		e.setId(id);
		e.setName(name);
		e.setPassword(password);
		e.setEmail(email);
		e.setCity(country);
		int status=EmpDao.update(e);
		if(status>0) {
			response.sendRedirect("ViewServlet1");
		}else {
			out.println("Sorry! unable to Update record");
		}
		out.close();
	}

}

package pack;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

public class Emp {
	private String id;
	private String name,password,email,city;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}

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



package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/SaveServlet1")
public class SaveServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String email =request.getParameter("email");
		String country =request.getParameter("city");
		Emp e=new Emp();
		e.setId(id);
		e.setName(name);
		e.setPassword(password);
		e.setEmail(email);
		e.setCity(country);
		int status=EmpDao.save(e);
		if(status>0) {
			out.println("<p>Record Saved Successfully</p>");
			request.getRequestDispatcher("index.html").include(request, response);
		}else {
			out.println("Sorry! Unable to save Record");
		}
		out.close();
	}

}

package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet("/ViewServlet1")
public class ViewServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<a href='index.html'>Add New Employee</a>");
		out.println("<h1>Employees List</h1>");
		List<Emp> list=EmpDao.getAllEmployees();
		out.println("<table border='1' width='100%'>");
		out.println("<tr><th>ID</th><th>Name</th><th>PassWord</th><th>Email</th><th>City"
				+"</th><th>Edit</th><th>Delete</th><th>");
		for(Emp e:list) {
			out.println("<tr><td>"+e.getId()+"</td><td>"+e.getName()+"</td><td>"+e.getPassword()+
					"</td><td>"+e.getEmail()+"</td><td>"+e.getCity()+"</td><td><a href='EditServlet?id="+e.getId()+"'>Edit</a></td><td><a href='DeleteServlet?id="+e.getId()+"'>Delete</a></td>"
					+"</tr>");
		}
		out.print("</table>");
		out.close();
		
	}

}
[Upload<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Add New Employee</h1>
<form action="SaveServlet1" method="post">
<table>
<tr>
<td>Id:</td>
<td><input type="text" name="id"/>
</td>
</tr>
<tr><td>Name:</td>
<td><input type="text" name="name"/></td></tr>
<tr>
<td>PassWord:</td>
<td><input type="text" name="password">
</td>
</tr>
<tr>
<td>Email:</td>
<td><input type="text" name="email"/></td></tr>
<tr><td>City:</td>
<td><select name="city" style="width:150px">
<option>Chennai</option>
<option>Trichy</option>
<option>Hosur</option>
<option>Theni</option>
<option>Others</option>
</select>
</td>
</tr>
<tr>
<td colspan="2">
<input type="submit" value="Save Employee"/></td></tr>
</table>
<br/>
<a href="ViewServlet1">View Employees</a>
</form>
</body>
</html>ing index.html…]()

