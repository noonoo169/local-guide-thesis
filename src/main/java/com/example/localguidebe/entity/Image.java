package com.example.localguidebe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @URL
    @Column(name = "image_link",nullable = false)
    private String imageLink;
    @NotNull
    @Column(name="associate_name")
    private String associateName;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)
    private User user;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)
    private Tour tour;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id",insertable=false, updatable=false)

    private Location location;
}
