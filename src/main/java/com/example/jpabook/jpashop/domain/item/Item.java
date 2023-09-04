package com.example.jpabook.jpashop.domain.item;

import com.example.jpabook.global.BaseEntity;
import com.example.jpabook.jpashop.domain.Category;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // == default
public class Item extends BaseEntity {

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();
}
