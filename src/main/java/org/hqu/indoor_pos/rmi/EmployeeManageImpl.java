package org.hqu.indoor_pos.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hqu.indoor_pos.bean.BaseStation;
import org.hqu.indoor_pos.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeManageImpl extends UnicastRemoteObject implements EmployeeManage{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public EmployeeManageImpl() throws RemoteException {
		super();
	}

	/**
	 * 查找所有员工
	 */
	@Override
	public List<Employee> findAllEmp() throws RemoteException {
		
		return this.jdbcTemplate.query("select * from employee",   
                new RowMapper<Employee>(){  
              
                    @Override  
                    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
                    	Employee employee = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
                        return employee;  
                    }  
        });  
	}

	/**
	 * 保存员工
	 * @param employee
	 */
	@Override
	public boolean saveEmployee(Employee employee) throws RemoteException {
		
		try {
			this.jdbcTemplate.update("insert into employee values(?, ?, ?, ?)",   
	                new Object[]{employee.getEmpId(), employee.getName(), employee.getSex(), employee.getTerminalId()}); 
		} catch (Exception e) {
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
	public boolean updateEmployee(final Employee employee) throws RemoteException {
		
		try {
			this.jdbcTemplate.update(  
					"update employee set name = ?, sex = ?, terminal_id = ? where emp_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1, employee.getName());
	                        ps.setString(2, employee.getSex());
	                        ps.setString(3, employee.getTerminalId());
	                        ps.setString(4, employee.getEmpId());
	                    }  
	                }  
	        );  
		} catch (Exception e) {
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
	public boolean deleteEmployee(final String empId) throws RemoteException {
		
		try {
			this.jdbcTemplate.update(  
					"delete from employee  where emp_id = ?",   
	                new PreparedStatementSetter(){  
	                    @Override  
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setString(1, empId);  
	                    }  
	                }  
	        );  
		} catch (Exception e) {
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
		
		return (Employee) this.jdbcTemplate.queryForObject(  
                "select * from employee where emp_id = ?",   
                new Object[]{empId},  
                new RowMapper<Employee>(){  
  
                    @Override  
                    public Employee mapRow(ResultSet rs,int rowNum)throws SQLException {  
                    	Employee employee = new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)); 
                        return employee;  
                    }  
        }); 
	}

}
