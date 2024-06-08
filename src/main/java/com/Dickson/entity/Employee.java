package com.Dickson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private static final Long serialVersionUID = 1L;

    private Integer em_id;

    private String em_name;

    private Character sex;

    private String roles;

    private Integer phone_no;

    private Integer passcode;

    private Date log_in_time;

    private Date log_out_time;

}
