package com.loopers.domain.point;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "points")
@Entity
public class Point extends BaseEntity {

	private Long userId;

	@Embedded
	private Amount amount;

	protected Point() {}

	public Point(
		final Long userId,
		final Amount amount
	) {
		this.userId = userId;
		this.amount = amount;
	}

	public void charge(final Long amount) {
		this.amount.charge(amount);
	}

	public Long getUserId() {
		return userId;
	}

	public Amount getAmount() {
		return amount;
	}
}
