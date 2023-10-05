package com.justshop.delivery.domain.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address extends Delivery {

    private String street;
    private String city;
    private String zipcode;

}
