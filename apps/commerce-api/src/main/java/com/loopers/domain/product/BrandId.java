package com.loopers.domain.product;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class BrandId {

	@Column(name = "ref_brand_id", nullable = false)
	private Long brandId;

	protected BrandId() {}

	public BrandId(final Long brandId) {
		if (brandId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "브랜드 ID는 비어있을 수 없습니다.");
		}

		this.brandId = brandId;
	}
}
