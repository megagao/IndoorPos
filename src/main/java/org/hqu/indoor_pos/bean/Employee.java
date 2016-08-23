package org.hqu.indoor_pos.bean;

import java.io.Serializable;

public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	private String empId;
	
	private String name;
	
	private String sex;
	
	private String terminalId;
	
	public Employee(String empId, String name, String sex, String terminalId) {
		super();
		this.empId = empId;
		this.name = name;
		this.sex = sex;
		this.terminalId = terminalId;
	}
	
	public Employee() {
		super();
	}



	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Override
	public String toString() {
		return "该员工id为：" + empId + ", 名字是：" + name + ", 性别：" + sex
				+ ", 对应的终端id为：" + terminalId + "]";
	}
	
}
