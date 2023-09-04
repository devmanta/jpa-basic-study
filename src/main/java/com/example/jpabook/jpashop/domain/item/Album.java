package com.example.jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;

}
