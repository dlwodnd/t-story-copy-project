package com.projcet.tstorycopyproject.common.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedFavComposite implements Serializable {
    private Long userPk;
    private Long feedPk;
}
