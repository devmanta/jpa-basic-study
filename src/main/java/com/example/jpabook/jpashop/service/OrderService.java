package com.example.jpabook.jpashop.service;

import com.example.jpabook.jpashop.domain.Delivery;
import com.example.jpabook.jpashop.domain.Member;
import com.example.jpabook.jpashop.domain.Order;
import com.example.jpabook.jpashop.domain.OrderItem;
import com.example.jpabook.jpashop.domain.item.Item;
import com.example.jpabook.jpashop.repository.ItemRepository;
import com.example.jpabook.jpashop.repository.MemberRepository;
import com.example.jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order); // orderItem, devliery is saved due to cascade = ALL option

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel(); // dirty checking, update automatically by JPA
    }

}
