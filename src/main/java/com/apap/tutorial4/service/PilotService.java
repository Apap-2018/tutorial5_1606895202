package com.apap.tutorial4.service;

import java.util.List;

import com.apap.tutorial4.model.PilotModel;

public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	void addPilot(PilotModel pilot);
	void deletePilot(PilotModel pilot);
}
