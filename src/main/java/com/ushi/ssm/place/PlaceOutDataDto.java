package com.ushi.ssm.place;

import java.util.UUID;

public record PlaceOutDataDto(
		// 場所ID
		UUID id,
		// 場所の名前
		String name,
		// 説明文（場所の特徴や詳細の説明）
		String description,
		// 住所
		String address,
		// 検索地点からの距離（km単位）
		double distanceKm) {
}
