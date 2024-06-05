package com.projcet.tstorycopyproject.page.posts.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryFeed {
    private Long feedPk;
    private String title;
    private LocalDateTime createdAt;
}
