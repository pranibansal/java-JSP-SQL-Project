package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import Model.EmployeeData;

public class EmployeeDAO {

	static final String USER = "root";
	static final String PASSWD = "pranI@98";
	
	private Connection conn = null;
	private Statement stmt = null;
	
	public EmployeeDAO() {
		makeConnection("employees");
	}
	
	
	void makeConnection(String dbName) {
		final String dbURL = "jdbc:mysql://localhost/" + dbName + 
				"?autoReconnect=TRUE&useSSL=false";
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn  = DriverManager.getConnection(dbURL, USER, PASSWD);
			stmt = conn.createStatement();
			System.out.println("Database Connected");
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void close() {
		
		try {
			if (stmt!=null) stmt.close();
			if (conn!=null) conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	List<Map<String,Object>> runSQL(String sql) {
		
		if (stmt == null) return null;
		
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
		
		try {
			ResultSet rs = stmt.executeQuery(sql);
			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int size = metaData.getColumnCount();
			
//			dont get confused it says the size
//			System.out.println(metaData);
			System.out.println(size);
			
			while (rs.next()) {
				Map<String,Object> row = new HashMap<String,Object>();
//				note that this starts from one
				for (int i=1;i<=size;i++) {
//					System.out.println(metaData.getColumnName(i)+ rs.getObject(i));
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				res.add(row);
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	public String getDept(String dept) {
		
		String sql = "SELECT dept_no from departments WHERE dept_name="+
				"\"" + dept + "\"" + ";";
		List<Map<String,Object>> res = runSQL(sql);
		if (res.size()==0) return null;
		
		return (String) res.get(0).get("dept_no");
	}

		
	public ArrayList<String> getEmpFromDept(String dept_no){
		
		String sql = "select emp_no from dept_emp where dept_no="+ 
					"\"" + dept_no + "\"" + ";"; 
		List<Map<String,Object>> res = runSQL(sql);
		if (res.size()==0) return null;
		
		ArrayList<String> data = new ArrayList<String>();
		
		for (int i=0;i<res.size();i++) {
			Map<String,Object> row = res.get(i);
//			key is attribute's name
			Object emp_no = (Object) row.get("emp_no");
			data.add(emp_no.toString());
		}
		return data;
		
	}

	public ArrayList<EmployeeData> getEmpInfo(String emp_no, String dept){
		
		String sql = "select * from employees where emp_no=" +
					"\"" + emp_no + "\"" + ";";
		
		List<Map<String,Object>> res = runSQL(sql);
		if (res.size()==0) return null;
		
		
		sql = "select salary from salaries where emp_no = " + 
				"\"" + emp_no + "\"" + "and year(to_date)=9999"+";";
		
		List<Map<String,Object>> res2 = runSQL(sql);
		if (res2.size()==0) return null;
		
		ArrayList<EmployeeData> data = new ArrayList<EmployeeData>();
		
		
		for (int i=0;i<res2.size();i++) {
			Map<String,Object> row=res.get(i);
			EmployeeData emp = new EmployeeData();
			
			emp.setEmp_no(emp_no);
			emp.setDept(dept);
			
			Object first_name = (Object) row.get("first_name");
			emp.setFirst_name(first_name.toString());
			
			Object last_name = (Object) row.get("last_name");
			emp.setLast_name(last_name.toString());
			
			Object gender = (Object) row.get("gender");
			emp.setGender(gender.toString());
			
			Object hire_date = (Object) row.get("hire_date");
			emp.setHire_date(hire_date.toString());
			
			Object birth_date = (Object) row.get("birth_date");
			emp.setBirth_date(birth_date.toString());
			
			row = res2.get(i);
			
			Object salary = (Object) row.get("salary");
			emp.setSalary(salary.toString());
			
			data.add(emp);
			
		}
		
		return data;
		
	}
	


}
