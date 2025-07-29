package com.loopers.domain.like;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "like")
@Entity
public class Like extends BaseEntity {

	private Long userId;

	@Embedded
	private LikeTarget target;

	protected Like() {}

	public Like(final Long userId, final LikeTarget target) {
		if (userId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "회원 ID는 비어있을 수 없습니다.");
		}

		this.userId = userId;
		this.target = target;
	}
}
