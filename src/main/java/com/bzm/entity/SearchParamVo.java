package com.bzm.entity;

import lombok.Data;

@Data
public class SearchParamVo {
    private Long pageSize;
    private Long pageNum;
    private String content;

    public Long getPageSize() {
        if(pageSize==null){
            return 10L;
        }
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNum() {
        if(pageNum==null){
            return 1L;
        }
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }
}
