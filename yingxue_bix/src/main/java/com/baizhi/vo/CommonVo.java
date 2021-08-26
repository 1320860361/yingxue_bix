package com.baizhi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonVo {

    private String message;
    private Object status;

    public static CommonVo success(String message){
        CommonVo commonVo = new CommonVo();
        commonVo.setMessage(message);
        commonVo.setStatus(200);
        return commonVo;
    }

    public static CommonVo faild(String message){
        CommonVo commonVo = new CommonVo();
        commonVo.setMessage(message);
        commonVo.setStatus(400);
        return commonVo;
    }
}
