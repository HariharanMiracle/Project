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

@Repository
public class VehicleJdbcRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class VehicleRowMapper implements RowMapper<Vehicle> {
		@Override
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Vehicle vehicle = new Vehicle();
			vehicle.setVnum(rs.getString("vnum"));
			vehicle.setModel(rs.getString("model"));
			vehicle.setDescr(rs.getString("descr"));
			vehicle.setCat(rs.getString("cat"));
			vehicle.setvAddDate(rs.getString("vAddDate"));
			vehicle.setManYear(rs.getInt("manYear"));
			return vehicle;
		}

	}

	public List<Vehicle> findAll() {
		return jdbcTemplate.query("select * from vehicle", new VehicleRowMapper());
	}

	public Vehicle findById(String id) {
		try {
			return jdbcTemplate.queryForObject("select * from vehicle where vnum=?", new Object[] { id },
					new BeanPropertyRowMapper<Vehicle>(Vehicle.class));
		}
		catch(Exception e) {
			return null;
		}
	}

	public int deleteById(String id) {
		return jdbcTemplate.update("delete from vehicle where vnum=?", new Object[] { id });
	}

	public String insert(Vehicle vehicle) {
		String msg = "";
		try{
			int x = jdbcTemplate.update("insert into vehicle (`vnum`, `model`, `descr`, `cat`, `vAddDate`, `manYear`) " + "values(?, ?, ?, ?, ?, ?)",
					new Object[] { vehicle.getVnum(), vehicle.getModel(), vehicle.getDescr(), vehicle.getCat(), vehicle.getvAddDate(), vehicle.getManYear() });
			msg = "Vehicle added!";
		}
		catch(Exception e) {
			msg = vehicle.getVnum() + " already exists!";
		}
		finally{
			return msg;
		}
	}

	public int update(Vehicle vehicle) {
		String sql = "UPDATE `vehicle` SET `model`='"+vehicle.getModel()+"',`descr`='"+vehicle.getDescr()+"',`cat`='"+vehicle.getCat()+"',`vAddDate`='"+vehicle.getvAddDate()+"',`manYear`='"+vehicle.getManYear()+"' WHERE `vnum`='"+vehicle.getVnum()+"'";
		return jdbcTemplate.update(sql);
	}

}
