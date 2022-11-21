package Controller;
import DAO.EmployeeDAO;
import Model.EmployeeData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import DAO.EmployeeDAO;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		below is default one you get
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String dept = request.getParameter("department");
		System.out.println(dept);
		
		EmployeeDAO dao = new EmployeeDAO();
		String dept_no = dao.getDept(dept);
		System.out.println(dept_no);
		
//		below line prints on browser
		PrintWriter out=response.getWriter();
//		out.println(dept_no);
		
		ArrayList<String> emp_no = dao.getEmpFromDept(dept_no);
		
		
		out.println("<table width=80% style=height:100% cellpadding=10 border=1>");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th>Sr. Num</th>");
		out.println("<th>Employee No</th>");
		out.println("<th>First Name</th>");
		out.println("<th>Last Name</th>");
		out.println("<th>Gender</th>");
		out.println("<th>Salary</th>");
		out.println("<th>Department</th>");
		out.println("<th>Hire Date</th>");
		out.println("<th>Birth Date</th>");
		
		out.println("</tr>");
		out.println("</thead>");
		
		for (int i=1,num=1;i<emp_no.size();i++,num++) {
			
			ArrayList<EmployeeData> emp = dao.getEmpInfo(emp_no.get(i), dept);
			
			if (emp==null) continue;
			
			out.println("<tr>");
			
			out.println("<td>");
			out.println(i);
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp_no.get(i));
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getFirst_name());
			out.println("</td>");
			
			
			out.println("<td>");
			out.println(emp.get(0).getLast_name());
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getGender());
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getSalary());
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getDept());
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getHire_date());
			out.println("</td>");
			
			out.println("<td>");
			out.println(emp.get(0).getBirth_date());
			out.println("</td>");
			
			
			out.println("</tr>");
			
			
		}
		
		out.println("</table>");
		
		dao.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}