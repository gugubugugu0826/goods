package com.billy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @TableId(type = IdType.AUTO)
    private int id;
    private String goodsname;
    private String brand;
    private int views;
    private double price;
    private int transactions;
    private int rate;
    private String time;
    private String type;
    private String province;
    private int goodsnumber;
}
