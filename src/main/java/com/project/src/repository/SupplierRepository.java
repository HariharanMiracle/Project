package com.project.src.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.src.model.AssignVehicle;
import com.project.src.model.Supplier;
import com.project.src.repository.AssignVehicleRepository.AssignVehicleRowMapper;

@Repository
public class SupplierRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class SupplierRowMapper implements RowMapper<Supplier> {
		@Override
		public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
			Supplier sup = new Supplier();
			sup.setSupId(rs.getString("supId"));
			sup.setSupName(rs.getString("supName"));
			sup.setEmail(rs.getString("email"));
			sup.setCotactNum(rs.getInt("contactNum"));
			return sup;
		}
	}
	
	public List<Supplier> supForThisProd(String proId) {
		return jdbcTemplate.query("SELECT s.* FROM supplier s WHERE s.supId IN (SELECT p.supId from prodsupby p WHERE p.prodId = '"+proId+"')", new SupplierRowMapper());
	}
	
	public String priceOfThisProd(String prodId, String supId) {
	    String sql = "SELECT cost FROM prodsupby WHERE supId=? AND prodId=?";

	    String cost = (String) jdbcTemplate.queryForObject(
	            sql, new Object[] { supId, prodId }, String.class);
		return cost;
	}
}
