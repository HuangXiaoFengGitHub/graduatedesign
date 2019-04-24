package com.example.graduatedesign.controller.frontent;

import com.example.graduatedesign.Model.ActivityCategory;
import com.example.graduatedesign.Model.Headline;
import com.example.graduatedesign.enums.ActivityCategoryStateEnum;
import com.example.graduatedesign.enums.HeadLineStateEnum;
import com.example.graduatedesign.service.ActivityCategoryService;
import com.example.graduatedesign.service.HeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private ActivityCategoryService activityCategoryService;
	@Autowired
	private HeadlineService headlineService;

	/**
	 * 初始化前端展示系统的头条信息和类别信息
	 * @return
	 */
	@RequestMapping(value = "/listMainPageInfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> list1stActivityCategory() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ActivityCategory> activityCategoryList = new ArrayList<ActivityCategory>();
		try {
			//获取一级列表，parentId为1的类别
			activityCategoryList = activityCategoryService.findFirstLever();
			modelMap.put("activityCategoryList", activityCategoryList);
		} catch (Exception e) {
			e.printStackTrace();
			ActivityCategoryStateEnum s = ActivityCategoryStateEnum.INNER_ERROR;
			modelMap.put("success", false);
			modelMap.put("errMsg", s.getStateInfo());
			return modelMap;
		}

		List<Headline> headLineList = new ArrayList<Headline>();
		try {
			Headline headLineCondition = new Headline();
			//headLineCondition.setEnableStatus(1);
			headLineList = headlineService.findEnableHeadline(1);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			e.printStackTrace();
			HeadLineStateEnum s = HeadLineStateEnum.INNER_ERROR;
			modelMap.put("success", false);
			modelMap.put("errMsg", s.getStateInfo());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}

}
