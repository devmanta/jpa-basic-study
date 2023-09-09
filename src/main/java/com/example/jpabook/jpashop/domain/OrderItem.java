package com.example.jpabook.jpashop.domain;

import com.example.jpabook.global.BaseEntity;
import com.example.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    @Builder
    public OrderItem(Item item, Order order, int orderPrice, int count) {
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem createOrderItem(Item item, int orderPrice, int count) {
        item.removeStock(count);

        return OrderItem.builder()
                .item(item)
                .orderPrice(orderPrice)
                .count(count)
                .build();
    }

    public void cancel() {
        this.item.addStock(this.count);
    }

    public int getTotalPrice() {
        // order price might be diff from item price (discount etc)
        return this.orderPrice * this.count;
    }

}
