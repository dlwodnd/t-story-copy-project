package com.projcet.tstorycopyproject.common.entity;

import com.projcet.tstorycopyproject.common.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_pk", referencedColumnName = "blogPk", columnDefinition = "BIGINT UNSIGNED")
    private BlogEntity blogEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_pk", referencedColumnName = "catPk", columnDefinition = "BIGINT UNSIGNED")
    private CategoryEntity categoryEntity;

    private String title;

    private String contents;

    @ColumnDefault("'0'")
    private Long viewCount;

    // 비공개 설정
    @ColumnDefault("'0'")
    private Long feedPrivate;

    // 글 작성 완료 설정
    @ColumnDefault("'0'")
    private Long complete;

    @OneToMany(mappedBy = "feedEntity", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FeedPicsEntity> feedPicsEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "feedEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<FeedCommentEntity> feedCommentEntityList = new HashSet<>();

    @OneToMany(mappedBy = "feedEntity", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<HashTagEntity> hashTagEntitySet = new HashSet<>();

    /*public void editFeedEntity(FeedInsDto dto, CatEntity catEntity) {
        this.title = dto.getTitle();
        this.contents = dto.getContent();
        this.catEntity = catEntity;
        this.feedPrivate = dto.getFeedPrivate();
        this.complete = 1L;
    }
    public void modifyHashTagEntityList(List<HashTagEntity> hashTagEntityList){
        this.hashTagEntitySet.clear();
        this.hashTagEntitySet.addAll(hashTagEntityList);
    }*/
}
