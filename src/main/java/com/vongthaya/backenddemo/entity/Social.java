package com.vongthaya.backenddemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "social")
@Data
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "line")
    private String line;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "tiktok")
    private String tiktok;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
