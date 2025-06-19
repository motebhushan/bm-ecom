package com.ecommerse.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    private String streetName;
    @NotBlank
    private String buildingName;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User>users=new ArrayList<>();

    public Address(String streetName, String buildingName, String state, String city, String country, String pincode, Long addressId) {
        this.streetName = streetName;
        this.buildingName = buildingName;
        this.state = state;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.addressId = addressId;
    }
}
