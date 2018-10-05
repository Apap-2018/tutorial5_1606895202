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
import com.apap.tutorial4.repository.PilotDB;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
		private String addFlightSubmit(@ModelAttribute FlightModel flight) {
			flightService.addFlight(flight);
			return "add";
	}
	
	@RequestMapping(value = "/flight/delete/{flightNumber}", method = RequestMethod.GET)
	public String deletePilot(@PathVariable("flightNumber") String flightNumber) {
		FlightModel archive = flightService.findByFlightNumber(flightNumber);
		flightService.deleteFlight(archive);
	    return "delete";
	}
	/** View Pilot dengan licenseNumber **/
	
	@RequestMapping("/flight/view")
	public String view(Model model) {
		List<FlightModel> flightList = flightService.getFlightList();
		model.addAttribute("flightList", flightList);
		return "view-flight";
	}
	
	/** Update Pilot **/
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
	public String updateFlight(@PathVariable("flightNumber") String flightNumber, Model model) {
		FlightModel archive = flightService.findByFlightNumber(flightNumber);
		model.addAttribute("flight", archive);
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
	public String updateFlightSubmit(@PathVariable("id") long id, @ModelAttribute FlightModel flight) {
		FlightModel archive = flightService.findByFlightId(id);
		archive.setFlightNumber(flight.getFlightNumber());
		archive.setDestination(flight.getDestination());
		archive.setOrigin(flight.getOrigin());
		archive.setTime(flight.getTime());
		flightService.addFlight(archive);
		return "update";
	}
}