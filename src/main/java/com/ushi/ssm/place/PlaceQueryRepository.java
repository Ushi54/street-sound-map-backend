package com.ushi.ssm.place;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceQueryRepository {

	private final NamedParameterJdbcTemplate jdbc;
	
	public PlaceQueryRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public List<PlaceDto> findNearby(double lat, double lon, double radiusKm, int limit){
		String sql = """
				select p.id, p.name, p.description, p.address,
              ST_Distance(
                p.geom,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
              ) / 1000.0 as distance_km
            from public.places p
            where ST_DWithin(
              p.geom,
              ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
              :radiusKm * 1000.0
            )
            order by distance_km
            limit :limit
				""";
		
		var params = Map.of("lat", lat, "lon", lon, "radiusKm", radiusKm, "limit", limit);
			return jdbc.query(sql, params, (rs, i) ->
			new PlaceDto(
					UUID.fromString(rs.getString("id")),
	                rs.getString("name"),
	                rs.getString("description"),
	                rs.getString("address"),
	                rs.getDouble("distance_km")
	        )
		);
	}
}
