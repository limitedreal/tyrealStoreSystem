package com.tianlb.myTest.APIs.v1;

import com.tianlb.myTest.model.Spu;
import com.tianlb.myTest.BO.PageCounter;
import com.tianlb.myTest.VO.PagingDozer;
import com.tianlb.myTest.VO.SpuSimplifyVO;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.service.SpuService;
import com.tianlb.myTest.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive(message = "{id.positive}") Long id) {
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

    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu,SpuSimplifyVO> getByCategoryId(@PathVariable @Positive(message = "{id.positive}") Long id,
                                                          @RequestParam(name = "is_root",defaultValue = "false") Boolean isRoot,
                                                          @RequestParam(defaultValue = "0") Integer start,
                                                          @RequestParam(defaultValue = "10") Integer count){
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start,count);
        Page<Spu> page = this.spuService.getByCategoryId(id,isRoot,pageCounter.getPage(),pageCounter.getCount());
        return new PagingDozer<>(page,SpuSimplifyVO.class);
    }
}
