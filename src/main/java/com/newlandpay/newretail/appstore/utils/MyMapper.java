package com.newlandpay.newretail.appstore.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    long DEFAULT_VERSION = 1l;

    default int deleteWithVersion(T t) {
        int result = delete(t);
        if (result == 0) {
            throw new RuntimeException("交易繁忙，请稍后再试!");
        }
        return result;
    }

    default int updateByPrimaryKeyWithVersion(T t) {
        int result = updateByPrimaryKey(t);
        if (result == 0) {
            throw new RuntimeException("交易繁忙，请稍后再试！");
        }
        return result;
    }


}
