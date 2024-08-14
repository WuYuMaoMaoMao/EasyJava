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
import java.util.List;
import java.util.Map;

public class BuildMapper {
    private static  final Logger logger= LoggerFactory.getLogger(BuildPo.class);
    public static void execute(TableInfo tableInfo){

        File folder=new File(Contans.PATH_MAPPER);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String ClassName=tableInfo.getBeanName()+Contans.Suffix_Mappers;
        File poFile=new File(folder,ClassName+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("package "+Contans.PACKAGE_MAPPER+";");
//            bw.write("import java.io.Serializable;");
            bw.newLine();

            bw.write("import org.apache.ibatis.annotations.Param;");

            bw.newLine();
            BuildComment.createClassComment(bw,tableInfo.getComment()+"Mapper");
            bw.write("public interface "+ClassName+"<T,P> extends BaseMapper{");
            bw.newLine();
            Map<String, List<FieldInfo>> keyIndexMap=tableInfo.getKeyIndexMap();

            for(Map.Entry<String,List<FieldInfo>> entry:keyIndexMap.entrySet()){
              List<FieldInfo> keyFieldIndexList=entry.getValue();
              Integer index=0;
              StringBuilder methodName=new StringBuilder();
              StringBuilder methodParams=new StringBuilder();

              for (FieldInfo fieldInfo:keyFieldIndexList){
                  methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
                  index++;
                  if(index<keyFieldIndexList.size()){
                      methodName.append("And");
                  }
                  methodParams.append("@Param(\""+fieldInfo.getPropertyName()+"\") "+ fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                  if(index<keyFieldIndexList.size()){
                      methodParams.append(",");
                  }
              }
              bw.newLine();
              BuildComment.createFieldComment(bw,"根据"+methodName+"查询");

             bw.write("\t T selectBy"+methodName+"("+methodParams+");");
             bw.newLine();
             bw.newLine();
             BuildComment.createFieldComment(bw,"根据"+methodName+"更新");
             bw.write("\t Integer updateBy"+methodName+"(@Param(\"bean\") T t,"+methodParams+");");
             bw.newLine();
             bw.newLine();

             BuildComment.createFieldComment(bw,"根据"+methodName+"删除");
             bw.write("\t Integer deleteBy"+methodName+"("+methodParams+");");
             bw.newLine();
             bw.newLine();


            }

            bw.write(" }");
            bw.newLine();
            bw.flush();
        }catch (Exception e){
            logger.error("创建Mapper失败");
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
