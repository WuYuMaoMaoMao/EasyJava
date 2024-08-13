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
import java.util.ArrayList;
import java.util.List;

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
            List<FieldInfo> extendList=new ArrayList<>();
            for(FieldInfo fieldInfo:tableInfo.getFieldList()){
                BuildComment.createFieldComment(bw,fieldInfo.getComment()+"查询对象");
                bw.write("    "+"private "+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName()+";");
                bw.newLine();
                bw.newLine();
                if(ArrayUtils.contains(Contans.SQL_STRING_TYPES,fieldInfo.getSqlType())){
                    String propertyName=fieldInfo.getPropertyName()+Contans.Suffix_bean_query_fuzzy;
                    bw.write("\tprivate "+fieldInfo.getJavaType()+" "+propertyName+";");
                    bw.newLine();
                    bw.newLine();
                    FieldInfo fuzzyField=new FieldInfo();
                    fuzzyField.setJavaType(fieldInfo.getJavaType());
                    fuzzyField.setPropertyName(propertyName);
                    extendList.add(fuzzyField);
                }
                if(ArrayUtils.contains(Contans.SQL_DATE_TYPES,fieldInfo.getSqlType())||ArrayUtils.contains(Contans.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                    bw.write("\tprivate String"+" "+fieldInfo.getPropertyName()+Contans.Suffix_bean_query_time_start+";");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tprivate String"+" "+fieldInfo.getPropertyName()+Contans.Suffix_bean_query_time_end+";");
                    bw.newLine();
                    bw.newLine();
                    FieldInfo timeStartField=new FieldInfo();
                    timeStartField.setJavaType("String");
                    timeStartField.setPropertyName(fieldInfo.getPropertyName()+Contans.Suffix_bean_query_time_start);
                    extendList.add(timeStartField);

                    FieldInfo timeEndField=new FieldInfo();
                    timeEndField.setJavaType("String");
                    timeEndField.setPropertyName(fieldInfo.getPropertyName()+Contans.Suffix_bean_query_time_end);
                    extendList.add(timeEndField);
                }
            }
            List<FieldInfo> fieldInfoList=tableInfo.getFieldList();
            fieldInfoList.addAll(extendList);
            for(FieldInfo fieldInfo:fieldInfoList){
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
