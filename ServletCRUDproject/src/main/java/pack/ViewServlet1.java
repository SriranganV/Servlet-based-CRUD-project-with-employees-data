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
