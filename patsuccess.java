import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class patsuccess
 */
@WebServlet("/patsuccess")
public class patsuccess extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String st1=request.getParameter("doctor");
		PrintWriter out=response.getWriter();
		String st="";
		try {
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			Date d=new Date();
			ArrayList<String> hs=new ArrayList<String>();
			if(con!=null) {
				PreparedStatement ps=con.prepareStatement("select doc_name from doc_list where specialisation=? and availability='yes'");
				ps.setString(1,st1);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					st=rs.getString("doc_name");
					hs.add(st);
				}
				if(hs.size()>0){
					HttpSession s=request.getSession();
					RequestDispatcher rd=request.getRequestDispatcher("patsuc1.html");
					rd.include(request, response);
					out.println("<table border='0'>");
					out.println("<tr><th>Doctor Name</th></tr>");
					out.println("<form method='post' action='confirm'>");
					for(int i=0;i<hs.size();i++) {
						out.println("<tr><td><input type='radio' name='radion' value="+hs.get(i)+">"+hs.get(i)+"</td></tr>");
					}
					out.print("<tr><td><input type='date' name='doa' value="+d+" min="+d+" max='2020-12-31'</td></tr>");
					out.print("<tr><td>Symptoms and complaints</td><td><textarea required name='symp' rows='10' cols='25'></textarea></td></tr>");
					out.print("<tr><td>Appointment time</td><td>");
					out.print("<select required name=toa>");
					out.print("<option value='null'>Select</option>");
					out.print("<option value='10AM'>10 AM-11 AM</option>");
					out.print("<option value='11AM'>11 AM-12 NOON</option>");
					out.print("<option value='12NOON'>12 NOON-1 PM</option>");
					out.print("<option value='2PM'>2 PM-3 PM</option>");
					out.print("<option value='3PM'>3 PM-4 PM</option>");
					out.print("<option value='4PM'>4 PM-5 PM</option>");
					out.print("</select>&nbsp;1 PM-2 PM(LUNCH BREAK);</td></tr>");
					out.print("<tr><td><input type='submit' value='Book Appointment'></td></tr>");
					out.println("</form></table></body></html>");
			}
				else {
					RequestDispatcher rd=request.getRequestDispatcher("patsuc1.html");
					rd.include(request,response);
					out.println("<h1>"+st1+" are currently unavailable</h1>");
				}
			}
			else {
				RequestDispatcher rd=request.getRequestDispatcher("failure.html");
				rd.forward(request,response);
			}
		}
		catch(Exception e){
			RequestDispatcher rd=request.getRequestDispatcher("failure.html");
			rd.forward(request,response);
				System.out.println(e);
				}  

	}

}
