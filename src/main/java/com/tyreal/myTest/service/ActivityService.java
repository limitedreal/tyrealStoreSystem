package com.tyreal.myTest.service;

import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.model.Activity;
import com.tyreal.myTest.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public Activity getByName(String name){
        Optional<Activity> optionalActivity = activityRepository.findOneByName(name);
        return optionalActivity.orElseThrow(()-> new NotFoundException(40001));
    }
}
