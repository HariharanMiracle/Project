package com.project.src.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.src.model.Product;
import com.project.src.model.Supplier;
import com.project.src.repository.ProductRepository;
import com.project.src.repository.SupplierRepository;

@Controller
public class SupplierController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SupplierRepository repository;
	
	@RequestMapping(value = "/supForThisProd", method = RequestMethod.GET)
	//@ResponseBody
	public String supForThisProd(@RequestParam("proId") String proId) {
		
		
		List<Supplier> listSup = repository.supForThisProd(proId);

				String records = "";

				int i = 0;
				for(Supplier sup : listSup) {
					String supId;
					String supName;
					int cotactNum;
					String email;
					
					supId = sup.getSupId();
					supName = sup.getSupName();
					cotactNum = sup.getCotactNum();
					email = sup.getEmail();

					String price = repository.priceOfThisProd(proId, supId);
					//String price = "500";
					//String link = "<a href = supForThisProd?id="+productId+">Suppliers</a>";
					
					records = records + "Supplier Id: " + supId + "<br/>Supplier Name: " + supName + "<br/>Contact Number: " + cotactNum + "<br/>Email: " + email  + "<br/>Price: " + price + "<br/><br/>";
					i++;
				}

				if(i == 0) {
					records = "No suppliers for this product";
				}
				
				String body = ""
						+ "<html><head></head>"
						+ "<body>"
						+ "<h1>Suppliers</h1>"
						+ records
						+ "<br/><br/>";
				
		return "supForThisPro.jsp?msg="+body;
	}
}
