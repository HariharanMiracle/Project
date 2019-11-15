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

import com.project.src.model.AssignVehicle;
import com.project.src.model.OrderSupplier;
import com.project.src.repository.AssignVehicleRepository;
import com.project.src.repository.OrderSupplierRepository;

@Controller
public class OrderSupplierController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OrderSupplierRepository repository;
	
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	//@ResponseBody
	public String changeStatus(@RequestParam("oid") String oid, @RequestParam("status") String status) {
		int x = repository.updStatus(oid, status);
		return "changeStatusx.jsp?msg=Change Successfull!!!";
	}
	
	@RequestMapping(value = "/trackOrder1")
	//@ResponseBody
	public String trackOrder() {		
		List<OrderSupplier> ord = repository.findAll();

				String records = "";

				int i = 0;
				for(OrderSupplier order : ord) {
					String orderId, datePlaced, dateDelivered, status, supplierId;
					Float totalPrice, discount;
					
					orderId = order.getOrderId();
					datePlaced = order.getDatePlaced();
					dateDelivered = order.getDateDelivered();
					status = order.getStatus();
					supplierId = order.getSupplierId();
					totalPrice = order.getTotalPrice();
					discount = order.getDiscount();
					String link = "<a href = changeStatus.jsp?id="+orderId+">Change Status</a>";
					records = records + "Order Id: " + orderId + "<br/>Supplier Id: " + supplierId + "<br/>Total Price: " + totalPrice + "<br/>Date Placed: " + datePlaced  + "<br/>Date Delivered: " + dateDelivered  + "<br/>Status: " + status +"<br/>Discount: " + discount + "<br/>"+link+"<br/><br/>";
					i++;
				}

				if(i == 0) {
					records = "There are no orders";
				}
				
				String url = "trackOrder.jsp?msg="+records;
						
				
		return url;
	}
	
	@RequestMapping(value = "/generateMonthlyPurchaseReport")
	//@ResponseBody
	public String generateMonthlyPurchaseReport() {		
		List<OrderSupplier> ord = repository.generateMonthlyPurchaseReport();

				String records = "";
				float total = 0;
				String morderId = "", mdatePlaced = "", mdateDelivered = "", mstatus = "", msupplierId = "";
				float mdiscount = 0;
				float mtotalPrice = 0;
				String stotalPrice = "";
				String sdiscount = "";
				int i = 0;
				for(OrderSupplier order : ord) {
					String orderId, datePlaced, dateDelivered, status, supplierId;
					float totalPrice, discount;
					
					orderId = order.getOrderId();
					datePlaced = order.getDatePlaced();
					dateDelivered = order.getDateDelivered();
					status = order.getStatus();
					supplierId = order.getSupplierId();
					totalPrice = order.getTotalPrice();
					discount = order.getDiscount();
					stotalPrice = Float.toString(totalPrice);
					sdiscount = Float.toString(discount);
					
					if(mtotalPrice < totalPrice) {
						mtotalPrice = totalPrice;
						morderId = orderId;
						mdatePlaced = datePlaced;
						mdateDelivered = dateDelivered;
						mstatus = status;
						msupplierId = supplierId;
						mdiscount = discount;
					}
					records = records + "Order Id: " + orderId + "<br/>Supplier Id: " + supplierId + "<br/>Total Price: " + stotalPrice + "<br/>Date Placed: " + datePlaced  + "<br/>Date Delivered: " + dateDelivered  + "<br/>Status: " + status +"<br/>Discount: " + sdiscount + "<br/><br/>";
					i++;
					total = total + totalPrice;
				}

				if(i == 0) {
					records = "There are no purchase for this month";
				}
				String smtotalPrice = Float.toString(mtotalPrice);
				String smdiscount = Float.toString(mdiscount);
				String sTotal =  Float.toString(total);
				String si = Integer.toString(i);
				String hello = ""
						+ "<h1>MonthlyPurchaseReport</h1>"
						+ "<br/><br/>"
						+ records
						+ "<h4>Total purchase cost: "+sTotal+"</h4>"
						+ "<h4>Number of purchase: "+si+"</h4>"
						+ "<br/>"
						+ "<h4><u>Highest paid purchase</u></h4>"
						+ "<h4>Order ID: "+morderId+"</h4>"
						+ "<h4>SupplierId: "+msupplierId+"</h4>"
						+ "<h4>Total Price: "+smtotalPrice+"</h4>"
						+ "<h4>Date Placed: "+mdatePlaced+"</h4>"
						+ "<h4>Date Delivered: "+mdateDelivered+"</h4>"
						+ "<h4>Discount: "+smdiscount+"</h4>";
				
		return "genMonthlyPurRep.jsp?msg="+hello;
	}
}
