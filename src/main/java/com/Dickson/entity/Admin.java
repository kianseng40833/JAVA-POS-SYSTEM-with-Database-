package com.Dickson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.jdbc.SQL;

@Data
@AllArgsConstructor
@NoArgsConstructor //这三个annotaion注解都是loombok插件，方便我们创建构造器，getter, setter, toString
public class Admin { //之所以在entity下面是因为，entity代表了mysql里的表，所以当我们要使用coding写入database就一定要跟着databsae里的表抄进来java-class

    private static final Long serialVersionUID = 1L;

    private int id; //你的数据库表格如果attributes有id那就在java类也创建一个数据格式一摸一样的成员变量
    private String username; // 一样的道理
    private String password; // 一样的道理

    public static String getAdminByUsername(String username) {
        return new SQL() {{
            SELECT("*");
            FROM("`admin`");
            WHERE("username like #{username} || '%'");
        }}.toString();
    } //这个只是测试不用管
}
