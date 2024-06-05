package com.projcet.tstorycopyproject.page.blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostsDetailInfoRq {
    private Long feedId;

    private String title;

    private String content;

    private String blogNm;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> tags;
}
