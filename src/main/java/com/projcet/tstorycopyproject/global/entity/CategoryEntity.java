package com.projcet.tstorycopyproject.global.entity;

import com.projcet.tstorycopyproject.domain.blog.request.CategoryInfoRq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_cat", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_order_cat",
                columnNames = {"top_cat_seq", "cat_order"}
        ),
        @UniqueConstraint(
                name = "uk_seq_blog_pk",
                columnNames = {"seq", "blog_pk"}
        )
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catPk;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_pk", referencedColumnName = "blogPk", columnDefinition = "BIGINT UNSIGNED")
    private BlogEntity blogEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_cat_seq", referencedColumnName = "seq", columnDefinition = "BIGINT UNSIGNED")
    private CategoryEntity categoryEntity;

    @Column(nullable = false)
    private String catNm;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long catOrder;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CategoryEntity> categoryEntityList = new ArrayList<>();

    public void editCatEntity(CategoryInfoRq dto, CategoryEntity catEntity) {
        this.seq = dto.getCatSeq();
        this.catNm = dto.getCatName();
        this.catOrder = dto.getCatOrder();
        this.categoryEntity = catEntity;
    }
}
