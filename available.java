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


/**
 * Servlet implementation class available
 */
@WebServlet("/available")
public class available extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		HttpSession s=request.getSession();
		String docname=(String)s.getAttribute("docname");
		String avb="";
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			if(con==null) {
				RequestDispatcher rd=request.getRequestDispatcher("failure.html");
				rd.forward(request,response);
			}
			else if(con!=null) {
				PreparedStatement ps=con.prepareStatement("select availability from doc_list where doc_name=?");
				ps.setString(1,docname);
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					avb=rs.getString("availability");
				}
				if(avb=="no") {
					PreparedStatement ps1=con.prepareStatement("update doc_list set availability='no' where doc_name=?;");
					ps1.setString(1,docname);
					boolean r=ps1.execute();
					if(r) {
						RequestDispatcher rd=request.getRequestDispatcher("New.html");
						rd.include(request,response);
						out.println("Hello Dr."+docname+" your unavailable for consultation");
					}
					else {
						RequestDispatcher rd=request.getRequestDispatcher("New.html");
						rd.include(request,response);
						out.println("Hello Dr."+docname+" your availability has not been updated");
					}
				}
				else if(avb=="yes") {
					PreparedStatement ps1=con.prepareStatement("update doc_list set availability='no' where doc_name=?;");
					ps1.setString(1,docname);
					boolean r=ps1.execute();
					if(r) {
						RequestDispatcher rd=request.getRequestDispatcher("New.html");
						rd.include(request,response);
						out.println("Hello Dr."+docname+" your available for consultation now");
					}
					else {
						RequestDispatcher rd=request.getRequestDispatcher("New.html");
						rd.include(request,response);
						out.println("Hello Dr."+docname+" your availability has not been updated");
					}
				}
			}
			
		}
		catch(Exception e){ 
			RequestDispatcher rd2=request.getRequestDispatcher("failure.html");
			rd2.forward(request,response);
				System.out.println(e);
				}  

	}

}
