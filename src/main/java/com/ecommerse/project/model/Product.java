package com.ecommerse.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String image;
    private  String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private  Double specialPrice;

    @ManyToOne
    @JoinColumn(name="CategoryId")
    private  Category category;
    @ManyToOne
    @JoinColumn(name = "Seller_id")
    private User user;
}
