package com.ushi.ssm.place;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepository {
	private final NamedParameterJdbcTemplate jdbc;

	public PlaceRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<PlaceResponse> findAll() {
		return jdbc.query("select * from v_places order by created_at desc",
				(rs, i) -> new PlaceResponse(UUID.fromString(rs.getString("id")), rs.getString("name"),
						rs.getString("description"), rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lon"),
						rs.getObject("created_at", java.time.OffsetDateTime.class),
						rs.getObject("updated_at", java.time.OffsetDateTime.class)));
	}

	public Optional<PlaceResponse> findById(UUID id) {
		return jdbc.query("select * from v_places where id=:id", Map.of("id", id),
				(rs, i) -> new PlaceResponse(UUID.fromString(rs.getString("id")), rs.getString("name"),
						rs.getString("description"), rs.getString("address"), rs.getDouble("lat"), rs.getDouble("lon"),
						rs.getObject("created_at", java.time.OffsetDateTime.class),
						rs.getObject("updated_at", java.time.OffsetDateTime.class)))
				.stream().findFirst();
	}

	public UUID insert(PlaceRequest req, UUID createdBy) {
		UUID id = UUID.randomUUID();
		String sql = """
				    insert into places(id, name, description, address, geom, created_by)
				    values(:id, :name, :description, :address,
				           ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, :created_by)
				""";
		jdbc.update(sql, Map.of("id", id, "name", req.name(), "description", req.description(), "address",
				req.address(), "lat", req.lat(), "lon", req.lon(), "created_by", createdBy));
		return id;
	}

	public int update(UUID id, PlaceRequest req) {
		String sql = """
				    update places
				    set name=:name, description=:description, address=:address,
				        geom = ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
				    where id=:id
				""";
		return jdbc.update(sql, Map.of("id", id, "name", req.name(), "description", req.description(), "address",
				req.address(), "lat", req.lat(), "lon", req.lon()));
	}

	public int delete(UUID id) {
		return jdbc.update("delete from places where id=:id", Map.of("id", id));
	}
}
