package com.Dickson.Mapperdao;

import com.Dickson.entity.Employee;
import com.Dickson.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FunctionMapper {

    @Select("select * from `employee` where passcode = #{passcode};")
    Employee login(@Param("passcode") Integer passcode);

    @Select("select em_name from `employee` where passcode = #{passcode} ")
    String show_name(Integer passcode);

    @Select("select product_name from `product` where product_id = #{product_id}")
    String displayselectioname(String product_id);

    @Select("select product_image from `product` where product_id = #{product_id}")
    String  selectProductImagesById(String product_id);

    @Select("select product_image from product where product_id like CONCAT(#{prefix}, '%')")
    List<String> selectProductImagesByPrefix(String prefix);



    @Select("select product_id from product where product_id like CONCAT(#{prefix}, '%')")
    List<Product> selectProductImagesByPrefix1(String prefix);

    @Select("select product_image from product where product_id = #{id}")
    List<String> selectProductImagesById1(String id);

    @Select("select * from product where product_id = #{id}")
    Product selectProductId1(String id);

    @Update("UPDATE product SET product_id = #{id}, product_name = #{name}, product_category = #{category}, price = #{price}, product_image = #{image} WHERE product_id = #{product}")
    void updateProduct(@Param("id") String id, @Param("name") String name, @Param("category") String category, @Param("price") Double price, @Param("image") String image, @Param("product") String product);


}
