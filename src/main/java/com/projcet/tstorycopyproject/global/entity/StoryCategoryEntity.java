package com.projcet.tstorycopyproject.global.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "t_storty_category")
@Builder
public class StoryCategoryEntity {

    @Id
    @Column(name = "story_cat_pk",columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storyCatNm;

    @OneToMany(mappedBy = "storyCategoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();
}
