import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/adminsuccess")
public class adminsuccess extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

try {
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/database_name","root","root");
			PrintWriter out=response.getWriter();
				Date date = Calendar.getInstance().getTime();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = dateFormat.format(date);
				PreparedStatement ps=con.prepareStatement("select * from appointment where date_of_appointment=?");
				ps.setString(1,strDate);
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					RequestDispatcher rd=request.getRequestDispatcher("adminsuccess.html");
					rd.include(request, response);
					out.println("<html><body text='indigo' ><table border='3' align='center'>");
					out.println("<tr><th>Patient Name</th><th>Doctor Name</th><th>Date</th><th>Time</th></tr>");
					while(rs.next()) {
						String pat=rs.getString("patient_name");
						String doc=rs.getString("doctor_name");
						String doa=rs.getString("date_of_appointment");
						String toa=rs.getString("time_of_appointment");
						out.print("<tr><td>"+pat+"</td>");
						out.print("<td>"+doc+"</td>");
						out.print("<td>"+doa+"</td>");
						out.print("<td>"+toa+"</td></tr>");
					}
					out.println("</table></body></html>");
				}
				else {
					RequestDispatcher rd=request.getRequestDispatcher("adminsuccess.html");
					rd.include(request, response);
					out.print("<center><h1>Appointments are not requested yet</h1><center>");
				}
		}
	catch(Exception e){ 
		RequestDispatcher rd=request.getRequestDispatcher("failure.html");
		rd.forward(request, response);
		}  
	}
}
