package com.loopers.domain.point;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Amount {

	private Long amount;

	protected Amount() {}

	public Amount(final Long amount) {
		if (amount < 0) {
			throw new CoreException(ErrorType.BAD_REQUEST, "금액은 0 이상이어야 합니다.");
		}

		this.amount = amount;
	}

	public void charge(final Long amount) {
		if (amount < 1) {
			throw new CoreException(ErrorType.BAD_REQUEST, "충전할 금액은 1 이상이어야 합니다.");
		}

		this.amount += amount;
	}

	public Long getAmount() {
		return amount;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Amount amount1 = (Amount) o;
		return Objects.equals(amount, amount1.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}
}
