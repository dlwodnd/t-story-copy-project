package com.projcet.tstorycopyproject.global.entity;

import com.projcet.tstorycopyproject.global.entity.base.CreatedAtBaseEntity;
import com.projcet.tstorycopyproject.global.entity.embeddable.BlogSubComposite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_blog_sub")
public class BlogSubEntity extends CreatedAtBaseEntity {
    @EmbeddedId
    private BlogSubComposite blogSubComposite;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userPk")
    @JoinColumn(name = "user_pk", referencedColumnName = "userPk", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("blogPk")
    @JoinColumn(name = "blog_pk", referencedColumnName = "blogPk", columnDefinition = "BIGINT UNSIGNED")
    private BlogEntity blogEntity;

}
