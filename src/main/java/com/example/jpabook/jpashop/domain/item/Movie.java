package com.example.jpabook.jpashop.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("M")
@Entity
public class Movie extends Item {

    private String director;
    private String actor;

}
