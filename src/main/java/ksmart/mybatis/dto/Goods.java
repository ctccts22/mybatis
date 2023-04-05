package ksmart.mybatis.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Goods {
    private String goodsCode;
    private String goodsName;
    private int goodsPrice;
    private String goodsSellerId;
    private LocalDateTime goodsRegDate;

    private Member sellerInfo;
}
