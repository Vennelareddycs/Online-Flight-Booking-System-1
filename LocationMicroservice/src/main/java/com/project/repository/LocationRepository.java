package com.project.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Location;
@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
	Location findByLocationId(int locationId);
}