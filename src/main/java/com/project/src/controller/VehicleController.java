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

import com.project.src.model.*;
import com.project.src.repository.*;

@Controller
public class VehicleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VehicleJdbcRepository repository;
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	//@ResponseBody
	public String insert(@RequestParam("vnum") String vnum, @RequestParam("model") String model, @RequestParam("desc") String desc, @RequestParam("cate") String cate, @RequestParam("manf") int manf) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cdate = df.format(date);
		
		String msg = repository.insert(new Vehicle(vnum, model, desc, cate, cdate, manf));
		return "add_vehicle_response.jsp?msg=" + msg;
	}
	
	@RequestMapping(value = "/editORdelete", method = RequestMethod.POST)
	//@ResponseBody
	public String insert(@RequestParam("vnum") String vnum, @RequestParam("submit") String submit) {
		Vehicle veh = findById(vnum);
		if(submit.equals("Edit")) {
			if(veh == null) {
				String msg = "There is no such vehicle";
				return "updateV_response.jsp?msg=" + msg;
			}
			else {
				String xvnum = veh.getVnum();
				String model = veh.getModel();
				String desc = veh.getDescr();
				String cate = veh.getCat();
				int manf = veh.getManYear();	
				return "update_vehicle.jsp?vnum=" +xvnum+ "&model=" +model+ "&desc=" +desc+ "&cate=" +cate+ "&manf=" +manf;
			}
		}
		else {
			String msg;
			if(veh == null) {
				msg = "There is no such vehicle";
			}
			else {
				repository.deleteById(vnum);
				msg = "deleted";
			}
			return "deleteV_response.jsp?msg=" + msg;
		}
	}
	
	@RequestMapping(value = "/findById")
	@ResponseBody
	public Vehicle findById(String vnum) {
		//logger.info("Student id 10001 -> {}", repository.findById(10001L));
		Vehicle veh = repository.findById(vnum);
		return veh;
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(@RequestParam("vnum") String vnum, @RequestParam("model") String model, @RequestParam("desc") String desc, @RequestParam("cate") String cate, @RequestParam("manf") int manf) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cdate = df.format(date);
		
		logger.info("Update 10003 -> {}", repository.update(new Vehicle(vnum, model, desc, cate, cdate, manf)));
		return "updVeh.jsp?msg=Updated Successful!!!";
	}
	
	/*
	 
	  	logger.info("Student id 10001 -> {}", repository.findById(10001L));

		logger.info("Update 10003 -> {}", repository.update(new Student(10001L, "Name-Updated", "New-Passport")));

		repository.deleteById(10002L);

		logger.info("All users -> {}", repository.findAll());
	  
	 */
	
}
