package com.tyreal.myTest.APIs.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.tyreal.myTest.BO.PageCounter;
import com.tyreal.myTest.VO.Paging;
import com.tyreal.myTest.VO.PagingDozer;
import com.tyreal.myTest.VO.SpuSimplifyVO;
import com.tyreal.myTest.exception.http.NotFoundException;
import com.tyreal.myTest.model.Spu;
import com.tyreal.myTest.service.SpuService;
import com.tyreal.myTest.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive Long id) {
        Spu spu = spuService.getSpu(id);
        SpuSimplifyVO vo = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

    @GetMapping("/latest")
    public PagingDozer<Spu,SpuSimplifyVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                                @RequestParam(defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<Spu> pageSpu=spuService.getLatestPagingSpu(pageCounter.getPage(),pageCounter.getCount());
        return new PagingDozer<Spu,SpuSimplifyVO>(pageSpu,SpuSimplifyVO.class);
    }

}
