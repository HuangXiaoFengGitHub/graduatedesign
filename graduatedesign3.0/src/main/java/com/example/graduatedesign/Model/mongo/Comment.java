package com.example.graduatedesign.Model.mongo;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {
    @Id
    private ObjectId _id;    //插入mongo时会自动生成_id，如果不加这个字段则会把id属性当成_id
    @Field
    private Long commentId;
    private String commentType; //留言，活动评论，学校通知
    private Long userId;
    private Long activityId;
    private Long organizationId;
    private ObjectId parentId;
    private Long commentLikeCount;
    private List<String> content;
}
