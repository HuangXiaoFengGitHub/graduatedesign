package com.example.test1.demo.dao.jpa;

import com.example.test1.demo.Model.jpa.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
    List<JpaUser> findTop3ByOrderByIdAsc();
    //jpa有默认实现类似findBy*(支持*And*)方法，其中*号为实体属性名(名称写的不对会报错哦)
    List<JpaUser> findByUsername(String username);
    JpaUser findById(long id);

    //自定义方法
    @Query("select count(u.id) from JpaUser u")
    long count();

    @Query("select u from JpaUser u where u.id=:id")
    JpaUser findUserById(@Param("id") long id);
}
