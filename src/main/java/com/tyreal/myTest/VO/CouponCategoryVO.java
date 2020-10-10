package com.tyreal.myTest.VO;

import com.tyreal.myTest.VO.CategoryPureVO;
import com.tyreal.myTest.VO.CouponPureVO;
import com.tyreal.myTest.model.Category;
import com.tyreal.myTest.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CouponCategoryVO  extends CouponPureVO {
    private List<CategoryPureVO> categories = new ArrayList<>();

    public CouponCategoryVO(Coupon coupon) {
        super(coupon);
        List<Category> categories = coupon.getCategoryList();
        categories.forEach(category->{
            CategoryPureVO vo = new CategoryPureVO(category);
            this.categories.add(vo);
        });
    }
}
