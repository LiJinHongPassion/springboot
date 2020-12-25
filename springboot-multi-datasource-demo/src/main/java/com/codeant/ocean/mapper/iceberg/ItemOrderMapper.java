package com.codeant.ocean.mapper.iceberg;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface ItemOrderMapper {

    /**
     * 权益购买数（包含兑换,领取,兑换）
     *
     * @param start start
     * @param end   end
     * @return count
     */
    Integer selectCountSuccessOrder(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
