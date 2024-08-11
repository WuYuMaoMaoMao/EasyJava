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

public class BuildQuery {
    private static  final Logger logger= LoggerFactory.getLogger(BuildQuery.class);
    public static void execute(TableInfo tableInfo){

        File folder=new File(Contans.PATH_QUERY);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String className=tableInfo.getBeanName()+Contans.suffix_bean_query;
        File poFile=new File(folder,className+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("package "+Contans.PACKAGE_QUERY+";");
            bw.newLine();

            if(tableInfo.getHaveDate()|| tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;");
                bw.newLine();
            }

            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            bw.newLine();
            BuildComment.createClassComment(bw,tableInfo.getComment());
            bw.write("public class "+className+"{");
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
