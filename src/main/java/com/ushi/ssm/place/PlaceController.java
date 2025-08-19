package com.ushi.ssm.place;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
	private final PlaceQueryRepository repo;

	public PlaceController(PlaceQueryRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping("/nearby")
	public List<PlaceDto> nearby(@RequestParam double lat, @RequestParam double lon, @RequestParam(defaultValue = "3") double radiusKm, @RequestParam(defaultValue = "2") int limit){
	return repo.findNearby(lat, lon, radiusKm, limit);
	}
}
