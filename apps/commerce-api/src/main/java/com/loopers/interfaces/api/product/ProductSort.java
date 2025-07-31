package com.loopers.interfaces.api.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSort {
	CREATED_AT("createdAt");

	private final String description;
}
