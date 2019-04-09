package com.example.graduatedesign.dao.mongo;

import com.example.graduatedesign.Model.mongo.ActivityMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoRepository extends MongoRepository<ActivityMongo,Long> {
    ActivityMongo findByActivityId(long id);
}
