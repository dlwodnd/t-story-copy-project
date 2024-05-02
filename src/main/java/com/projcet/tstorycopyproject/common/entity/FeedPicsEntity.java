package com.projcet.tstorycopyproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_feed_pics")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeedPicsEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPicsPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_pk", referencedColumnName = "feedPk", columnDefinition = "BIGINT UNSIGNED")
    private FeedEntity feedEntity;

    @Column(nullable = false)
    private String feedPic;
}
