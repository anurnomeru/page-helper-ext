package com.anur.pagehelper.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.anur.pagehelper.core.Mapper;
import com.anur.pagehelper.model.ProviderOrder;

public interface ProviderOrderMapper extends Mapper<ProviderOrder> {
    List<ProviderOrder> getProviderOrderWithOrderInfoTest(@Param("po_id") String po_id);
}