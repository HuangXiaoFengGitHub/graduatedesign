package com.example.graduatedesign.service;

import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dao.ActivityRepository;
import com.example.graduatedesign.dao.ManagerOrganizationRepository;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.ManagerOrganizationImp;
import com.example.graduatedesign.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerOrganizationService implements ManagerOrganizationImp {
    @Autowired
    ManagerOrganizationRepository managerOrganizationRepository;
    @Autowired
    UserRepostory userRepostory;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ActivityRepository activityRepository;
    public void save(ManagerOrganization managerOrganization)
    {
        managerOrganizationRepository.save(managerOrganization);
    }
    //管理的组织数量
    public int countAllByUser(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        return managerOrganizationRepository.countAllByUser(user);
    }
    public List<ManagerOrganization> findByUser(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        return managerOrganizationRepository.findByUser(user);
    }
    public List<ManagerOrganization> findByUserAndGrade(long userId,int grade)
    {
        User user=userRepostory.findByUserId(userId);
        return managerOrganizationRepository.findByUserAndGrade(user,grade);
    }
    public List<ManagerOrganization> findByOrganizationAndGrade(long organizationId, int grade)
    {
        Organization organization=organizationRepository.findByOrganizationId(organizationId);
        return managerOrganizationRepository.findByOrganizationAndGrade(organization,grade);
    }

    /**
     * 找到该管理员的所有审核活动,特定状态，特定组织
     * @return
     */
    public ActivityExecution findMyActivity(long userId, long organizationId,int grade, int activityStatus) {
        User user = userRepostory.findByUserId(userId);
        Organization organization = organizationRepository.findByOrganizationId(organizationId);
        if (user != null) {
            Sort sort = new Sort(Sort.Direction.DESC, "priority");
            //  Pageable pageable= PageRequest.of(pageIndex,pageSize,sort);
            List<ManagerOrganization> result = managerOrganizationRepository.findAll(new Specification<ManagerOrganization>() {
                public Predicate toPredicate(Root<ManagerOrganization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    //匹配状态,仅仅匹配已经审核成功的活动
                    if (user != null) {
                        list.add(cb.equal(root.get("user").as(User.class), user));
                    }
                    //匹配组织
                    if (organization != null) {
                        list.add(cb.equal(root.get("organization").as(Organization.class), organization));
                    }
                    //匹配状态
                    if (grade != 0) {
                        list.add(cb.equal(root.get("grade").as(Integer.class), grade));
                    }
                    // TO DO 匹配活动时间
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            });
            List<Activity> activityList=new ArrayList<>();
            for(ManagerOrganization managerOrganization:result)
            {
                Organization organization1=managerOrganization.getOrganization();
                //查找特定状态的活动
                if(activityStatus!=0)
                    activityList.addAll(activityRepository.findActivitiesByOrganizationAndStatus(organization1,activityStatus));
                //查找全部活动
                else
                    activityList.addAll(activityRepository.findActivitiesByOrganization(organization1));
            }
            ActivityExecution activityExecution = new ActivityExecution(ActivityState.SUCCESS, activityList);//当前内容
            return activityExecution;
        }
        else {
            return new ActivityExecution(ActivityState.NULL_ACTIVITY_INFO);
        }
    }
    public UserExecution checkLogin(String email, String password)
    {
        if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password))
        {
            String password1= MD5Util.getMd5(password);
            User user=userRepostory.findByEmailAndPassword(email,password1);
            if(user!=null && user.getIsManager()==1)
                return new UserExecution(UserStateEnum.SUCCESS,user);
            else
                return new UserExecution(UserStateEnum.LOGINFAIL);
        }
        else
        {
            return new UserExecution(UserStateEnum.NULL_AUTH_INFO);
        }
    }
    /**
     * 待审核活动
     * @param userId
     * @return
     */
    public int findCheckingNumber(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        List<ManagerOrganization> managerOrganizations=managerOrganizationRepository.findByUser(user);
        int count=0;
        for(ManagerOrganization managerOrganization:managerOrganizations)
        {
            count+=activityRepository.countActivityByOrganizationAndStatus(managerOrganization.getOrganization(),1);
        }
        return count;
    }

    /**
     * 该管理员下的活动数量
     * @param userId
     * @return
     */
    public int findCheckedActivity(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        List<ManagerOrganization> managerOrganizations=managerOrganizationRepository.findByUser(user);
        int count=0;
        for(ManagerOrganization managerOrganization:managerOrganizations)
        {
            count+=activityRepository.countActivityByOrganization(managerOrganization.getOrganization());
        }
        return count;
    }

    /**
     * 该管理员下的已经审核的活动数量
     * @param userId
     * @return
     */
    public int checked(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        List<ManagerOrganization> managerOrganizations=managerOrganizationRepository.findByUser(user);
        int count=0;
        for(ManagerOrganization managerOrganization:managerOrganizations)
        {
            count+=activityRepository.countActivityByOrganizationAndStatusBetween(managerOrganization.getOrganization(),2,3);
        }
        return count;
    }

    /**
     * 组织数量
     * @param userId
     * @return
     */
    public int organizationNum(long userId)
    {
        User user=userRepostory.findByUserId(userId);
        return managerOrganizationRepository.findByUser(user).size();
    }
}
