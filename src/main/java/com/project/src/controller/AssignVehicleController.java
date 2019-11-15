package com.project.src.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.src.model.*;
import com.project.src.repository.*;

@Controller
public class AssignVehicleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AssignVehicleRepository arepository;
	
	@RequestMapping(value = "/assignVeh", method = RequestMethod.POST)
	//@ResponseBody
	public String insert(@RequestParam("vnum") String vnum, @RequestParam("did") String did, @RequestParam("pur") String pur, @RequestParam("sdate") String sdate, @RequestParam("shrs") int shrs, @RequestParam("smins") int smins, @RequestParam("edate") String edate, @RequestParam("ehrs") int ehrs, @RequestParam("emins") int emins) {		
		String msg = arepository.insert(new AssignVehicle(vnum, did, pur, sdate, edate, shrs, smins, ehrs, emins));
		return "assign_response.jsp?msg=" + msg;
	}
	
	@RequestMapping(value = "/weeklyReport"	)
	//@ResponseBody
	public String weeklyTransportationReport() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String cdate = df.format(date);
		
		String partDate = cdate.substring(0, 8);
		int day = Integer.parseInt(cdate.substring(8,10));
		
		String d1, d2, d3, d4, d5, d6, d7, d8;
		
		if(day >= 1 && day < 9) {
			d1 = partDate + "01";
			d2 = partDate + "02";
			d3 = partDate + "03";
			d4 = partDate + "04";
			d5 = partDate + "05";
			d6 = partDate + "06";
			d7 = partDate + "07";
			d8 = partDate + "08";
		}
		else if(day >= 9 && day < 17) {
			d1 = partDate + "09";
			d2 = partDate + "10";
			d3 = partDate + "11";
			d4 = partDate + "12";
			d5 = partDate + "13";
			d6 = partDate + "14";
			d7 = partDate + "15";
			d8 = partDate + "16";
		}
		else if(day >= 17 && day < 24) {
			d1 = partDate + "17";
			d2 = partDate + "18";
			d3 = partDate + "19";
			d4 = partDate + "20";
			d5 = partDate + "21";
			d6 = partDate + "22";
			d7 = partDate + "23";
			d8 = partDate + "24";
		}
		else {
			d1 = partDate + "25";
			d2 = partDate + "26";
			d3 = partDate + "27";
			d4 = partDate + "28";
			d5 = partDate + "29";
			d6 = partDate + "30";
			d7 = partDate + "31";
			d8 = partDate + "32";
		}
		
		List<AssignVehicle> listVeh = arepository.generateWeeklyTransportationReport(d1, d2, d3, d4, d5, d6, d7, d8);
		//List<AssignVehicle> listVeh = arepository.findAll();

				String records = "";

				int i = 0;
				for(AssignVehicle veh : listVeh) {
					String vnum, eId, purpose, sdate, edate, shrs, ehrs, smins, emins;
					
					vnum = veh.getVnum();
					eId = veh.geteId();
					purpose = veh.getPurpose();
					sdate = veh.getSdate();
					edate = veh.getEdate();
					shrs = Integer.toString(veh.getShrs());
					ehrs = Integer.toString(veh.getEhrs());
					smins = Integer.toString(veh.getSmins());
					emins = Integer.toString(veh.getEmins());
					records = records + "Vehicle Number: " + vnum + "<br/>Employee Id: " + eId + "<br/>Purpose: " + purpose + "<br/>Starting date: " + sdate  + "<br/>Ending date: " + edate  + "<br/>Starting time: " + shrs + ":" + smins  + "<br/>Ending time: " + ehrs + ":" + emins + "<br/><br/>";
					i++;
				}

				if(i == 0) {
					records = "There are no records for this week";
				}
				
				String url = "WeeklyReport.jsp?msg="+records;
				return url;
	}
	
}
