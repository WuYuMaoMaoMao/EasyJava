package com.wuyumaomao.easygenerate.builder;

import com.wuyumaomao.easygenerate.bean.Contans;
import com.wuyumaomao.easygenerate.bean.FieldInfo;
import com.wuyumaomao.easygenerate.bean.TableInfo;
import com.wuyumaomao.easygenerate.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildMapperXML {
    private static  final Logger logger= LoggerFactory.getLogger(BuildMapperXML.class);
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
            bw.write("<!--实体映射-->");
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
              bw.write("<"+key+" column=\""+fieldInfo.getFieldName()+"\" property=\""+fieldInfo.getPropertyName()+"\"/>");
                bw.newLine();


            }
            bw.write("</resultMap>");
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
