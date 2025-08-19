package com.ushi.ssm.place;

import java.util.UUID;

public record PlaceDto(
    UUID id,
    String name,
    String description,
    String address,
    double distanceKm
) {}
