package com.wuyumaomao.easygenerate.bean;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Data
public class TableInfo {
   private String tableName;
   private String beanName;
   private String beanParamName;
   private String comment;
   private List<FieldInfo> fieldList;
   private Map<String,List<FieldInfo>> keyIndexMap=new LinkedHashMap<>();
   private Boolean haveDate;
   private Boolean haveDateTime;
   private List<FieldInfo> fieldExtendList;
   private Boolean haveBigDecimal;
//   private Boolean haveAutoIncrement;
}
