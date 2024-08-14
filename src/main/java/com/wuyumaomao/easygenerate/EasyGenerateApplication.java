package com.wuyumaomao.easygenerate;

import com.wuyumaomao.easygenerate.bean.TableInfo;
import com.wuyumaomao.easygenerate.builder.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EasyGenerateApplication {

    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTables.getTables();
        BuildBase.execute();
        for(TableInfo tableInfo:tableInfoList){
             BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
            BuildMapper.execute(tableInfo);
        }
    }

}
