package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.Location;
import com.project.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired
	LocationRepository locrepo;

	public String addLocation(Location location) {
		locrepo.save(location);
		return "Id  " +location.getLocationId() + " Location details are inserted successfully";
	}

	public List<Location> getLocations() {
		return locrepo.findAll();
	}

	public Location fetchLocById(int locationid) {
		return locrepo.findByLocationId(locationid);
	}

	public Location updateLocation(Location loc) {
		return locrepo.save(loc);
	}

	public Location deleteLocation(int locationid) {
		locrepo.deleteById(locationid);
		return null;
	}

	public Boolean deleteAllLocations() {
		locrepo.deleteAll();
		return false;
	}

}
