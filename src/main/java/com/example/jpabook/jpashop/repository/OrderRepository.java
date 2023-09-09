package com.example.jpabook.jpashop.repository;

import com.example.jpabook.jpashop.domain.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public void save(Order o) {
        em.persist(o);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

}
