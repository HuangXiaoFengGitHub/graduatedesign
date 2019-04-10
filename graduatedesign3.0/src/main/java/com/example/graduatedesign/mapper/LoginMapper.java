package com.example.graduatedesign.mapper;


import com.example.graduatedesign.Model.Login;
import com.example.graduatedesign.Model.LoginExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface LoginMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    long countByExample(LoginExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    int deleteByExample(LoginExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    @Delete({
        "delete from login",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    @Insert({
        "insert into login (name, phone)",
        "values (#{name,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Login record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    int insertSelective(Login record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    List<Login> selectByExampleWithRowbounds(LoginExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    List<Login> selectByExample(LoginExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    @Select({
        "select",
        "id, name, phone",
        "from login",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.example.test1.demo.mapper.LoginMapper.BaseResultMap")
    Login selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    int updateByExampleSelective(@Param("record") Login record, @Param("example") LoginExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    int updateByExample(@Param("record") Login record, @Param("example") LoginExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    int updateByPrimaryKeySelective(Login record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table login
     *
     * @mbg.generated Tue Mar 19 13:17:23 CST 2019
     */
    @Update({
        "update login",
        "set name = #{name,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Login record);
}