package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.category.model.GetCategoryRes;
import com.example.demo.src.category.model.GetCategoryStoreRes;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.stores.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CategoryProvider categoryProvider;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final JwtService jwtService;

    public CategoryController(CategoryProvider categoryProvider, CategoryService categoryService, JwtService jwtService){
        this.categoryProvider = categoryProvider;
        this.categoryService = categoryService;
        this.jwtService = jwtService;
    }

    /**
     * 전체 카테고리 조회 API
     * [GET] /category
     * @return BaseResponse<GetCategoryRes>
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/category
    public BaseResponse<List<GetCategoryRes>> getCategory() {
        try{
            List<GetCategoryRes> getCategoryRes = categoryProvider.getCategoryRes();
            return new BaseResponse<>(getCategoryRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 카테고리 가게 전체 조회 API
     * [GET] category/:categoryId/stores
     * @return BaseResponse<List<GetCategoryStoreRes>>
     */
    @ResponseBody
    @GetMapping("/{categoryId}/stores") // (GET) 127.0.0.1:9000/review/users/:userid
    public BaseResponse<List<GetCategoryStoreRes>> getCategoryStoreRes(@PathVariable("categoryId") int categoryId, @RequestHeader("userId") int userId) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetCategoryStoreRes> getCategoryStoreRes = categoryProvider.getCategoryStoreRes(categoryId);
            return new BaseResponse<>(getCategoryStoreRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 카테고리 가게 정렬 조회 API
     * [GET] category/:categoryId/:sorttype/stores
     * @return BaseResponse<List<GetCategoryStoreRes>>
     */
    @ResponseBody
    @GetMapping("/{categoryId}/{sorttype}/stores") // (GET) 127.0.0.1:9000/category/:categoryId/:sorttype/stores
    public BaseResponse<List<GetCategoryStoreRes>> getCategoryStoreRes(@PathVariable("categoryId") int categoryId, @PathVariable("sorttype") String sortType, @RequestHeader("userId") int userId) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetCategoryStoreRes> getCategoryStoreRes = categoryProvider.getCategoryStoreSortRes(categoryId, sortType);
            return new BaseResponse<>(getCategoryStoreRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
