package com.example.localguidebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name="associate_name")
    private String associateName;
    @ManyToOne
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)

    private Location location;
}
