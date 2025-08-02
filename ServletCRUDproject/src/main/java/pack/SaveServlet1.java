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
