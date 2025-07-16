package com.loopers.domain.point;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "points")
@Entity
public class Point extends BaseEntity {

	private Long userId;

	private Long amount;

	protected Point() {}

	public Point(
		final Long userId,
		final Long amount
	) {
		if (amount < 1) {
			throw new CoreException(ErrorType.BAD_REQUEST, "금액은 양수여야 합니다.");
		}

		this.userId = userId;
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getAmount() {
		return amount;
	}
}
