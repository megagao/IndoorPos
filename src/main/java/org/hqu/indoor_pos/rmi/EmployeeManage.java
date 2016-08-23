package org.hqu.indoor_pos.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.hqu.indoor_pos.bean.Employee;

public interface EmployeeManage extends Remote {

	/**
	 * 查找所有员工
	 */
	public List<Employee> findAllEmp() throws RemoteException; 
	
	/**
	 * 保存员工
	 * @param employee
	 */
	public boolean saveEmployee(Employee employee) throws RemoteException;
	
	/**
	 * 修改员工信息
	 * @param employee
	 */
	public boolean updateEmployee(Employee employee) throws RemoteException;
	
	/**
	 * 删除员工
	 * @param empId
	 */
	public boolean deleteEmployee(String empId) throws RemoteException;
	
	/**
	 * 根据员工id查询员工
	 * @param empId
	 */
	public Employee getEmployeeById(String empId) throws RemoteException;
	
}
