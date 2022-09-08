package edu.kh.emp.model.vo;

import java.util.Objects;

//VO(Value Object) : 값 저장용 객체 (==DB 조회 결과 한 행을 저장)
public class Employee {
	   private int empId;      			 // 사원 번호(사번)
	   private String empName; 			 // 사원 이름
	   private String empNo;    		 // 주민등록번호
	   private String email;    		 // 이메일
	   private String phone;   			 // 전화번호
	   private String departmentTitle;   // 부서명
	   private String jobName;  		 // 직급명
	   private int salary;      		 // 급여
	   
	   private String deptCode;
	   private String jobCode;
	   private String salLevel;
	   private double bonus;
	   private int managerId;
	   
	   public Employee() {}


	public Employee(int empId, String empName, String empNo, String email, String phone, String departmentTitle,
			String jobName, int salary) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empNo = empNo;
		this.email = email;
		this.phone = phone;
		this.departmentTitle = departmentTitle;
		this.jobName = jobName;
		this.salary = salary;
	}


	public Employee(int empId, String empName, String empNo, String email, String phone, int salary, String deptCode,
			String jobCode, String salLevel, double bonus, int managerId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empNo = empNo;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
		this.deptCode = deptCode;
		this.jobCode = jobCode;
		this.salLevel = salLevel;
		this.bonus = bonus;
		this.managerId = managerId;
	}


	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getEmpNo() {
		return empNo;
	}


	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getDepartmentTitle() {
		return departmentTitle;
	}


	public void setDepartmentTitle(String departmentTitle) {
		this.departmentTitle = departmentTitle;
	}


	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}

	
	public String getDeptCode() {
		return deptCode;
	}


	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getJobCode() {
		return jobCode;
	}


	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}


	public String getSalLevel() {
		return salLevel;
	}


	public void setSalLevel(String salLevel) {
		this.salLevel = salLevel;
	}


	public double getBonus() {
		return bonus;
	}


	public void setBonus(double bonus) {
		this.bonus = bonus;
	}


	public int getManagerId() {
		return managerId;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empNo=" + empNo + ", email=" + email
				+ ", phone=" + phone + ", departmentTitle=" + departmentTitle + ", jobName=" + jobName + ", salary="
				+ salary + ", deptCode=" + deptCode + ", jobCode=" + jobCode + ", salLevel=" + salLevel + ", bonus="
				+ bonus + ", managerId=" + managerId + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(bonus, departmentTitle, deptCode, email, empId, empName, empNo, jobCode, jobName, managerId,
				phone, salLevel, salary);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Double.doubleToLongBits(bonus) == Double.doubleToLongBits(other.bonus)
				&& Objects.equals(departmentTitle, other.departmentTitle) && Objects.equals(deptCode, other.deptCode)
				&& Objects.equals(email, other.email) && empId == other.empId && Objects.equals(empName, other.empName)
				&& Objects.equals(empNo, other.empNo) && Objects.equals(jobCode, other.jobCode)
				&& Objects.equals(jobName, other.jobName) && managerId == other.managerId
				&& Objects.equals(phone, other.phone) && Objects.equals(salLevel, other.salLevel)
				&& salary == other.salary;
	}

	

	
	   
	   
	   
	   
}
