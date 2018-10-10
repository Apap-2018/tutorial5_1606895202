package com.apap.tutorial4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	/** Add Pilot **/
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	/** View Pilot dengan licenseNumber **/
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", archive);
		List<FlightModel> pilotFligths = archive.getPilotFlight();
		model.addAttribute("listFlight",pilotFligths);
		return "view-pilot";
	}
	
	/** Delete Pilot dengan licenseNumber **/
	
	@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	public String deletePilot(@PathVariable("licenseNumber") String licenseNumber) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.deletePilot(archive);
	    return "delete";
	}
	
	/** Update Pilot dengan licenseNumber **/
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	public String updatePilot(@PathVariable("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", archive);
		return "updatePilot";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
	public String updatePilotSubmit(@PathVariable("licenseNumber") String licenseNumber, @ModelAttribute PilotModel pilot) {
		
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		archive.setFlyhour(pilot.getFlyhour());
		archive.setName(pilot.getName());
		pilotService.addPilot(archive);
		return "update";
	}
	
	
	
}