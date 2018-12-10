package com.gps.action;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gps.domain.VehicleTracker;
import com.gps.exception.ResourceNotFoundException;
import com.gps.repository.VehicaleTrackingRepositary;

@RestController
@RequestMapping("/api")
public class VehicleTrackerAction {
	
	@Autowired
	VehicaleTrackingRepositary vehicleRepository;
	
	// Get All Vehicle
	@GetMapping("/vehicle")
	public List<VehicleTracker> getAllNotes() {
	    return vehicleRepository.findAll();
	}

	// Create a new Vehicle
	@PostMapping("/vehicle")
	public VehicleTracker createVehicle(@Valid @RequestBody VehicleTracker vehicle) {
	    return vehicleRepository.save(vehicle);
	}
	
	// Get a Single Vehicle
	@GetMapping("/vehicle/{id}")
	public VehicleTracker getVehicleById(@PathVariable(value = "id") Long vehicleId) {
	    return vehicleRepository.findById(vehicleId).orElseThrow(() -> new ResourceNotFoundException("VehicleTracker", "id", vehicleId));
	}

	// Update a Vehicle
	@PutMapping("/vehicle/{id}")
	public VehicleTracker updateVehicle(@PathVariable(value = "id") Long vehicleTrackerId,@Valid @RequestBody VehicleTracker vehicleDetails) {

		VehicleTracker vehicle = vehicleRepository.findById(vehicleTrackerId) .orElseThrow(() -> new ResourceNotFoundException("VehicleTracker", "id", vehicleTrackerId));

		vehicleDetails.setVehicleId(vehicle.getVehicleId());

		VehicleTracker updatedVehicle = vehicleRepository.save(vehicleDetails);
	    return updatedVehicle;
	}


	// Delete a Vehicle
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<?> deleteVehicle(@PathVariable(value = "id") Long vehicleId) {
		VehicleTracker vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new ResourceNotFoundException("VehicleTracker", "id", vehicleId));

		vehicleRepository.delete(vehicle);

	    return ResponseEntity.ok().build();
	}


}
