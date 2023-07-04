import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/docreg")
public class docreg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("doc_name");
		String dob=request.getParameter("dob");
		String spec=request.getParameter("doc_spec");
		String email=request.getParameter("mail");
		String user_id=request.getParameter("doc_id");
		String password=request.getParameter("doc_pass");
		String gender=request.getParameter("sex");
		String phno=request.getParameter("phno");
		HttpSession s=request.getSession();
		s.setAttribute("name",name);
		RequestDispatcher rd=request.getRequestDispatcher("patsuc1.html");
		PrintWriter out=response.getWriter();
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
				PreparedStatement ps=con.prepareStatement("insert into doc_list(dob,doc_name,specialisation,email,userid,pass,phno,gender,review_points,no_of_reviews,rating) values(?,?,?,?,?,?,?,?,0,0,0);");
				ps.setString(1,dob);
				ps.setString(2,name);
				ps.setString(3,spec);
				ps.setString(4,email);
				ps.setString(5,user_id);
				ps.setString(6,password);
				ps.setString(7,phno);
				ps.setString(8,gender);
				int rs=ps.executeUpdate();
				if(rs>0) {
					//RequestDispatcher rd=request.getRequestDispatcher("AppointmentConfirmed.html");
					//rd.forward(request, response);
					response.sendRedirect("fadmin.html");
				}
			
		}
		catch(Exception e){
		RequestDispatcher rd1=request.getRequestDispatcher("failure.html");
		rd1.forward(request,response);
				System.out.println(e);
				}  
	}
}

