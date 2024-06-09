package com.Dickson.Mapperdao;

import com.Dickson.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface AdminMapper { // 这是第二种方法，不用xml去写数据库的方法，直接用注解annotation放在接口interface方法上方

    @Insert("insert into admin (username,password) values (#{username},#{password})")//这里为注解annotation 当interface子类调用这个方法，insert的数据逻辑就会先
    //进入到admin的constructor构造器然后在从构造器拿出来并且打印出来
    int saveAdmin(Admin admin);

    @Update("update admin set username=#{username},password=#{password} where id = #{id}")
    int updateAdmin(Admin admin);

    @Delete("delete from admin where id=#{id}")
    int deleteAdmin(int id);

    @Select("select id,username,password from admin where id=#{id}")
    Admin findAdminById(@Param("id") int id);

    @Select("select id,username,password from admin")
    List<Admin> findAllAdmins();

    @Select("select id,username,password from `admin` where id=#{p1}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "password"),
            @Result(property = "password", column = "username")
    })
    Admin getAdminById(@Param("p1") int input);

    @SelectProvider(type = AdminMapper.class, method = "getAdminByUsernamedemo")
    List<Admin> getAdminByUsername(String name);

    static String getAdminByUsernamedemo(String name) {
        return new SQL() {{
            SQL select = SELECT("*");
            FROM("admin");
            WHERE("username like #{name} '%'");
        }}.toString();
    }

    @SelectProvider(type = AdminMapper.class, method = "testmethod")
    List<Admin> demo3(String haha);

    static String testmethod(@Param("username") String fuck) {
        return new SQL() {{
            SQL select = SELECT("id,pas  sword");
            FROM("admin");
            WHERE("username=#{username}");
        }}.toString();
    }
}
