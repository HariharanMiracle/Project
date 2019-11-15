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
import com.project.src.repository.ProductRepository.ProductRowMapper;

@Repository
public class ProductRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class ProductRowMapper implements RowMapper<Product> {
		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setProductId(rs.getString("productId"));
			product.setProductName(rs.getString("productName"));
			product.setQuantity(rs.getInt("quantity"));
			product.setUnitPrice(rs.getDouble("unitPrice"));
			product.setDescription(rs.getString("description"));
			return product;
		}

	}
	
	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", new ProductRowMapper());
	}
	
	public List<Product> inventoryNotification() {
		return jdbcTemplate.query("select * from products where quantity < 10 ORDER BY quantity ASC", new ProductRowMapper());
	}
	
}
