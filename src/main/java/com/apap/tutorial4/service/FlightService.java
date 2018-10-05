package com.apap.tutorial4.service;

import java.sql.Date;
import java.util.List;

import com.apap.tutorial4.model.FlightModel;

public interface FlightService {
	FlightModel findByFlightNumber(String flightNumber);
	FlightModel findByFlightId(long id);
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flight);
	List <FlightModel> getFlightList(); 
}
