package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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
}
