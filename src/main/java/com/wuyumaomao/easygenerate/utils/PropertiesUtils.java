package com.wuyumaomao.easygenerate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesUtils {
    private static Properties props=new Properties();
    private static Map<String,String> Proper_Map=new ConcurrentHashMap<>();
    static {
        InputStream is=null;
        try {
           is=PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
          props.load(new InputStreamReader(is,"gbk"));
            Iterator<Object> iterator=props.keySet().iterator();
            while(iterator.hasNext()){
               String key=iterator.next().toString();
               Proper_Map.put(key,props.getProperty(key));
            }
        }catch (Exception e){

        }finally {
            if(is!=null){
                try {
                   is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static String getString(String key){
        return Proper_Map.get(key);
    }
//    public static void main(String[] args) {
//        System.out.println(props.getProperty("db.url"));
//    }
}
