package com.ushi.ssm.place;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PlaceResponse(UUID id, String name, String description, String address, double lat, double lon,
		OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
