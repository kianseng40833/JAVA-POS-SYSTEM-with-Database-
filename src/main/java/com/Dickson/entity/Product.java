package com.Dickson.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Product {

    private String product_id;

    private String product_name;

    private String product_category;

    private Double price;

    private Integer stock;

    private List<String> product_image;

    private String remarks;
}