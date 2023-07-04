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
import jakarta.servlet.http.HttpSession;

@WebServlet("/docremove")
public class docremove extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("doc_name");
		String dob=request.getParameter("dob");
		String spec=request.getParameter("doc_spec");
		RequestDispatcher rd=request.getRequestDispatcher("patsuc1.html");
		PrintWriter out=response.getWriter();
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			if(con!=null) {
				PreparedStatement ps=con.prepareStatement("select * from doc_list where doc_name=? and specialisation=?");
				ps.setString(1,name);
				ps.setString(2,spec);
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					HttpSession s=request.getSession();
					out.println("<html><body text='indigo' bgcolor='pink'><table align='center'><tr><th><h1>DOCTOR'S APP</h1></th></tr></table>");
					out.println("<table align='right'><tr><td><form action='emergency.html'>");
					out.println("<input type='submit' value='Emergency'></td></tr></form></table>");
					out.println("<br><br><hr size='2' color='indigo'><table border='2'>");
					out.println("<form action='remove' method='post'>");
					out.println("<tr><th></th><th>Doctor Id</th><th>Doctor Name</th><th>Date of birth</th><th>Specialisation</th>");
					out.println("<th>E-mail id</th><th>User-Id</th><th>phone number</th><th>Gender</th>");
					out.println("<th>Total rating points</th><th>No. of ratings</th><th>Overall Rating</th></tr>");
					while(rs.next()) {
						//RequestDispatcher rd=request.getRequestDispatcher("AppointmentConfirmed.html");
						//rd.forward(request, response);
							String doci=rs.getString("doc_id"); 
							String doc=rs.getString("doc_name");
							s.setAttribute("doname",doc);
							String dob1=rs.getString("dob");
							s.setAttribute("dob",dob1);
							String speci=rs.getString("specialisation");
							s.setAttribute("specialisation",speci);
							String e_mail=rs.getString("email");
							String id=rs.getString("userid");
							s.setAttribute("userid",id);
							String pass=rs.getString("pass");
							String ph=rs.getString("phno");
							String gend=rs.getString("gender");
							String points=rs.getString("review_points");
							String no=rs.getString("no_of_reviews");
							String rating=rs.getString("rating");
							out.print("<tr><td><input type='radio' name="+doci+"/></td><td>"+doci+"</td><td>"+doc+"</td><td>"+dob1+"</td>");
							out.print("<td>"+speci+"</td><td>"+e_mail+"</td>");
							out.print("<td>"+id+"</td>");
							out.print("<td>"+ph+"</td><td>"+gend+"</td><td>"+points+"</td>");
							out.print("<td>"+no+"</td><td>"+rating+"</td>");
							out.print("</tr>");
						}
					out.println("</table><table border='0'><tr><td><input type='submit' value='Delete'></td></tr></form>");
						out.println("</table></body></html>");
				}	
				else {
					RequestDispatcher rd1=request.getRequestDispatcher("New.html");
					rd1.include(request, response);
					out.print("<table align='center'><tr><td><h1>No record of Dr."+name+" with specialisation "+spec+" was found</h1></td></tr></table>");
					out.print("<table align='center'><form action=><tr><td><input type='submit' value='LogOut'></td>");
					out.print("<td><form action=><input type='submit' value='<<GoBack'></td></tr></table>");
				}
			}
		}
		catch(Exception e){
		RequestDispatcher rd1=request.getRequestDispatcher("failure.html");
		rd1.forward(request,response);
				System.out.println(e);
				}  

	}

}


