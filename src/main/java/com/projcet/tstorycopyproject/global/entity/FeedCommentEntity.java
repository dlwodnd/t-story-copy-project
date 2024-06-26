package com.projcet.tstorycopyproject.global.entity;

import com.projcet.tstorycopyproject.global.entity.base.BaseEntity;
import com.projcet.tstorycopyproject.page.blog.request.FeedCmtPutRq;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "t_feed_cmt")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedCommentEntity extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmtPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_pk", referencedColumnName = "feedPk", columnDefinition = "BIGINT UNSIGNED")
    private FeedEntity feedEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk", referencedColumnName = "userPk", columnDefinition = "BIGINT UNSIGNED", nullable = true)
    private UserEntity userEntity;

    @Column(nullable = false)
    private String cmt;

    // 댓글 비공개 설정
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long cmtPrivate;

    public void modifyFeedComment(FeedCmtPutRq rq){
        this.cmt = rq.getCmt();
        this.cmtPrivate = rq.getCmtPrivate();
    }

}
