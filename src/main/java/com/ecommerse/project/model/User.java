package com.ecommerse.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users",uniqueConstraints = {
@UniqueConstraint(columnNames = "username"),
@UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    @Column(name = "username")
    @Size(max=28)
    private String userName;
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotBlank
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role>roles=new HashSet<>();
    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_addresses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    List<Address> addresses=new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    private Set<Product>products;


    public User(String userName,String email,String password) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

}
