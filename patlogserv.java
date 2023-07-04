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


@WebServlet("/patlogserv")
public class patlogserv extends HttpServlet {
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("patid");
		String password=request.getParameter("patpass");
		String dbname="",dbpass="",patname="";
		PrintWriter out=response.getWriter();
		try{  
			Class.forName("org.h2.Driver");  
			Connection con=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/Doctor","sa","");
			if(con!=null) {
				PreparedStatement ps=con.prepareStatement("select pat_name,userid,pass from pat_list where userid=? and pass=?");
				ps.setString(1,name);
				ps.setString(2,password);
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					patname=rs.getString("pat_name");
					dbname=rs.getString("userid");
					dbpass=rs.getString("pass");
				}
				if(dbname.equals(name) && dbpass.equals(password)) {
					out.print("Welcome "+patname);
					 HttpSession s=request.getSession();
					 s.setAttribute("patname",patname);
					RequestDispatcher rd=request.getRequestDispatcher("patsuc1.html");
					rd.forward(request, response);
				}
				else {
					out.println("Check your username and password");
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
