package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityComment;
import com.example.graduatedesign.Model.UserActivityComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//评论，需要分页查询
public interface ActivityCommentRepository extends JpaRepository<ActivityComment,Long>, JpaSpecificationExecutor<ActivityComment> {
    ActivityComment findByCommentId(long commentId);

}
