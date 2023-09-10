package com.example.jpabook.jpashop.domain.item;

import com.example.jpabook.global.BaseEntity;
import com.example.jpabook.jpashop.domain.Category;
import com.example.jpabook.jpashop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // == default
@Getter @Setter
public class Item extends BaseEntity {

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();

    public void addStock(int qty) {
        this.stockQuantity += qty;
    }

    public void removeStock(int qty) {
        int currentQty = this.stockQuantity;
        this.stockQuantity -= qty;

        if (this.stockQuantity < 0) {
            this.stockQuantity = currentQty;
            throw new NotEnoughStockException("need more stock");
        }
    }

}
