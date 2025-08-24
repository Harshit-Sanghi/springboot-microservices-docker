package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String productId;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public OrderStatus getStatus() {
	    return status;
	}

	public void setStatus(OrderStatus status) {
	    this.status = status;
	}
	

}
