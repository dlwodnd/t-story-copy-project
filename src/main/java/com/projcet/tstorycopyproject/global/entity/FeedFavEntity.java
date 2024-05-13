package com.projcet.tstorycopyproject.global.entity;

import com.projcet.tstorycopyproject.global.entity.base.CreatedAtBaseEntity;
import com.projcet.tstorycopyproject.global.entity.embeddable.FeedFavComposite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_feed_fav")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FeedFavEntity extends CreatedAtBaseEntity {
    @EmbeddedId
    private FeedFavComposite feedFavComposite;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userPk")
    @JoinColumn(name = "user_pk", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("feedPk")
    @JoinColumn(name = "feed_pk", columnDefinition = "BIGINT UNSIGNED")
    private FeedEntity feedEntity;
}
