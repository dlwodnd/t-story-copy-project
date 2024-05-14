package com.projcet.tstorycopyproject.domain.blog.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInsRq {
    private Long blogPk;
    private List<CategoryInfoRq> catInfoList;
}
