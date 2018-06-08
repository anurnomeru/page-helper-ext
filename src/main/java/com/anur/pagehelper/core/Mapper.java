package com.anur.pagehelper.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface Mapper<T>
        extends
    BaseMapper<T>,
    ConditionMapper<T>,
    IdsMapper<T>,
    InsertListMapper<T> {
}