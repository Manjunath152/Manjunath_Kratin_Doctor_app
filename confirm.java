import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 * Servlet implementation class confirm
 */
@WebServlet("/confirm")
public class confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		HttpSession s=request.getSession();
		String pn=(String)s.getAttribute("patname");
		String dn=request.getParameter("radion");
		s.setAttribute("dn",dn);
		String symp=request.getParameter("symp");
		s.setAttribute("symp",symp);
		String toa=request.getParameter("toa");
		s.setAttribute("toa",toa);
		String doa=request.getParameter("doa");
		s.setAttribute("doa",doa);
		String sta="Pending";
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			if(con!=null) {
				PreparedStatement ps=con.prepareStatement("insert into appointment values(?,?,?,?,?,?)");
				ps.setString(1,pn);
				ps.setString(2,dn);
				ps.setString(3,doa);
				ps.setString(4,toa);
				ps.setString(5,symp);
				ps.setString(6,sta);
				int rs=ps.executeUpdate();
				while(rs>0) {
					RequestDispatcher rd=request.getRequestDispatcher("New.html");
					rd.include(request, response);
					out.println("<center><html><body>");
					out.println("<h1>Appointment Confirmed<h1>");
					out.println("<h2>Doctor Name: "+dn);
					out.println("<br>Patient Name: "+pn);
					out.println("<br>Date of appointment: "+doa);
					out.println("<br>Time of appointment: "+toa);
					out.println("<br>Symptomps and complaints: "+symp+"</h2>");
					out.println("<form method='get' action='lastpat'><input type='submit' value='Done with Consultaion'></form>");
					out.println("</body></html></center");
					break;
					}
				
			}
			else {
				out.println("not connected");
			}
		}
		catch(Exception e){ 
			RequestDispatcher rd=request.getRequestDispatcher("failure.html");
			rd.forward(request,response);
				System.out.println(e);
				}
		
	}

}
