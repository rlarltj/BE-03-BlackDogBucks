package com.prgrms.bdbks.domain.payment.entity;

import static com.google.common.base.Preconditions.*;
import static com.prgrms.bdbks.domain.card.entity.Card.*;
import static com.prgrms.bdbks.domain.payment.entity.PaymentStatus.*;
import static javax.persistence.EnumType.*;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.prgrms.bdbks.common.domain.AbstractTimeColumn;
import com.prgrms.bdbks.domain.card.entity.Card;
import com.prgrms.bdbks.domain.order.entity.Order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends AbstractTimeColumn {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "payment_id")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	private Order order;

	private String cardId;

	@Enumerated(value = STRING)
	private PaymentType paymentType;

	@Column(nullable = false)
	private int price;

	private LocalDateTime paymentDateTime;

	@Enumerated(value = STRING)
	private PaymentStatus paymentStatus;

	@Builder
	protected Payment(Order order, String cardId, PaymentType paymentType, int price, LocalDateTime paymentDateTime) {
		validateCardId(cardId);
		validatePrice(price);
		validatePaymentType(paymentType);
		validatePaymentDateTime(paymentDateTime);

		this.order = order;
		this.cardId = cardId;
		this.paymentType = paymentType;
		this.price = price;
		this.paymentDateTime = paymentDateTime;
		this.paymentStatus = APPROVE;
	}

	private static void validateChargeAmount(int amount) {
		checkArgument(MIN_CHARGE_PRICE <= amount && amount <= MAX_CHARGE_PRICE, "충전 금액은 10,000~550,000원 까지 가능합니다.");
	}

	public static Payment createChargePayment(String cardId, int price) {
		validateChargeAmount(price);

		return Payment.builder()
			.paymentType(PaymentType.CHARGE)
			.price(price)
			.paymentDateTime(LocalDateTime.now())
			.cardId(cardId)
			.build();
	}

	public static Payment createOrderPayment(Order order, Card card, int price) {
		return Payment.builder()
			.paymentType(PaymentType.ORDER)
			.order(order)
			.price(price)
			.paymentDateTime(LocalDateTime.now())
			.cardId(card.getId())
			.build();
	}

	private void validatePaymentType(PaymentType paymentType) {
		checkNotNull(paymentType, "결제 타입을 입력해주세요");
	}

	//TODO 주문 금액과 충전 카드의 금액을 비교해야 함(결제 가능한 경우 충전카드의 메소드 호출)

	private void validatePrice(int price) {
		checkArgument(price >= 0, "결제 금액은 0원부터 가능합니다.");
	}

	private void validateCardId(String cardId) {
		checkNotNull(cardId, "카드 아이디를 입력해주세요");
	}

	private void validatePaymentDateTime(LocalDateTime paymentDateTime) {
		checkNotNull(paymentDateTime, "결제 시간을 입력해주세요");
	}
}