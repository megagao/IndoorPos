package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.Employee;

public interface EmployeeManage {

	/**
	 * 查找所有员工
	 */
	public List<Employee> findAllEmp(); 
	
	/**
	 * 保存员工
	 * @param employee
	 */
	public boolean saveEmployee(Employee employee);
	
	/**
	 * 修改员工信息
	 * @param employee
	 */
	public boolean updateEmployee(Employee employee);
	
	/**
	 * 删除员工
	 * @param empId
	 */
	public boolean deleteEmployee(String empId);
	
	/**
	 * 根据员工id查询员工
	 * @param empId
	 */
	public Employee getEmployeeById(String empId);
	
}
