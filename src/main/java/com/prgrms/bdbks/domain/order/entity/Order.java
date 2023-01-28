package com.prgrms.bdbks.domain.order.entity;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.prgrms.bdbks.common.domain.AbstractTimeColumn;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders", indexes = @Index(name = "user_id_index", columnList = "user_id"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AbstractTimeColumn {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "order_id_generator")
	@GenericGenerator(name = "order_id_generator", strategy = "com.prgrms.bdbks.domain.order.repository.OrderIdGenerator")
	private String id;

	// 추후 객체 참조로 변경
	@Column(name = "coupon_id")
	private Long coupon;

	@NotNull
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@NotNull
	@Column(name = "store_id", nullable = false)
	private String storeId;

	@NotNull
	@Column(name = "total_price", nullable = false)
	private int totalPrice;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	protected Order(Long coupon, Long userId, String storeId, int totalPrice) {
		checkNotNull(userId, "userId 는 null 일 수 없습니다.");
		checkNotNull(storeId, "storeId 는 null 일 수 없습니다.");
		checkArgument(totalPrice >= 0, "totalPrice 는 0보다 작을 수 없습니다.");
		this.coupon = coupon;
		this.userId = userId;
		this.storeId = storeId;
		this.totalPrice = totalPrice;
	}

}
