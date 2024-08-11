package com.wuyumaomao.easygenerate.builder;

import com.wuyumaomao.easygenerate.bean.Contans;
import com.wuyumaomao.easygenerate.bean.FieldInfo;
import com.wuyumaomao.easygenerate.bean.TableInfo;
import com.wuyumaomao.easygenerate.utils.DateUtils;
import com.wuyumaomao.easygenerate.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class BuildPo {
    private static  final Logger logger= LoggerFactory.getLogger(BuildPo.class);
    public static void execute(TableInfo tableInfo){

        File folder=new File(Contans.PATH_PO);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File poFile=new File(folder,tableInfo.getBeanName()+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
          out=new FileOutputStream(poFile);
          outw=new OutputStreamWriter(out,"utf8");
          bw=new BufferedWriter(outw);
          bw.write("package "+Contans.PACKAGE_PO+";");
          bw.newLine();
          bw.newLine();
          bw.write("import java.io.Serializable;");
          bw.newLine();

          if(tableInfo.getHaveDate()|| tableInfo.getHaveDateTime()){
           bw.write("import java.util.Date;");
           bw.newLine();
           bw.write("import"+" "+Contans.PACKAGE_ENUM+"."+"DateTimePatternEnum;");
           bw.newLine();
           bw.write("import"+" "+Contans.package_Utils+"."+"DateUtil;");
           bw.newLine();
           bw.write(Contans.bean_date_format_class+";");
           bw.newLine();
           bw.write(Contans.bean_date_unformat_class+";");
           bw.newLine();
          }

          if(tableInfo.getHaveBigDecimal()){
              bw.write("import java.math.BigDecimal;");
              bw.newLine();
          }
          Boolean haveIgnoreBean=false;
          for(FieldInfo fieldInfo:tableInfo.getFieldList()){
              if(ArrayUtils.contains(Contans.ignore_bean_tojson_field.split(","),fieldInfo.getPropertyName())){
                 haveIgnoreBean=true;
                 break;
              }
          }
          if(haveIgnoreBean){
              bw.write(Contans.ignore_bean_tojson_class+";");
              bw.newLine();
          }
          bw.newLine();
          BuildComment.createClassComment(bw,tableInfo.getComment());
          bw.write("public class "+tableInfo.getBeanName()+" implements Serializable {");
          bw.newLine();
          for(FieldInfo fieldInfo:tableInfo.getFieldList()){
              BuildComment.createFieldComment(bw,fieldInfo.getComment());

              if(ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                  bw.write(" \t"+String.format(Contans.bean_date_format_expression, DateUtils.YYYY_MM_DD_HH_MM_SS));
                  bw.newLine();
                  bw.write(" \t"+String.format(Contans.bean_date_unformat_expression,DateUtils.YYYY_MM_DD_HH_MM_SS));
                  bw.newLine();
              }
              if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                  bw.write(" \t"+String.format(Contans.bean_date_format_expression,DateUtils.YYYY_MM_DD));
                  bw.newLine();
                  bw.write(" \t"+String.format(Contans.bean_date_unformat_expression,DateUtils.YYYY_MM_DD));
                  bw.newLine();
              }

              if(ArrayUtils.contains(Contans.ignore_bean_tojson_field.split(","),fieldInfo.getPropertyName())){
                  bw.write(" \t"+String.format(Contans.ignore_bean_tojson_expression,DateUtils.YYYY_MM_DD));
                  bw.newLine();
              }
              bw.write("    "+"private "+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName()+";");
              bw.newLine() ;
              bw.newLine();
          }
          for(FieldInfo fieldInfo:tableInfo.getFieldList()){
              String tempField= StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName());
              bw.write("\t public void set"+tempField+"("+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName()+"){");
              bw.newLine();
              bw.write("\t   this."+fieldInfo.getPropertyName()+"="+fieldInfo.getPropertyName()+";");
              bw.newLine();
              bw.write("\t }");
              bw.newLine();

              bw.write("\t public"+" "+fieldInfo.getJavaType()+" "+"get"+tempField+"("+"){");
              bw.newLine();
              bw.write("\t  return this."+fieldInfo.getPropertyName()+";");
              bw.newLine();
              bw.write("\t }");
              bw.newLine();
          }
          StringBuffer toString=new StringBuffer();
          Integer index=0;
          for(FieldInfo fieldInfo:tableInfo.getFieldList()){
               index++;
               String propertyName=fieldInfo.getPropertyName();
               if(ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                       propertyName=" DateUtil.format("+propertyName+", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
               }else if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                       propertyName=" DateUtil.format("+propertyName+", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
               }
               toString.append(fieldInfo.getComment()+":\" + ("+fieldInfo.getPropertyName()+" == null ? \"空\" :"+propertyName+")");
               if(index<tableInfo.getFieldList().size()){
                   toString.append("+").append("\",");
               }

          }
          String toStringResult = toString.toString();
          String toStringStr = "\"" + toStringResult;
          toStringStr.substring(0,toString.lastIndexOf(","));
          bw.write("\t @Override");
          bw.newLine();
          bw.write("\t public String toString () {");
          bw.newLine();
          bw.write("\t return "+toStringStr+";");
          bw.newLine();
          bw.write("\t }");
          bw.newLine();
          bw.write("}");
          bw.flush();
        }catch (Exception e){
            logger.error("创建po失败");
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
