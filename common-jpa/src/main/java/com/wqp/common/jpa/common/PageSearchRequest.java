package com.wqp.common.jpa.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "分页查询请求")
public class PageSearchRequest<T> {
    @ApiModelProperty(name = "请求页码", example = "0")
    private Integer page;
    @ApiModelProperty(name = "每页数量", example = "10")
    private Integer pageSize;
    @ApiModelProperty(name = "查询条件", example = "{}")
    private T searchCondition;
    @ApiModelProperty(name = "排序条件", example = "[{\"property\":\"id\", \"direction\":\"DESC\"}]")
    private List<SortCondition> sortCondition;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(T searchCondition) {
        this.searchCondition = searchCondition;
    }

    public List<SortCondition> getSortCondition() {
        return sortCondition;
    }

    public void setSortCondition(List<SortCondition> sortCondition) {
        this.sortCondition = sortCondition;
    }
}
