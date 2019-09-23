package com.wqp.common.jpa.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "排序条件")
public class SortCondition {
    @ApiModelProperty(name = "属性名称", example = "id")
    private String property;
    @ApiModelProperty(name = "排序方向", example = "DESC")
    private Direction direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Direction getDirection() {
        return this.direction == null && StringUtils.isNotEmpty(this.property) ? Direction.ASC : this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction{
        ASC("ASC"), DESC("DESC");

        private String direction;

        Direction(String direction){
            this.direction = direction;
        }
    }
}
