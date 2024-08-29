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

        File folder=new File(Contans.PATH_SERVICE_IMPL);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String interfaceName=tableInfo.getBeanName()+"Service";
        String className=tableInfo.getBeanName()+"ServiceImpl";
        File poFile=new File(folder,className+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        try {
            out=new FileOutputStream(poFile);
            outw=new OutputStreamWriter(out,"utf8");
            bw=new BufferedWriter(outw);
            bw.write("package "+Contans.PACKAGE_SERVICE_IMPL+";");
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
            String mapperName=tableInfo.getBeanName()+Contans.Suffix_Mappers;
            String mapperBeanName=StringUtils.lowerCaseFirstLetter(mapperName);
            bw.write("import "+Contans.PACKAGE_VO+".PaginationResultVO;");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_PO+"."+tableInfo.getBeanName()+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_QUERY+"."+tableInfo.getBeanParamName()+";");
            bw.newLine();
            bw.write("import org.springframework.stereotype.Service;");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_SERVICE+"."+interfaceName+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_MAPPER+"."+mapperName+";");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_QUERY+".SimplePage;");
            bw.newLine();
            bw.write("import "+Contans.PACKAGE_ENUM+".PageSize;");
            bw.newLine();
            bw.write("import jakarta.annotation.Resource;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment()+"Service");
            bw.write("@Service(\""+StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName())+"Service\")");
            bw.newLine();
            bw.write("public class "+className+" implements "+interfaceName+"{");
            bw.newLine();
            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate "+mapperName+"<"+tableInfo.getBeanName()+","+tableInfo.getBeanParamName()+"> "+StringUtils.lowerCaseFirstLetter(mapperName)+";" );
            bw.newLine();
            BuildComment.createClassComment(bw, "根据条件查询列表");
            bw.write("\tpublic List<"+tableInfo.getBeanName()+"> findByParam("+tableInfo.getBeanParamName()+" query){");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".selectList(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createClassComment(bw, "根据条件查询数量");
            bw.write("\tpublic Integer findCountByParam( "+tableInfo.getBeanParamName()+" query){");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".selectCount(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"分页查询");
            bw.write("\tpublic PaginationResultVO<"+tableInfo.getBeanName()+"> findListByPage("+tableInfo.getBeanParamName()+" query){");
            bw.newLine();
            bw.write("\t\tInteger count=this.findCountByParam(query);");
            bw.newLine();
            bw.write("\t\tInteger pageSize=query.getPageSize()==null?PageSize.SIZE15.getSize():query.getPageSize();");
            bw.newLine();
            bw.write("\t\tSimplePage page=new SimplePage(query.getPageNo(),count,pageSize);");
            bw.newLine();
            bw.write("\t\tquery.setSimplePage(page);");
            bw.newLine();
            bw.write("\t\tList<"+tableInfo.getBeanName()+"> list=this.findByParam(query);");
            bw.newLine();
            bw.write("\t\tPaginationResultVO<"+tableInfo.getBeanName()+"> result=new PaginationResultVO(count,page.getPageSize(),page.getPageNo(),page.getPageTotal(),list);");
            bw.newLine();
            bw.write("\t\treturn result;");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"新增");
            bw.write("\tpublic Integer add("+tableInfo.getBeanName()+" bean){");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".insert(bean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增");
            bw.write("\tpublic Integer addBatch(List<"+tableInfo.getBeanName()+"> listBean){");
            bw.newLine();
            bw.write("\t\tif (listBean==null||listBean.isEmpty()){");
            bw.newLine();
            bw.write("\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".insertBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            BuildComment.createFieldComment(bw,"批量新增或修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<"+tableInfo.getBeanName()+"> listBean){");
            bw.newLine();
            bw.write("\t\tif (listBean==null||listBean.isEmpty()){");
            bw.newLine();
            bw.write("\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".insertOrUpdateBatch(listBean);");
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

                bw.write("\tpublic "+tableInfo.getBeanName()+" get"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".selectBy"+methodName+"("+paramBuilder+");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"更新");
                bw.write("\tpublic Integer update"+tableInfo.getBeanName()+"By"+methodName+"("+tableInfo.getBeanName()+" bean,"+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".updateBy"+methodName+"(bean,"+paramBuilder+");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                BuildComment.createFieldComment(bw,"根据"+methodName+"删除");
                bw.write("\tpublic Integer delete"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".deleteBy"+methodName+"("+paramBuilder+");");
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
