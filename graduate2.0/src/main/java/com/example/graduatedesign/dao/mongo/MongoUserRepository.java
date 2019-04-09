package com.example.test1.demo.dao.mongo;

import com.example.test1.demo.Model.mongo.MgUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<MgUser, Long> {

    //有默认实现类似findBy*(支持*And*)方法，其中*号为实体属性名
    MgUser findByNickname(String nickname);

    //根据id为条件搜索(默认实现的findOne方法是以mongodb中ObjectId为条件的)
    //MgUser findByMgid(Long mid) ;

    List<MgUser> findByMgid(Long mid);
}
