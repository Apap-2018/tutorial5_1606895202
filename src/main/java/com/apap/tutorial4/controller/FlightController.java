package com.apap.tutorial4.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		ArrayList<FlightModel> flightList = new ArrayList<>();
		pilot.setPilotFlight(flightList);
		flightList.add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() ==  null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		pilot.getPilotFlight().add(new FlightModel());
	    model.addAttribute("pilot", pilot);
	    return "addFlight";

	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params="save")
		private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(archive);
			flightService.addFlight(flight);
		}
		return "add";
	}
	
	
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	public String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight);
		}
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