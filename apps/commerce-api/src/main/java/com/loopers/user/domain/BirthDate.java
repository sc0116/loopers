package com.loopers.user.domain;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Embeddable
public class BirthDate {

	private LocalDate birthDate;

	protected BirthDate() {}

	public BirthDate(final String birthDate) {
		if (birthDate == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 필수입니다.");
		}

		try {
			LocalDate.parse(birthDate);
		} catch (DateTimeParseException e) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다.");
		}

		if (LocalDate.parse(birthDate).isAfter(LocalDate.now())) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 미래일 수 없습니다.");
		}

		this.birthDate = LocalDate.parse(birthDate);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final BirthDate birthDate1 = (BirthDate) o;
		return Objects.equals(birthDate, birthDate1.birthDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate);
	}
}
