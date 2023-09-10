package com.example.jpabook.jpashop.service;

import com.example.jpabook.jpashop.domain.Address;
import com.example.jpabook.jpashop.domain.Member;
import com.example.jpabook.jpashop.domain.Order;
import com.example.jpabook.jpashop.domain.enumtype.OrderStatus;
import com.example.jpabook.jpashop.domain.item.Book;
import com.example.jpabook.jpashop.exception.NotEnoughStockException;
import com.example.jpabook.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class) // == @RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void create_order() throws Exception {
        Member member = getMember();

        int price = 10000;
        int stockQuantity = 10;
        Book item = getItem("JPA Book", price, stockQuantity);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(price * orderCount);
        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity - orderCount);
    }

    @Test
    void out_of_stock() throws Exception {
        Member member = getMember();

        int price = 10000;
        int stockQuantity = 10;
        Book item = getItem("JPA Book", price, stockQuantity);

        int orderCount = 12;

        assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void cancel_order() throws Exception {
        Member member = getMember();

        int price = 10000;
        int stockQuantity = 10;
        Book item = getItem("JPA Book", price, stockQuantity);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity);
    }

    private Book getItem(String name, int price, int stockQuantity) {
        Book item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);

        return item;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("manta");
        member.setAddress(new Address("New York", "Wall Street", "00223"));
        em.persist(member);
        
        return member;
    }

}