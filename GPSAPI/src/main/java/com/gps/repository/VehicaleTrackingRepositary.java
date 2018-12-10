package com.gps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gps.domain.VehicleTracker;

@Repository
public interface VehicaleTrackingRepositary extends JpaRepository<VehicleTracker, Long>{

}
