package com.example.test1.demo.Model.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
//需要与mongo中对象(文档)名一致
public class ActivityMongo {
    @Id
    private ObjectId _id;    //插入mongo时会自动生成_id，如果不加这个字段则会把id属性当成_id
    @Field
    private Long activityId;
    private List<String> tags;
    private List<String> notices;

}
