package com.example.jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("B")
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

}
