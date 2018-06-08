package com.anur.pagehelper.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.anur.pagehelper.dao.ProviderOrderMapper;
import com.anur.pagehelper.model.ProviderOrder;
import com.github.pagehelper.PageHelper;
/**
 * Created by Anur IjuoKaruKas on 2018/6/7
 */
@Service
public class TestService {

    @Autowired
    private ProviderOrderMapper providerOrderMapper;

    public List<ProviderOrder> getProviderOrderWithOrderInfoTest(String id) {
        return providerOrderMapper.getProviderOrderWithOrderInfoTest(id);
    }
}
