package com.example.localguidebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "level_detail")
public class LevelDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String level;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "levelDetail")
    List<LanguageSkill> languageSkills = new ArrayList<>();
}
