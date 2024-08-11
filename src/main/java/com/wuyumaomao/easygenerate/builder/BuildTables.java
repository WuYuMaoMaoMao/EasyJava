package com.wuyumaomao.easygenerate.builder;

import com.wuyumaomao.easygenerate.bean.Contans;
import com.wuyumaomao.easygenerate.bean.FieldInfo;
import com.wuyumaomao.easygenerate.bean.TableInfo;
import com.wuyumaomao.easygenerate.utils.JsonUtils;
import com.wuyumaomao.easygenerate.utils.PropertiesUtils;
import com.wuyumaomao.easygenerate.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ListFactoryBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BuildTables {
    public static Connection conn=null;
    private static final Logger logger= LoggerFactory.getLogger(BuildTables.class);
    private static String SQL_SHOW_TABLE_STATUS="show table status";
    private static String SQL_SHOW_TABLE_FIELD="show full fields FROM %s";
    private static String SQL_SHOW_TABLE_INDEX="show index from %s";
    static {
        String driverName= PropertiesUtils.getString("db.driver.name");
        String url= PropertiesUtils.getString("db.url");
        String username= PropertiesUtils.getString("db.username");
        String password= PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn= DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            logger.error("数据库连接失败",e);
        }
    }
    public static List<TableInfo> getTables() {
        PreparedStatement ps=null;
        ResultSet tableResult=null;
        List<TableInfo> tableInfoList=new ArrayList<>();

        try {
           ps=conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
           tableResult=ps.executeQuery();
           while(tableResult.next()){
               String tableName=tableResult.getString("Name");
               String comment=tableResult.getString("comment");

//               logger.info("tableName:{},comment:{}",tableName,comment);
              TableInfo tableInfo=new TableInfo();
              tableInfo.setTableName(tableName);
              String beanName=tableName;
              if(Contans.ignore_table_prefix){
                  beanName=tableName.substring(beanName.indexOf("_")+1);
              }
              beanName=processField(beanName,true);

             tableInfo.setBeanName(beanName);
             tableInfo.setComment(comment);
             tableInfo.setBeanParamName(beanName+Contans.suffix_bean_query);
//             logger.info("BeanParamName:{},comment:{},beanName:{},tableName:{}",beanName+Contans.suffix_bean_param,comment,beanName,tableName);
              readFieldInfo(tableInfo);
               getKeyIndexInfo(tableInfo);
               tableInfoList.add(tableInfo);
               logger.info("tableInfo:{}",JsonUtils.convertObj2Json(tableInfo));
//             logger.info("表:{}", JsonUtils.convertObj2Json(tableInfo));
//             logger.info("字段:{}",JsonUtils.convertObj2Json(fieldInfoList));
           }
        }catch (Exception e){
            logger.error("读取表失败",e);
        }finally {
              try {
                  if (tableResult != null) {
                      tableResult.close();
                  }
              }catch (SQLException e){

              }
            try {
                if (ps!=null){
                    ps.close();
                }
            }catch (SQLException e){

            }
            try {
                if(conn!=null){
                    conn.close();
                }
            }catch (SQLException e){

            }

        }
        return tableInfoList;
    }
    private static String processField(String field,Boolean upperCaseFirstLetter){
     StringBuffer sb=new StringBuffer();
     String[] fields=field.split("_");
     sb.append(upperCaseFirstLetter? StringUtils.upperCaseFirstLetter(fields[0]):fields[0]);
     for (int i=1,len=fields.length;i<len;i++){
       sb.append(StringUtils.upperCaseFirstLetter(fields[i]));
     }
     return sb.toString();
    }

    private static  void readFieldInfo(TableInfo tableInfo){
        PreparedStatement ps=null;
        ResultSet fieldResult=null;
        List<FieldInfo> fieldInfoList=new ArrayList<>();

        try {
            ps=conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELD,tableInfo.getTableName()));
            fieldResult=ps.executeQuery();
            while(fieldResult.next()){
                String field=fieldResult.getString("Field");
                String type=fieldResult.getString("Type");
                String extra=fieldResult.getString("Extra");
                String comment=fieldResult.getString("Comment");
//                logger.info("field:{},type:{},extra:{},cpmment:{}",field,type,extra,comment);
                if(type.indexOf("(")>0){
                    type=type.substring(0,type.indexOf("("));
                }
                String propertyName=processField(field,false);
                FieldInfo fieldInfo=new FieldInfo();
                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setSqlType(type);
                fieldInfo.setIsAutoIncrement("auto_increment".equalsIgnoreCase(extra)?true:false);
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(parseJavaType(type));
                fieldInfoList.add(fieldInfo);
//                tableInfo.setFieldList(fieldInfoList);
//                logger.info("field:{},type:{},extra:{},cpmment:{},propertyName:{},JavaType:{}",field,type,extra,comment,propertyName,parseJavaType(type));
                if(ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,type)){
                    tableInfo.setHaveDateTime(true);
                }else {
                    tableInfo.setHaveDateTime(false);
                }
                if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,type)){
                    tableInfo.setHaveDate(true);
                }else {
                    tableInfo.setHaveDate(false);
                }
                if(ArrayUtils.contains(Contans.SQL_DECIMAL_TYPES,type)){
                    tableInfo.setHaveBigDecimal(true);
                }else {
                    tableInfo.setHaveBigDecimal(false);
                }

            }
           tableInfo.setFieldList(fieldInfoList);
        }catch (Exception e){
            logger.error("读取表失败",e);
        }finally {


        }
    }
    private static  String parseJavaType(String type){
        if(ArrayUtils.contains(Contans.SQL_INTEGER_TYPES,type)){
            return "Integer";
        }else if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,type)||ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,type)){
             return "Date";
        }else if(ArrayUtils.contains(Contans.SQL_LONG_TYPES,type)){
            return "Long";
        }else if(ArrayUtils.contains(Contans.SQL_DECIMAL_TYPES,type)){
            return "BigDecimal";
        }else if(ArrayUtils.contains(Contans.SQL_STRING_TYPES,type)){
            return "String";
        }else {
            throw new RuntimeException("无法识别类型"+type);
        }
    }



    private static  List<FieldInfo> getKeyIndexInfo(TableInfo tableInfo){
        PreparedStatement ps=null;
        ResultSet fieldResult=null;
        List<FieldInfo> fieldInfoList=new ArrayList<>();

        try {
            Map<String,FieldInfo> tempMap=new HashMap<>();
            for(FieldInfo fieldInfo:tableInfo.getFieldList()){
                tempMap.put(fieldInfo.getFieldName(),fieldInfo);
            }
            ps=conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEX,tableInfo.getTableName()));
            fieldResult=ps.executeQuery();
            while(fieldResult.next()){
                String keyName=fieldResult.getString("key_name");
                int nonUnique=fieldResult.getInt("non_unique");
                String columnName=fieldResult.getString("Column_name");
               if(nonUnique==1){
                   continue;
               }
              List<FieldInfo> keyFieldList= tableInfo.getKeyIndexMap().get(keyName);
              if(null==keyFieldList){
                  keyFieldList=new ArrayList();
                  tableInfo.getKeyIndexMap().put(keyName,keyFieldList);
              }
//              for(FieldInfo fieldInfo:tableInfo.getFieldList()) {
//                  if(fieldInfo.getFieldName().equals(columnName)){
//                      keyFieldList.add(fieldInfo);
//                  }
//              }
                keyFieldList.add(tempMap.get(columnName));
            }
        }catch (Exception e){
            logger.error("读取索引失败",e);
        }finally {
        }
        return fieldInfoList;
    }
    public static void main(String[] args){
        getTables();
 }
}
