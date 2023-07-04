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
 * Servlet implementation class docserv
 */
@WebServlet("/docserv")
public class docserv extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("docid");
		String password=request.getParameter("docpass");
		String dbname="",dbpass="",docname="";
		PrintWriter out=response.getWriter();
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			if(con==null) {
				RequestDispatcher rd=request.getRequestDispatcher("failure.html");
				rd.forward(request,response);
			}
			else if(con!=null) {
				PreparedStatement ps=con.prepareStatement("select doc_name,userid,pass from doc_list where userid=? and pass=?");
				ps.setString(1,name);
				ps.setString(2,password);
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
						docname=rs.getString("doc_name");
						dbname=rs.getString("userid");
						dbpass=rs.getString("pass");
					if(dbname.equals(name) && dbpass.equals(password)) {
						HttpSession s=request.getSession();
						s.setAttribute("docname",docname);
						RequestDispatcher rd=request.getRequestDispatcher("docsucess");
						rd.forward(request, response);
					}
					else if(!dbname.equals(name) || !dbpass.equals(password)) {
						RequestDispatcher rd=request.getRequestDispatcher("doc.html");
						rd.include(request, response);
						out.println("<center><font color='red'>Check your username and password</font></center>");
					}
				}
				else {
					RequestDispatcher rd=request.getRequestDispatcher("doc.html");
					rd.include(request,response);
					out.print("<center><font color='red'>No Records Found..!<br>Please contact Website Administrator</font></center>");
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


