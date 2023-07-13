package com.vongthaya.backenddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "email", nullable = false, unique = true, length = 60)
    private String email;

//    @JsonIgnore // not return this field to client
    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    // orphanRemoval = true means when delete user also delete social.
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Social social;

    // use FetchType.EAGER to fix problem org.hibernate.LazyInitializationException: failed to lazily initialize a collection
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Address> addresses;

}
