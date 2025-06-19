package com.ecommerse.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    private AppRole roleName;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
