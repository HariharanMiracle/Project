package com.project.src.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.src.model.*;

@Repository
public class AssignVehicleRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class AssignVehicleRowMapper implements RowMapper<AssignVehicle> {
		@Override
		public AssignVehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssignVehicle assignVehicle = new AssignVehicle();
			assignVehicle.setVnum(rs.getString("vnum"));
			assignVehicle.seteId(rs.getString("eId"));
			assignVehicle.setPurpose(rs.getString("purpose"));
			assignVehicle.setSdate(rs.getString("sdate"));
			assignVehicle.setEdate(rs.getString("edate"));
			assignVehicle.setShrs(rs.getInt("shrs"));
			assignVehicle.setSmins(rs.getInt("smins"));
			assignVehicle.setEhrs(rs.getInt("ehrs"));
			assignVehicle.setEmins(rs.getInt("emins"));
			return assignVehicle;
		}
	}
	
	public List<AssignVehicle> findAll() {
		return jdbcTemplate.query("select * from assign", new AssignVehicleRowMapper());
	}
	
	public List<AssignVehicle> generateWeeklyTransportationReport(String aDate, String bDate, String cDate, String dDate, String eDate, String fDate, String gDate, String hDate) {
		return jdbcTemplate.query("SELECT * FROM `assign` WHERE sdate = '"+aDate+"' or sdate = '"+bDate+"' or sdate = '"+cDate+"' or sdate = '"+dDate+"' or sdate = '"+eDate+"' or sdate = '"+fDate+"' or sdate = '"+gDate+"' or sdate = '"+hDate+"'", new AssignVehicleRowMapper());
	}
	
	public String insert(AssignVehicle assignVehicle) {
		
		String msg = "";
		Vehicle veh;
		Employee emp;
		try {
			emp = jdbcTemplate.queryForObject("select * from employee where eId=?", new Object[] { assignVehicle.geteId() },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		}
		catch(Exception e) {
			emp = null;
		}
		
		try {
			veh = jdbcTemplate.queryForObject("select * from vehicle where vnum=?", new Object[] { assignVehicle.getVnum() },
					new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		}
		catch(Exception e) {
			veh = null;
		}
		
		if(veh == null) {
			msg = "Vehicle id is incorrect";
		}
		else if(emp == null) {
			msg = "Employee id is incorrect";
		}
		else {
			try {
				int x = jdbcTemplate.update("insert into assign (`vnum`, `eId`, `purpose`, `sdate`, `edate`, `shrs`, `smins`, `ehrs`, `emins`) " + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
						new Object[] { assignVehicle.getVnum(), assignVehicle.geteId(), assignVehicle.getPurpose(), assignVehicle.getSdate(), assignVehicle.getEdate(), assignVehicle.getShrs(), assignVehicle.getSmins(), assignVehicle.getEhrs(), assignVehicle.getEmins() });		
				
			}
			catch(Exception e) {
				msg = "Record already added!!!";
				return msg;
			}
			msg = "Vehicle successfully assigned!!!";
		}
		return msg;

	}

}
