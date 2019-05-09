package com.example.graduatedesign.dao;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Long> {
  Place findByStatus(int status);
  Place findByPlaceName(String placeName);
}
