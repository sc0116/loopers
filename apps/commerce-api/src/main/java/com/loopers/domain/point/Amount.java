package com.loopers.domain.point;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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

	public void use(final Long amount) {
		if (this.amount < amount) {
			throw new CoreException(ErrorType.BAD_REQUEST, "금액이 부족하여 차감할 수 없습니다.");
		}

		this.amount -= amount;
	}
}
