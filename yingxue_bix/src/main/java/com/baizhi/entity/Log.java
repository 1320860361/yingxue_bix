package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    private String id;

    @Column(name ="admin_name")
    private String adminName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "option_time")
    private Date optionTime;

    @Column(name = "method_name")
    private String methodName;

    private String status;

}