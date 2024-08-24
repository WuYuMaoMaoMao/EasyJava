package com.wuyumaomao.easygenerate.builder;

import com.wuyumaomao.easygenerate.bean.Contans;
import com.wuyumaomao.easygenerate.bean.FieldInfo;
import com.wuyumaomao.easygenerate.bean.TableInfo;
import com.wuyumaomao.easygenerate.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildMapperXML {
    private static  final Logger logger= LoggerFactory.getLogger(BuildMapperXML.class);
    private static String base_column_list="base_column_list";
    private static String base_query_condition="base_query_condition";
    private static String query_condition="query_condition";
    private static String base_query_condition_extend="base_query_condition_extend";
    public static void execute(TableInfo tableInfo){

       File folder=new File(Contans.PATH_MAPPERS_XML);
       if(!folder.exists()){
           folder.mkdirs();
       }
        String ClassName=tableInfo.getBeanName()+Contans.Suffix_Mappers;
        File poFile=new File(folder,ClassName+".xml");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
            bw.newLine();
            bw.write("\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<mapper namespace=\""+Contans.PACKAGE_MAPPER+"."+ClassName+"\">");
            bw.newLine();
            bw.write("\t<!--实体映射-->");
            bw.newLine();

            String poClass=Contans.PACKAGE_PO+"."+tableInfo.getBeanName();
            bw.write("<resultMap id=\"base_result_map\" type=\""+poClass+"\">");
            bw.newLine();
            tableInfo.getKeyIndexMap().entrySet();
            Map<String,List<FieldInfo>> keyIndexMap=tableInfo.getKeyIndexMap();
            FieldInfo isField=null;
            for(Map.Entry<String,List<FieldInfo>> entry:keyIndexMap.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    if (fieldInfoList.size() == 1) {
                        isField = fieldInfoList.get(0);
                        break;
                    }
                }
            }
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                bw.write("\t\t<!--"+fieldInfo.getComment()+"-->");
                bw.newLine();
                String key="";
                if (isField!=null&&fieldInfo.getPropertyName().equals(isField.getPropertyName())){
                    key="id";
                }else {
//                    bw.write("<result column=\"id\" property=\"id\"/>");
                    key="result";
                }
              bw.write("\t<"+key+" column=\""+fieldInfo.getFieldName()+"\" property=\""+fieldInfo.getPropertyName()+"\"/>");
                bw.newLine();


            }
            bw.write(" </resultMap>");
            bw.newLine();
            bw.write("\t<!--通用查询列-->");
            bw.newLine();
//            String base_column_list="base_column_list";
            bw.write("\t <sql id=\""+base_column_list+"\">");
            bw.newLine();
            StringBuilder columnBuilder=new StringBuilder();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                columnBuilder.append(fieldInfo.getFieldName()).append(",");
            }
            String columnBuilderStr=columnBuilder.substring(0,columnBuilder.lastIndexOf(","));
            bw.write("\t\t  "+columnBuilderStr);
            bw.newLine();
            bw.write("\t </sql>");
            bw.newLine();
            bw.write("\t<!--基础查询条件-->");
            bw.newLine();
//            String base_column_list="base_column_list";
            bw.write("\t <sql id=\""+base_query_condition+"\">");
            bw.newLine();
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
               String stringQuery="";
                if(ArrayUtils.contains(Contans.SQL_STRING_TYPES,fieldInfo.getSqlType())){
                  stringQuery=" and query."+fieldInfo.getPropertyName()+"!=''";
               }
               bw.write("\t  <if test=\"query."+fieldInfo.getPropertyName()+"!=null\">");
               bw.newLine();
               bw.write("\t    and id=#{query."+fieldInfo.getPropertyName()+"}");
               bw.newLine();
               bw.write("\t  </if>");
               bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.write("\t<!--拓展查询条件-->");
            bw.newLine();
            bw.write("\t <sql id=\""+base_query_condition_extend+"\">");
            bw.newLine();
            for (FieldInfo fieldInfo:tableInfo.getFieldExtendList()){
                String andWhere="";
                if(ArrayUtils.contains(Contans.SQL_STRING_TYPES,fieldInfo.getSqlType())){
                    andWhere=" and "+fieldInfo.getFieldName()+" like concat('%',#(query."+fieldInfo.getPropertyName()+"),'%')";
                }else if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,fieldInfo.getSqlType())||ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                    if(fieldInfo.getPropertyName().endsWith(Contans.Suffix_bean_query_time_start)){
                        andWhere="<![CDATA[ and "+fieldInfo.getFieldName()+">= str_to_date(#("+fieldInfo.getPropertyName()+"),'%Y-%m-%d')]]>";
                    }else if(fieldInfo.getPropertyName().endsWith(Contans.Suffix_bean_query_time_end)){
                        andWhere="<![CDATA[ and "+fieldInfo.getFieldName()+" <date_sub(str_to_date(#(query."+fieldInfo.getPropertyName()+"),'%Y-%m-%d'),"+"interval -1 day) ]]>";
                    }
                }
                bw.write("\t  <if test=\"query."+fieldInfo.getPropertyName()+"!=null and query."+fieldInfo.getPropertyName()+"!=''\">");
                bw.newLine();
                bw.write("\t    "+andWhere);
                bw.newLine();
                bw.write("\t  </if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.write("\t<!--拓展查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\""+query_condition+"\">");
            bw.newLine();
            bw.write("\t <where>");
            bw.newLine();
            bw.write("\t  <include refid=\""+base_column_list+"\"/>");
            bw.newLine();
            bw.write("\t  <include refid=\""+base_query_condition_extend+"\">");
            bw.newLine();
            bw.write("\t </where>");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.write("</mapper>");
            bw.newLine();
            bw.flush();
        }catch (Exception e){
            logger.error("创建Mapper.xml失败");
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                }catch (Exception e){

                }
            }
        }

    }

}
