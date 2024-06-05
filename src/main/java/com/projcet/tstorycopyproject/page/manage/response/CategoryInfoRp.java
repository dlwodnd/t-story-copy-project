package com.projcet.tstorycopyproject.page.manage.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryInfoRp {
    private Long catPk;
    private Long catSeq;
    private String catName;
    private Long catOrder;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategorySubInfoRp> categorySubInfoRpList;
}
