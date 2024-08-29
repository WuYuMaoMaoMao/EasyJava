package com.wuyumaomao.easygenerate;

import com.wuyumaomao.easygenerate.mappers.InfoMapper;
import com.wuyumaomao.easygenerate.po.Info;
import com.wuyumaomao.easygenerate.query.InfoQuery;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

class EasyGenerateApplicationTests {
    @Resource
    private InfoMapper<Info, InfoQuery> infoMapper;
    @Test
    void contextLoads() {
        List<Info> user = infoMapper.selectList(new InfoQuery());
        System.out.println(user.size());
    }

}
