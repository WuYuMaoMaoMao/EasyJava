package com.wuyumaomao.easygenerate.builder;

import com.wuyumaomao.easygenerate.bean.Contans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BuildBase {
    private static final Logger logger= LoggerFactory.getLogger(BuildBase.class);
    public static void execute(){
        List<String> headerInfoList=new ArrayList<>();
        headerInfoList.add("package"+" "+Contans.PACKAGE_ENUM);
        build(headerInfoList,"DateTimePatternEnum",Contans.PATH_ENUM);
        headerInfoList.clear();
        headerInfoList.add("package"+" "+Contans.package_Utils);
       build(headerInfoList,"DateUtil", Contans.PATH_UTILS);

        headerInfoList.clear();
        headerInfoList.add("package"+" "+Contans.PACKAGE_MAPPER);
        build(headerInfoList,"BaseMapper", Contans.PATH_MAPPER);

    }
    public static void build(List<String> headerInfoList, String fileName, String outPutPath){
        File folder=new File(outPutPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File JavaFile=new File(outPutPath,fileName+".java");
        OutputStream out=null;
        OutputStreamWriter outw=null;
        BufferedWriter bw=null;
        InputStream in=null;
        InputStreamReader inr=null;
        BufferedReader bf=null;
        try{
           out =new FileOutputStream(JavaFile);
           outw=new OutputStreamWriter(out,"utf-8");
           bw=new BufferedWriter(outw);
           String templatePath= BuildBase.class.getClassLoader().getResource("template/"+fileName+".txt").getPath();

           in=new FileInputStream(templatePath);
           inr=new InputStreamReader(in,"utf-8");
           bf=new BufferedReader(inr);
           for(String head:headerInfoList){
               bw.write(head+";");
               bw.newLine();
               if(head.contains("package")){
                   bw.newLine();
               }
           }
            String lineInfo=null;
           while((lineInfo=bf.readLine())!=null){
                bw.write(lineInfo);
                bw.newLine();
           }
           bw.flush();

        }catch (Exception e){
           logger.error(String.valueOf(e));
        }finally {
           if (inr!=null){
               try {
                  inr.close();
               }catch (IOException e){
                  e.printStackTrace();
               }
           }
            if(bw!=null){
                try {
                    bw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(in!=null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(bf!=null){
                try {
                   bf.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
