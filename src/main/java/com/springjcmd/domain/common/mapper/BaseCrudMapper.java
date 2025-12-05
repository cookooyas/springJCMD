package com.springjcmd.domain.common.mapper;

import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonSelectMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;

public interface BaseCrudMapper<T>
		extends CommonSelectMapper, CommonInsertMapper<T>, CommonUpdateMapper, CommonDeleteMapper {

}
