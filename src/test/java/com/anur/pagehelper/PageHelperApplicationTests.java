package com.anur.pagehelper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import com.anur.pagehelper.model.ProviderOrder;
import com.anur.pagehelper.service.TestService;
import com.github.pagehelper.PageHelper;
import net.bytebuddy.asm.Advice.Unused;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PageHelperApplication.class)
public class PageHelperApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    public void contextLoads() {
        PageHelper.startPage(1, 10);
        List<ProviderOrder> providerOrderList = testService.getProviderOrderWithOrderInfoTest("%-%");
        System.out.println(providerOrderList);
    }
}
