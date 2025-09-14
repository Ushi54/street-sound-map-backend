package com.ushi.ssm.place;

public record PlaceRequest(String name, String description, String address, double lat, double lon) {
}
