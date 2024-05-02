package com.projcet.tstorycopyproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_hash_tag")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashTagEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long hashTagPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_pk", referencedColumnName = "feedPk", columnDefinition = "BIGINT UNSIGNED")
    private FeedEntity feedEntity;

    @Column(nullable = false)
    private String hashTagNm;
}
