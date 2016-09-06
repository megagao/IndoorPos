package org.hqu.indoor_pos.rmi;

import java.util.List;

import org.hqu.indoor_pos.bean.Employee;

/**
 * created on 2016年8月26日
 *
 * 员工管理服务接口
 *
 * @author  megagao
 * @version  0.0.1
 */
public interface EmployeeManage {

	/**
	 * 查找所有员工
	 *
	 * @return  所有员工信息
	 */
	public List<Employee> findAllEmp(); 
	
	/**
	 * 保存员工
	 *
	 * @param  employee
	 * @return  是否成功
	 */
	public boolean saveEmployee(Employee employee);
	
	/**
	 * 修改员工信息
	 *
	 * @param  employee
	 * @return  是否成功
	 */
	public boolean updateEmployee(Employee employee);
	
	/**
	 * 删除员工
	 *
	 * @param  empId
	 * @return  是否成功
	 */
	public boolean deleteEmployee(String empId);
	
	/**
	 * 根据员工id查询员工
	 *
	 * @param  empId
	 * @return  员工信息
	 */
	public Employee getEmployeeById(String empId);
	
}
