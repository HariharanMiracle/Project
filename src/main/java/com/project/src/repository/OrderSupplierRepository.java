package com.project.src.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.src.model.AssignVehicle;
import com.project.src.model.OrderSupplier;
import com.project.src.repository.AssignVehicleRepository.AssignVehicleRowMapper;

@Repository
public class OrderSupplierRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class OrderSupplierRowMapper implements RowMapper<OrderSupplier> {
		@Override
		public OrderSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderSupplier ord = new OrderSupplier();
			ord.setOrderId(rs.getString("orderId"));
			ord.setTotalPrice(rs.getFloat("totalPrice"));
			ord.setDatePlaced(rs.getString("datePlaced"));
			ord.setDateDelivered(rs.getString("dateDelivered"));
			ord.setStatus(rs.getString("status"));
			ord.setDiscount(rs.getFloat("discount"));
			ord.setSupplierId(rs.getString("supplierId"));
			return ord;
		}
	}
	
	public List<OrderSupplier> findAll() {
		return jdbcTemplate.query("select * from order_to_suppliers", new OrderSupplierRowMapper());
	}
	
	public int updStatus(String oid, String status) {
		String sql = "UPDATE `order_to_suppliers` SET `status`='"+status+"' WHERE `orderId`='"+oid+"'";
		return jdbcTemplate.update(sql);
	}
	
	public List<OrderSupplier> generateMonthlyPurchaseReport() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cdate = df.format(date);
		String xcdate = cdate.substring(0, 8);
		return jdbcTemplate.query("select * from order_to_suppliers where dateDelivered LIKE '"+xcdate+"%'", new OrderSupplierRowMapper());
	}
}
