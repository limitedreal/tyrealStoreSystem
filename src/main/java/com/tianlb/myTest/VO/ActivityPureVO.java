package com.tianlb.myTest.VO;

import com.tianlb.myTest.model.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
public class ActivityPureVO {
    private Long id;
    private String title;
    private String entranceImg;
    private Boolean online;
    private String remark;
    private Date startTime;
    private Date endTime;
    public ActivityPureVO(Activity activity){
        BeanUtils.copyProperties(activity,this);
    }
}
