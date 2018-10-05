package com.apap.tutorial4.repository;

import org.springframework.stereotype.Repository;

import com.apap.tutorial4.model.PilotModel;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PilotDB extends JpaRepository<PilotModel, Long> {
	PilotModel findByLicenseNumber(String licenseNumber);
}
