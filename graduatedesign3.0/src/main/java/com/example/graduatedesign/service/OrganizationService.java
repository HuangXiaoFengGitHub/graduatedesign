package com.example.graduatedesign.service;


import com.example.graduatedesign.Model.*;
import com.example.graduatedesign.dao.OrganizationRepository;
import com.example.graduatedesign.dao.UserRepostory;
import com.example.graduatedesign.dto.ActivityExecution;
import com.example.graduatedesign.dto.OrganizationExecution;
import com.example.graduatedesign.dto.UserExecution;
import com.example.graduatedesign.enums.ActivityState;
import com.example.graduatedesign.enums.OrganizationState;
import com.example.graduatedesign.enums.UserStateEnum;
import com.example.graduatedesign.service.serviceImp.OrganizationImp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.AttributeOverride;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrganizationService implements OrganizationImp {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    UserRepostory userRepostory;
    public Organization findById(long id)
    {
        return organizationRepository.findByOrganizationId(id);
    }
    public  List<Organization> findTop(long id)
    {
        return organizationRepository.findByParentId(id);
    }
    public List<Organization> findParent(String name)
    {
        return organizationRepository.findByOrganizationName(name);
    }
    public void save(Organization organization)
    {
        organizationRepository.save(organization);
    }
    public Organization checkLogin(String email,String password)
    {
        return organizationRepository.findByEmailAndPassword(email, password);
    }
    public List<Organization> findByOrganizationName(String organizationName){
        return organizationRepository.findByOrganizationName(organizationName);
    }
    public Organization findByOrganizationNameContain(String organizationName)
    {
        return organizationRepository.findByOrganizationNameContaining(organizationName);
    }
   public List<Organization> findOrganizationsByNameContain(String name)
    {
        return organizationRepository.findOrganizationsByOrganizationNameContaining(name);
    }
    public OrganizationExecution register(Organization organization, MultipartFile organizationImg, MultipartFile wechatImg)
    {
        return new OrganizationExecution(OrganizationState.SUCCESS,organization);
    }
    //public void addProfileImg(User user, CommonsMultipartFile profileImg);
    public OrganizationExecution modifyUser(Organization organization,MultipartFile organizationImg,MultipartFile wechatImg){
        return new OrganizationExecution(OrganizationState.SUCCESS,organization);
    }
    public boolean isLike(long organizationId, User user)
    {
        log.info("判断用户是否关注组织");
        if(user!=null)
        {
            User user1=userRepostory.findByUserId(user.getUserId());
            if(user1!=null)
            {
                Organization organization=organizationRepository.findByOrganizationId(organizationId);
                return user1.getLikeOrganizations().contains(organization);
            }
            else
                return false;
        }
        else {
            return false;
        }
    }

    /**
     * 该组织包含的活动标签
     * @param organizationId
     * @return
     */
    public List<Tags> findAllTags(long organizationId)
    {
        Organization organization=organizationRepository.findByOrganizationId(organizationId);
        if(organization!=null) {
            Set<Activity> activities=organization.getActivities();
            List<Tags> tags=new ArrayList<>();
            for(Activity activity:activities)
            {
                tags.add(activity.getTags());
            }
            return tags;
        }
        else {
            return null;
        }
    }
    /**
     * 组合查询，组织名称，组织类别
     * @param model
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public OrganizationExecution findSearch(Organization model, int pageIndex, int pageSize, String search)
    {
        if(model!=null && pageIndex>=0 && pageSize>0)
        {
            Sort sort= new Sort(Sort.Direction.DESC,"priority");
            Pageable pageable= PageRequest.of(pageIndex,pageSize,sort);
            Page<Organization> result = organizationRepository.findAll(new Specification<Organization>() {
                public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    //匹配状态,仅仅匹配已经审核成功的活动
                    if(model.getEnableStatus()>0){
                        list.add(cb.equal(root.get("enableStatus").as(Integer.class),model.getEnableStatus()));
                    }
                    //匹配活动名称,活动简介
                    if(StringUtils.isNotBlank(search))
                    {
                        Predicate pred=cb.and(cb.or(cb.like(root.get("organizationName"),"%"+search+"%"),
                                cb.or(cb.like(root.get("organizationDesc"),"%"+search+"%"))));
                        //   list.add(cb.like(root.get("activityName").as(String.class), "%" + model.getActivityName() + "%"));
                        list.add(pred);
                    }
                    // 活动类别
                    if (model.getOrganizationCategory() != null) {
                        list.add(cb.equal(root.get("organizationCategory").as(OrganizationCategory.class), model.getOrganizationCategory()));
                    }
                    // TO DO 匹配活动时间
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            },pageable);
            OrganizationExecution organizationExecution=new OrganizationExecution(OrganizationState.SUCCESS,result.getContent());//当前内容
            organizationExecution.setCount((int)result.getTotalElements());//活动总数
            return organizationExecution;
        }
        else {
            return new OrganizationExecution(OrganizationState.NULL_ACTIVITY_INFO);
        }
    }

}
