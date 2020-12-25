package com.codeant.ocean.mapper.account;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface UserMapper {

    /**
     * 查看总用户数
     *
     * @param start start
     * @param end   end
     * @return count
     */
    Integer selectCountUser(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
