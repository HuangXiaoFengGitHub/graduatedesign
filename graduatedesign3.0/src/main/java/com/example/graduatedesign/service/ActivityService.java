package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dao.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.service.serviceImp.ActivityServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.security.PublicKey;
import java.util.*;

@Service
@Slf4j
public class ActivityService implements ActivityServiceImp {
    @Autowired
    ActivityCategoryRepository activityCategoryRepository;
    @Autowired
    TagsRepository tagRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ActivityImgRepository activityImgRepository;

    public Activity findActivityById(long id)
    {
        return activityRepository.findByActivityId(id);
    }
    //分页查询活动，可输入的条件有：活动类别，活动标签，活动状态，组织名称，活动时间等等
    public Set<Activity> findActivityByCategory(String category)
    {
        return activityCategoryRepository.findByActivityCategoryName(category).getActivities();
    }
    public Set<Activity> findActivityByTags(String tags)
    {
        return tagRepository.findByTagName(tags).getActivities();
    }
    public List<Activity> findActivityByState(ActivityState state)
    {
        return  activityRepository.findActivitiesByStatus(state);
    }

    /**
     * 模糊查询组织名称
     * @param
     * @return
     */
    @Transactional
    public List<Activity> findActivityByOrganization(Organization organization2)
    {
        List<Organization> organizations=organizationRepository.findByOrganizationNameLike(organization2.getOrganizationName());
        List<Activity> list=new ArrayList<>();
        for(Organization organization:organizations)
        {
            List<Activity> activityList=activityRepository.findActivitiesByOrganization(organization);
            list.addAll(activityList);
        }
        return list;
    }
    public List<Activity> findActivityByTime(Calendar startTime, Calendar endTime)
    {
        return activityRepository.findActivitiesByStartTimeAndEndTime(startTime,endTime);
    }

    @Transactional
    public ActivityExecution addActivity(Activity activity, MultipartFile activityImg, List<MultipartFile> activityImgs)
    {
        if (activity != null && activity.getOrganization() != null && activity.getOrganization().getOrganizationId() != 0) {
            activity.setCreateTime(Calendar.getInstance());
            activity.setUpdateTime(Calendar.getInstance());

            if (activityImg != null) {
                addProfileImg(activity, activityImg);//添加缩略图
            }
            try {
                long effectedNum = activityRepository.saveAndFlush(activity).getActivityId();
                if (effectedNum <= 0) {
                    throw new RuntimeException("创建活动失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建活动失败:" + e.toString());
            }
            if (activityImgs != null && activityImgs.size() > 0) {
                addProductImgs(activity, activityImgs); //批量添加商品详情图
            }
            return new ActivityExecution(ActivityState.SUCCESS, activity);
        } else {
            return new ActivityExecution(ActivityState.NULL_ACTIVITYID);
        }
    }
    //组织创建和申请活动的时候用
    private void addProfileImg(Activity activity, MultipartFile profileImg) {
        log.info("begin add profileImg:");
        String dest = FileUtil.getPersonInfoImagePath();//"/upload/images/item/userinfo/";
        String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);//创建文件，获取文件名
        activity.setImgAddr(profileImgAddr);
        log.info("profile dir:"+activity.getImgAddr());
    }
    //组织创建和申请活动的时候用
    private void addProductImgs(Activity activity, List<MultipartFile> activityImgs) {
        Activity activity1=activityRepository.findByActivityId(activity.getActivityId());//获取持久化对象
        String dest = FileUtil.getShopImagePath(activity.getOrganization().getOrganizationId());
        List<String> imgAddrList = ImageUtil.generateNormalImgs(activityImgs, dest);
        if (imgAddrList != null && imgAddrList.size() > 0) {
            List<ActivityImg> activityImgList = new ArrayList<ActivityImg>();
            for (String imgAddr : imgAddrList) {
                ActivityImg activityImg = new ActivityImg();
                activityImg.setImageAddr(imgAddr);
                activityImg.setActivity(activity1);//创建图片，维护方主动添加被维护方
                activityImg.setCreateTime(Calendar.getInstance());
                activityImgList.add(activityImg);
            }
            try {
                //TO DO 批量插入活动图片
                for(ActivityImg activityImg:activityImgList)
                {
                    activityImgRepository.save(activityImg);
                }
                //int effectedNum = productImgDao.batchInsertActivityImg(productImgList);
//                if (effectedNum <= 0) {
//                    throw new RuntimeException("创建商品详情图片失败");
//                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品详情图片失败:" + e.toString());
            }
        }
    }

}
