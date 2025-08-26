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
	public List<PlaceDto> nearby(
			@RequestParam("lat") double lat,
		    @RequestParam("lon") double lon,
		    @RequestParam(value = "radiusKm", defaultValue = "1.0") double radiusKm,
		    @RequestParam(value = "limit", defaultValue = "50") int limit){
	    if (lat < -90 || lat > 90)  throw new IllegalArgumentException("lat must be between -90 and 90");
	    if (lon < -180 || lon > 180) throw new IllegalArgumentException("lon must be between -180 and 180");
	    if (radiusKm <= 0 || radiusKm > 50) throw new IllegalArgumentException("radiusKm must be between 0 and 50");
	    if (limit <= 0 || limit > 100) throw new IllegalArgumentException("limit must be between 1 and 100");
	return repo.findNearby(lat, lon, radiusKm, limit);
	}
}
