package com.example.jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("B")
@Entity
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;

}
