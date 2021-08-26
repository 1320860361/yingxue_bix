package com.baizhi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageDTO {

    private Integer page;
    private Integer pageSize;
    private String categoryId;

}
