package com.example.test1.demo.dao.mongo;

import com.example.test1.demo.Model.mongo.ActivityMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoRepository extends MongoRepository<ActivityMongo,Long> {
    ActivityMongo findByActivityId(long id);
}
