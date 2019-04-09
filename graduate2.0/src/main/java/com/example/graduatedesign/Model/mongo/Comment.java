package com.example.test1.demo.Model.mongo;

import com.example.test1.demo.Model.Activity;
import com.example.test1.demo.Model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {
    @Id
    private ObjectId _id;    //插入mongo时会自动生成_id，如果不加这个字段则会把id属性当成_id
    @Field
    private Long commentId;
    private String commentType;
    private Long userId;
    private Long activityId;
    private Long organizationId;
    private ObjectId parentId;
    private Long commentLikeCount;
    private List<String> content;
}
