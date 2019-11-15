package com.project.src.controller;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.src.model.AssignVehicle;
import com.project.src.model.Product;
import com.project.src.model.Vehicle;
import com.project.src.repository.*;

@Controller
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductRepository repository;
	
	@RequestMapping(value = "/inventoryNotification")
	//@ResponseBody
	public String inventoryNotification() {
		
		
		List<Product> listPro = repository.inventoryNotification();

				String records = "";

				int i = 0;
				for(Product pro : listPro) {
					String productId;
					String productName;
					int quantity;
					double unitPrice;
					String description;
					
					productId = pro.getProductId();
					productName = pro.getProductName();
					quantity = pro.getQuantity();
					unitPrice = pro.getUnitPrice();
					description = pro.getDescription();

					String link = "<a href = supForThisProd?proId="+productId+">Suppliers</a>";
					
					records = records + "Product Id: " + productId + "<br/>Product Name: " + productName + "<br/>Quantity: " + quantity + "<br/>Unit Price: " + unitPrice  + "<br/>Description: " + description + "<br/>" + link + "<br/><br/>";
					i++;
				}

				if(i == 0) {
					records = "There are no records for this week";
				}
				
				String body = ""
						+ "<h1>Inventory level notification</h1>"
						+ records
						+ "<br/><br/>";
				
		return "inventNotifi.jsp?msg="+body;
	}
}
