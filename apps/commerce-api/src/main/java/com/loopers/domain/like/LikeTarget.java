package com.loopers.domain.like;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class LikeTarget {

	@Column(name = "target_type", nullable = false)
	private TargetType type;

	@Column(name = "target_id", nullable = false)
	private Long id;

	protected LikeTarget() {}

	public LikeTarget(final TargetType type, final Long id) {
		if (id == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "좋아요 대상 ID는 비어있을 수 없습니다.");
		}

		this.type = type;
		this.id	= id;
	}

	public enum TargetType {

		BRAND,
		PRODUCT;

		public static TargetType from(final String targetType) {
			try {
				return TargetType.valueOf(targetType);
			} catch (NullPointerException | IllegalArgumentException e) {
				throw new CoreException(ErrorType.BAD_REQUEST, "좋아요 대상 타입이 유효하지 않습니다.");
			}
		}
	}
}
