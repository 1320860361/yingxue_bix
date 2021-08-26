package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String sign;

    @Column(name = "head_img")
    private String headImg;

    private String phone;

    private String wechat;

    private String status;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String sex;

    private String city;
}