package com.loopers.domain.order;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

	@Column(name = "ref_user_id", nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	protected Order() {}

	public Order(final Long userId, final List<OrderItem> items) {
		if (userId == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "회원 ID는 비어있을 수 없습니다.");
		}

		if (items == null || items.isEmpty()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "주문 상품은 최소 1개 이상이어야 합니다.");
		}

		this.userId = userId;
		this.status = OrderStatus.PENDING;
		items.forEach(this::addOrderItem);
	}

	public BigDecimal calculateTotalPrice() {
		return items.stream()
			.map(OrderItem::calculateTotalPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void addOrderItem(final OrderItem item) {
		items.add(item);
		item.initOrder(this);
	}

	public enum OrderStatus {
		PENDING,
		CONFIRM,
		CANCELED
	}
}
