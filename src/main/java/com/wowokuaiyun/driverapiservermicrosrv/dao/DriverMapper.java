package com.wowokuaiyun.driverapiservermicrosrv.dao;

import com.wowokuaiyun.driverapiservermicrosrv.entity.Driver;
import com.wowokuaiyun.driverapiservermicrosrv.entity.DriverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DriverMapper {
    long countByExample(DriverExample example);

    int deleteByExample(DriverExample example);

    int insert(Driver record);

    int insertSelective(Driver record);

    List<Driver> selectByExample(DriverExample example);

    int updateByExampleSelective(@Param("record") Driver record, @Param("example") DriverExample example);

    int updateByExample(@Param("record") Driver record, @Param("example") DriverExample example);
}