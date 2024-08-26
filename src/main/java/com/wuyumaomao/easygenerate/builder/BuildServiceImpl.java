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

public class BuildServiceImpl {
    private static  final Logger logger= LoggerFactory.getLogger(BuildService.class);
    public static void execute(TableInfo tableInfo){

        File folder=new File(Contans.PATH_SERVICE);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String className=tableInfo.getBeanName()+"Service";
        File poFile=new File(folder,className+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("package "+Contans.PACKAGE_SERVICE+";");
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

            bw.write("import "+Contans.PACKAGE_VO+".PaginationResultVO;");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_PO+"."+tableInfo.getBeanName()+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_QUERY+"."+tableInfo.getBeanParamName()+";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment()+"Service");
            bw.write("public interface "+className+" {");
            bw.newLine();
            bw.newLine();
            BuildComment.createClassComment(bw, "根据条件查询列表");
            bw.write("\tList<"+tableInfo.getBeanName()+"> findByParam("+tableInfo.getBeanParamName()+" param);");
            bw.newLine();
            bw.newLine();
            BuildComment.createClassComment(bw, "根据条件查询数量");
            bw.write("\tLong findCountByParam( "+tableInfo.getBeanParamName()+" query);");
            bw.newLine();
            bw.newLine();
            BuildComment.createFieldComment(bw,"分页查询");
            bw.write("\tPaginationResultVO<"+tableInfo.getBeanName()+"> findListByPage("+tableInfo.getBeanParamName()+" param);");
            bw.newLine();
            bw.newLine();
            BuildComment.createFieldComment(bw,"新增");
            bw.write("\tLong add("+tableInfo.getBeanName()+" bean);");
            bw.newLine();
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增");
            bw.write("\tLong addBatch(List<"+tableInfo.getBeanName()+"> listBean);");
            bw.newLine();
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增或修改");
            bw.write("\tLong addOrUpdateBatch(List<"+tableInfo.getBeanName()+"> listBean);");
            bw.newLine();
            bw.newLine();
            Map<String, List<FieldInfo>> keyIndexMap=tableInfo.getKeyIndexMap();

            for(Map.Entry<String, List<FieldInfo>> entry:keyIndexMap.entrySet()){
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
                    methodParams.append( fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                    if(index<keyFieldIndexList.size()){
                        methodParams.append(",");
                    }
                }
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"查询");

                bw.write("\t"+tableInfo.getBeanName()+" getBy"+methodName+"("+methodParams+");");
                bw.newLine();
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"更新");
                bw.write("\t Long updateBy"+methodName+"("+tableInfo.getBeanName()+" bean,"+methodParams+");");
                bw.newLine();
                bw.newLine();

                BuildComment.createFieldComment(bw,"根据"+methodName+"删除");
                bw.write("\t Long deleteBy"+methodName+"("+methodParams+");");
                bw.newLine();
                bw.newLine();


            }
            bw.write("}");

            bw.flush();
        }catch (Exception e){
            logger.error("创建impl失败");
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
