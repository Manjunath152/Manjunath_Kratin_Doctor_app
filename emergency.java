import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class emergency
 */
@WebServlet("/emergency")
public class emergency extends HttpServlet {
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String city=request.getParameter("city");
		String area=request.getParameter("area");
		try {
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			PreparedStatement ps=con.prepareStatement("select * from hospital_list where city=?");
			ps.setString(1,city);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				out.print("<html><head><title>Doctor's Application</title></head><body bgcolor='pink' text='indigo'>");
				out.print("<table align='center'><tr><td><h1>DOCTOR'S APP</h1></td></tr></table>");
				out.print("<hr size='2' color='indigo'></body></html>");
				String st=rs.getString("name");
				String add=rs.getString("address");
				String spec=rs.getString("spec");
				out.println("<b>Hospital Name:</b>"+st+"<br>");
				out.println("<b>Hospital Addresss:</b>"+add+","+city+"<br>");
				out.println("<b>Specialist available:</b>"+spec+"<br><br><br>");
			}
			
		}
		catch(Exception e){ 
			//response.sendRedirect("failure.html");
			RequestDispatcher rd=request.getRequestDispatcher("failure.html");
			rd.forward(request,response);
				System.out.println(e);
		}
	}

}
