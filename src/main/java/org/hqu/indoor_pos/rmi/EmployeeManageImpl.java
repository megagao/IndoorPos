package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hqu.indoor_pos.bean.Employee;
import org.hqu.indoor_pos.util.DBUtil;

public class EmployeeManageImpl extends UnicastRemoteObject implements EmployeeManage{

	private static final long serialVersionUID = 1L;
	
	private Connection conn = DBUtil.getConnection();

	public EmployeeManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有员工
	 */
	@Override
	public List<Employee> findAllEmp() throws RemoteException {
		
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from employee");
			ResultSet rs = stat.executeQuery();
			while(rs.next()){
				Employee employee = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	/**
	 * 保存员工
	 * @param employee
	 */
	@Override
	public boolean saveEmployee(Employee employee) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("insert into employee values(?,?,?,?)");
			stat.setString(1, employee.getEmpId());
			stat.setString(2, employee.getName());
			stat.setString(3, employee.getSex());
			stat.setString(4, employee.getTerminalId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改员工信息
	 * @param employee
	 */
	@Override
	public boolean updateEmployee(Employee employee) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("update employee set name = ?, sex = ?, terminal_id = ? where emp_id = ?");
			stat.setString(1, employee.getName());
			stat.setString(2, employee.getSex());
			stat.setString(3, employee.getTerminalId());
			stat.setString(4, employee.getEmpId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除员工
	 * @param empId
	 */
	@Override
	public boolean deleteEmployee(String empId) throws RemoteException {
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("delete from employee  where emp_id = ?");
			stat.setString(1,empId);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据员工id查询员工
	 * @param empId
	 */
	@Override
	public Employee getEmployeeById(String empId) throws RemoteException {
		
		Employee employee = null;
		
		try {
			PreparedStatement stat; 
			stat = conn.prepareStatement("select * from employee where emp_id = ?");
			stat.setString(1, empId);
			ResultSet rs = stat.executeQuery();
			rs.next();
			employee = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

}
