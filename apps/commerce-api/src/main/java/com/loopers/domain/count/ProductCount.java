package com.loopers.domain.count;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Table(name = "product_count")
@Entity
public class ProductCount extends BaseEntity {

	@Column(name = "ref_product_id")
	private Long productId;

	@AttributeOverride(name = "count", column = @Column(name = "like_count"))
	@Embedded
	private Count likeCount;

	protected ProductCount() {}

	public ProductCount(final Long productId) {
		if (productId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "상품 ID는 비어있을 수 없습니다.");
		}

		this.productId = productId;
		this.likeCount = new Count(0L);
	}

	public void increment(final CountType type) {
		switch (type) {
			case LIKE -> likeCount.increment();
		}
	}

	public void decrement(final CountType type) {
		switch (type) {
			case LIKE -> likeCount.decrement();
		}
	}

	@Getter
	@EqualsAndHashCode
	@Embeddable
	public static class Count {

		private Long count;

		protected Count() {}

		public Count(final Long count) {
			if (count == null) {
				throw new CoreException(ErrorType.BAD_REQUEST, "카운트는 비어있을 수 없습니다.");
			}
			if (count < 0) {
				throw new CoreException(ErrorType.BAD_REQUEST, "카운트는 음수일 수 없습니다.");
			}

			this.count = count;
		}

		public void increment() {
			this.count += 1;
		}

		public void decrement() {
			if (this.count == 0) {
				throw new CoreException(ErrorType.BAD_REQUEST, "카운트를 감소시킬 수 없습니다.");
			}

			this.count -= 1;
		}
	}

	public enum CountType {
		LIKE
	}
}
