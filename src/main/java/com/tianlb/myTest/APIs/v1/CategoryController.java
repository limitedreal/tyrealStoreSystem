package com.tianlb.myTest.APIs.v1;

import com.tianlb.myTest.VO.CategoryAllVo;
import com.tianlb.myTest.exception.http.NotFoundException;
import com.tianlb.myTest.model.Category;
import com.tianlb.myTest.model.GridCategory;
import com.tianlb.myTest.service.CategoryService;
import com.tianlb.myTest.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("category")
@RestController
@ResponseBody
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public CategoryAllVo getAll(){
        Map<Integer, List<Category>> categories = categoryService.getAll();
        return new CategoryAllVo(categories);
    }

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList(){
        List<GridCategory> gridCategoryList = gridCategoryService.getGridCategoryList();
        if(gridCategoryList.isEmpty()){
            throw new NotFoundException(30009);
        }
        return gridCategoryList;
    }

}
