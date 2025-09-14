package com.ushi.ssm.place;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
public class PlaceCrudController {
	private final PlaceRepository repo;

	public PlaceCrudController(PlaceRepository repo) {
		this.repo = repo;
	}

	@GetMapping
	public List<PlaceResponse> list() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlaceResponse> get(@PathVariable UUID id) {
		return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody PlaceRequest req) {
		UUID id = repo.insert(req, null); // 認証導入時は createdBy をセット
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlaceRequest req) {
		int rows = repo.update(id, req);
		if (rows == 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(Map.of("id", id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable UUID id) {
		int rows = repo.delete(id);
		if (rows == 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}
}
