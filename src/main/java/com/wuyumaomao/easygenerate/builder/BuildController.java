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

public class BuildController {
    private static  final Logger logger= LoggerFactory.getLogger(BuildController.class);
    public static void execute(TableInfo tableInfo){

        File folder=new File(Contans.PATH_CONTROLLER);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String className=tableInfo.getBeanName()+"Controller";
        File poFile=new File(folder,className+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("package "+Contans.PACKAGE_CONTROLLER+";");
            bw.newLine();
            bw.newLine();
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
            String serviceName=tableInfo.getBeanName()+"Service";
            String serviceBeanName= StringUtils.lowerCaseFirstLetter(serviceName);
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_PO+"."+tableInfo.getBeanName()+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_QUERY+"."+tableInfo.getBeanParamName()+";");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RestController;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestBody;");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_SERVICE+"."+serviceName+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_VO+".ResponseVO;");
            bw.newLine();
            bw.newLine();
            bw.write("import jakarta.annotation.Resource;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment()+"Controller");
            bw.write("@RestController");
            bw.newLine();
            bw.write("@RequestMapping(\"/"+StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName())+"\")");
            bw.newLine();
            bw.write("public class "+className+" extends ABaseController{");
            bw.newLine();
            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate "+serviceName+" "+serviceBeanName+";" );
            bw.newLine();
            bw.newLine();
            bw.newLine();
            bw.write("\t@RequestMapping(\"loadDataList\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO loadDataList("+tableInfo.getBeanParamName()+" query) {");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO("+serviceBeanName+".findListByPage(query));");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"新增");
            bw.newLine();
            bw.write("\t@RequestMapping(\"add\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO add("+tableInfo.getBeanName()+" bean){");
            bw.newLine();
            bw.write("\t\tthis."+serviceBeanName+".add(bean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();

            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增");
            bw.newLine();
            bw.write("\t@RequestMapping(\"addBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addBatch(@RequestBody List<"+tableInfo.getBeanName()+"> listBean){");
            bw.newLine();
            bw.write("\t\tthis."+serviceBeanName+".addBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();

            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增或修改");
            bw.newLine();
            bw.write("\t@RequestMapping(\"addOrUpdateBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<"+tableInfo.getBeanName()+"> listBean){");
            bw.newLine();
            bw.write("\t\tthis."+serviceBeanName+".addOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            Map<String, List<FieldInfo>> keyIndexMap=tableInfo.getKeyIndexMap();

            for(Map.Entry<String, List<FieldInfo>> entry:keyIndexMap.entrySet()){
                List<FieldInfo> keyFieldIndexList=entry.getValue();
                Integer index=0;
                StringBuilder methodName=new StringBuilder();
                StringBuilder methodParams=new StringBuilder();
                StringBuilder paramBuilder=new StringBuilder();
                for (FieldInfo fieldInfo:keyFieldIndexList){
                    methodName.append(StringUtils.upperCaseFirstLetter(fieldInfo.getPropertyName()));
                    index++;
                    if(index<keyFieldIndexList.size()){
                        methodName.append("And");
                    }
                    methodParams.append( fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                    paramBuilder.append(fieldInfo.getPropertyName());
                    if(index<keyFieldIndexList.size()){
                        methodParams.append(",");
                        paramBuilder.append(",");
                    }
                }
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"查询");
                bw.newLine();
                String methodNameResult="get"+tableInfo.getBeanName()+"By"+methodName;
                bw.write("\t@RequestMapping(\""+methodNameResult+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO " +methodNameResult+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\t return getSuccessResponseVO(this."+serviceBeanName+".get"+tableInfo.getBeanName()+"By"+methodName+"("+paramBuilder+"));");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                methodNameResult="update"+tableInfo.getBeanName()+"By"+methodName;
                BuildComment.createFieldComment(bw,"根据"+methodName+"更新");
                bw.newLine();
                bw.write("\t@RequestMapping(\""+methodNameResult+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO "+methodNameResult+"("+tableInfo.getBeanName()+" bean,"+methodParams+"){");
                bw.newLine();
                bw.write("\t\tthis."+serviceBeanName+".update"+tableInfo.getBeanName()+"By"+methodName+"(bean,"+paramBuilder+");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"删除");
                methodNameResult="delete"+tableInfo.getBeanName()+"By"+methodName;
                bw.newLine();
                bw.write("\t@RequestMapping(\""+methodNameResult+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO "+methodNameResult+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\t this."+serviceBeanName+".delete"+tableInfo.getBeanName()+"By"+methodName+"("+paramBuilder+");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
                bw.newLine();

                bw.write("\t}");
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
