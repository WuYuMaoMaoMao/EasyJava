package com.wuyumaomao.easygenerate.bean;

import lombok.Data;

@Data
public class FieldInfo {
    private String FieldName;
    private String propertyName;
    private String sqlType;
    private String javaType;
    private String comment;
    private Boolean isAutoIncrement;

}
