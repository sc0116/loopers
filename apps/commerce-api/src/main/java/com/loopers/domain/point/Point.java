package com.loopers.domain.point;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "points")
@Entity
public class Point extends BaseEntity {

	@Column(name = "ref_user_id", nullable = false)
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

	public void use(final Long amount) {
		this.amount.use(amount);
	}
}
