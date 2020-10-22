package com.tianlb.myTest.service;

import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.repository.ActivityRepository;
import com.tianlb.myTest.utils.CommonUtil;
import com.tianlb.myTest.exception.http.ParameterException;
import com.tianlb.myTest.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public Activity getByName(String name){

        Optional<Activity> optionalActivity = activityRepository.findOneByName(name);
        Activity activity = optionalActivity.orElseThrow(()-> new NotFoundException(40001));
        //下面判断是否过期
        Date date = new Date();
        Boolean isInTime = CommonUtil.isInTimeLine(date,activity.getStartTime(),activity.getEndTime());
        if(!isInTime){
            throw new ParameterException(40005);
        }
        return activity;
    }
}
