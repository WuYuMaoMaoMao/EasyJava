package com.wuyumaomao.easygenerate.bean;

import com.wuyumaomao.easygenerate.utils.PropertiesUtils;
import lombok.Data;

@Data
public class Contans {
    public static Boolean ignore_table_prefix;
    public static  String  suffix_bean_query;
    public static String PATH_BASE;
    public static  String PACKAGE_BASE;
   public static  String PATH_PO;
   private static String PATH_JAVA="java";
   private static String PATH_resource="resources";
   public static  String PACKAGE_PO;
   public static String AUTHOR_COMMENT;
   public static  String ignore_bean_tojson_field;
   public static String ignore_bean_tojson_expression;
   public static String ignore_bean_tojson_class;
   public static String bean_date_format_expression;
   public static String  bean_date_format_class;
   public static String bean_date_unformat_expression;
   public static String bean_date_unformat_class;
   public static  String package_Utils;
   public static String PATH_UTILS;
   public static String PACKAGE_ENUM;
   public static String PATH_ENUM;
   public static String PACKAGE_QUERY;
   public static String PATH_QUERY;
   public static String Suffix_bean_query_fuzzy;
   public static String Suffix_bean_query_time_start;
   public static String Suffix_bean_query_time_end;
    public static String Suffix_Mappers;

   public static final String PACKAGE_MAPPER;
   public static final String PATH_MAPPER;
   public static final String PATH_MAPPERS_XML;
    static {
        ignore_table_prefix= Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        suffix_bean_query=PropertiesUtils.getString("suffix.bean.query");
        PATH_BASE=PropertiesUtils.getString("path.base");
        PATH_BASE=PATH_BASE+PATH_JAVA+"/"+PropertiesUtils.getString("package.base");
        PATH_BASE=PATH_BASE.replace(".","/");
        PATH_PO=PATH_BASE+"/"+PropertiesUtils.getString("package.po").replace(".","/");
        PACKAGE_BASE=PropertiesUtils.getString("package.base");
        PACKAGE_PO=PACKAGE_BASE+"."+PropertiesUtils.getString("package.po");
        AUTHOR_COMMENT=PropertiesUtils.getString("author.comment");
         ignore_bean_tojson_field=PropertiesUtils.getString("ignore.bean.tojson.field");
         ignore_bean_tojson_expression=PropertiesUtils.getString("ignore.bean.tojson.expression");
         ignore_bean_tojson_class=PropertiesUtils.getString("ignore.bean.tojson.class");
         bean_date_format_expression=PropertiesUtils.getString("bean.date.format.expression");
         bean_date_format_class=PropertiesUtils.getString("bean.date.format.class");
        bean_date_unformat_expression=PropertiesUtils.getString("bean.date.unformat.expression");
        bean_date_unformat_class=PropertiesUtils.getString("bean.date.unformat.class");
        package_Utils=PACKAGE_BASE+"."+PropertiesUtils.getString("package.utils");
       PATH_UTILS=PATH_BASE+"/"+"utils";
       PACKAGE_ENUM=PACKAGE_BASE+"."+PropertiesUtils.getString("package.enums");
       PATH_ENUM=PATH_BASE+"/"+PropertiesUtils.getString("package.enums");

       PACKAGE_QUERY=PACKAGE_BASE+"."+PropertiesUtils.getString("package.query"); ;
       PATH_QUERY=PATH_BASE+"/"+PropertiesUtils.getString("package.query").replace(".","/");

       Suffix_bean_query_fuzzy=PropertiesUtils.getString("suffix.bean.query.fuzzy");
       Suffix_bean_query_time_start=PropertiesUtils.getString("suffix.bean.query.time.start");
       Suffix_bean_query_time_end=PropertiesUtils.getString("suffix.bean.query.time.end");
       Suffix_Mappers=PropertiesUtils.getString("suffix.mappers");
       PACKAGE_MAPPER=PACKAGE_BASE+"."+PropertiesUtils.getString("package.mapper");
       PATH_MAPPER=PATH_BASE+"/"+PropertiesUtils.getString("package.mapper").replace(".","/");
       PATH_MAPPERS_XML=PropertiesUtils.getString("path.base")+PATH_resource+"/"+PACKAGE_MAPPER.replace(".","/");
    }
    public  final static String[] SQL_DATE_TIME_TYPES=new String[]{"datetime","timestamp"};
    public  final static String[] SQL_DATE_TYPES=new String[]{"date"};
    public final static String[] SQL_DECIMAL_TYPES=new String[]{"decimal","double","float"};
    public final static String[] SQL_STRING_TYPES=new String[]{"char","varchar","text","mediumtext","longtext"};
    public final static  String[] SQL_INTEGER_TYPES=new String[]{"int","tinyint"};
    public final static String[] SQL_LONG_TYPES=new String[]{"bigint"};
    public static void main(String[] args){
        System.out.println( PATH_MAPPERS_XML);
    }
}
