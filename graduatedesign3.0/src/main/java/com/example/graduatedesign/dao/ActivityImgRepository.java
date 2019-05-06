package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.Activity;
import com.example.graduatedesign.Model.ActivityImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityImgRepository  extends JpaRepository<ActivityImg,Long> {
    List<ActivityImg> findActivityImgsByActivity(Activity activity);
}
