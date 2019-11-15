package com.project.src.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.src.model.*;
import com.project.src.repository.VehicleJdbcRepository.VehicleRowMapper;

@Repository
public class EmployeeJdbcRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee employee = new Employee();
			employee.seteId(rs.getString("eId"));
			employee.setFname(rs.getString("fname"));
			employee.setLname(rs.getString("lname"));
			employee.setAddress(rs.getString("address"));
			employee.setTelephone(rs.getInt("telephone"));
			employee.setDob(rs.getString("dob"));
			
			return employee;
		}

	}

	public List<Employee> findAll() {
		return jdbcTemplate.query("select * from employee", new EmployeeRowMapper());
	}

	public Employee findById(String eId) {
		try {
			return jdbcTemplate.queryForObject("select * from employee where eId=?", new Object[] { eId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		}
		catch(Exception e) {
			return null;
		}
	}

	public int deleteById(String eId) {
		return jdbcTemplate.update("delete from employee where eId=?", new Object[] { eId });
	}

	public String insert(Employee employee) {
		String msg = "";
		try{
			int x = jdbcTemplate.update("insert into employee (`eId`, `fname`, `lname`, `address`, `telephone`, `dob`) " + "values(?, ?, ?, ?, ?, ?)",
					new Object[] { employee.geteId(), employee.getFname(), employee.getLname(), employee.getAddress(), employee.getTelephone(), employee.getDob() });
			msg = "Employee added!";
		}
		catch(Exception e) {
			msg = employee.geteId() + " already exists!";
		}
		finally{
			return msg;
		}
	}
	
}
