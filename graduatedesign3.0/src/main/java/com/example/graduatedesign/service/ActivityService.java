package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dao.*;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.service.serviceImp.ActivityServiceImp;
import com.example.graduatedesign.util.FileUtil;
import com.example.graduatedesign.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    //不能实现分页查询功能
    public Set<Activity> findActivityByCategory(String category)
    {
        return activityCategoryRepository.findByActivityCategoryName(category).getActivities();
    }

    public ActivityExecution findAll(int pageIndex,int pageSize)
    {
        if(pageIndex>=0 && pageSize>0)
        {
            Sort sort= new Sort(Sort.Direction.DESC,"priority");
            Pageable pageable2= PageRequest.of(pageIndex,pageSize,sort);
            Page<Activity> activityPage=activityRepository.findAll(pageable2);
            ActivityExecution activityExecution=new ActivityExecution(ActivityState.SUCCESS,activityPage.getContent());//当前内容
            activityExecution.setCount((int)activityPage.getTotalElements());//活动总数
            return activityExecution;

        }
        else {
            return new ActivityExecution(ActivityState.NULL_ACTIVITYID);
        }
    }
    /**
     * 组合查询，活动类别，组织名称，活动时间，活动状态（未开始，已经结束），活动标签
     * @param model
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ActivityExecution findSearch(Activity model,int pageIndex,int pageSize)
    {
        if(model!=null && pageIndex>=0 && pageSize>0)
        {
            Sort sort= new Sort(Sort.Direction.DESC,"priority");
            Pageable pageable= PageRequest.of(pageIndex,pageSize,sort);
            Page<Activity> result = activityRepository.findAll(new Specification<Activity>() {
                public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    //匹配状态,仅仅匹配已经审核成功的活动
                    if(model.getEnableStatus()>0){
                        list.add(cb.equal(root.get("enableStatus").as(Integer.class),model.getEnableStatus()));
                    }
                    //匹配组织
                    if (model.getOrganization()!=null) {
                        list.add(cb.equal(root.get("organization").as(Organization.class),model.getOrganization()));
                    }
                    //匹配活动名称
                    if(StringUtils.isNotBlank(model.getActivityName()))
                    {
                        list.add(cb.like(root.get("activityName").as(String.class), "%" + model.getActivityName() + "%"));
                    }
                    // 活动类别
                    if (model.getCategory() != null) {
                        list.add(cb.equal(root.get("category").as(ActivityCategory.class), model.getCategory()));
                    }
                    //匹配状态
                    if (StringUtils.isNotBlank(model.getStatus())) {
                        list.add(cb.like(root.get("status").as(String.class), "%" + model.getStatus() + "%"));
                    }
                    //匹配标签
                    if (model.getTags() != null) {
                        list.add(cb.equal(root.get("tags").as(Tags.class), model.getTags()));
                    }
                   // TO DO 匹配活动时间
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            },pageable);
            ActivityExecution activityExecution=new ActivityExecution(ActivityState.SUCCESS,result.getContent());//当前内容
            activityExecution.setCount((int)result.getTotalElements());//活动总数
            return activityExecution;
        }
        else {
            return new ActivityExecution(ActivityState.NULL_ACTIVITYID);
        }
    }
    /**
     * //能实现分页功能的,查询活动,根据分页查询查数据
     * @param activityCategory
     * @param pageIndex
     * @param pagesize
     * @return
     */
    public ActivityExecution findByCategoryPage(ActivityCategory activityCategory,int pageIndex,int pagesize)
    {
        Sort sort=new Sort(Sort.Direction.DESC,"priority");
        Pageable pageable= PageRequest.of(pageIndex,pagesize,sort);
        Page<Activity> activityPage=activityRepository.findActivitiesByCategory(activityCategory,pageable);
        ActivityExecution activityExecution=new ActivityExecution(ActivityState.SUCCESS,activityPage.getContent());//当前内容
        activityExecution.setCount((int)activityPage.getTotalElements());//活动总数
        return activityExecution;
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
