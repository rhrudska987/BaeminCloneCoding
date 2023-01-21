package com.example.demo.src.stores;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.stores.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/stores")
public class StoresController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoresProvider storesProvider;
    @Autowired
    private final StoresService storesService;
    @Autowired
    private final JwtService jwtService;

    public StoresController(StoresProvider storesProvider, StoresService storesService, JwtService jwtService){
        this.storesProvider = storesProvider;
        this.storesService = storesService;
        this.jwtService = jwtService;
    }

    /**
     * 유저 가게 생성 API
     * [PATCH] stores/users/:userId/
     * @return BaseResponse<PostStoresRes>
     */
    @ResponseBody
    @PostMapping("/users/{userId}")
    public BaseResponse<PostStoresRes> createStore(@PathVariable("userId") int userId, @RequestBody PostStoresReq postStoresReq) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(postStoresReq.getStoreName() == null) return new BaseResponse<>(POST_STORE_EMPTY_STORENAME);
            if(postStoresReq.getTelephoneNumber() == null) return new BaseResponse<>(POST_STORE_EMPTY_TELEPHONENUMBER);
            if(postStoresReq.getAddress() == null) return new BaseResponse<>(POST_STORE_EMPTY_ADDRESS);
            if(postStoresReq.getStoreImage() == null) return new BaseResponse<>(POST_STORE_EMPTY_STOREIMAGE);

            PostStoresRes postStoresRes = storesService.createStore(postStoresReq);
            return new BaseResponse<>(postStoresRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 삭제 API
     * [PATCH] /stores/:storeId/:userId/deletion
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{storeId}/{userId}/deletion")
    public BaseResponse<String> removeStore(@PathVariable("userId") int userId, @PathVariable("storeId") int storeId){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 가게 삭제
            storesService.removeStore(storeId);
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 가게 조회 API
     * [GET] /stores/:storeId
     * @return BaseResponse<GetStoresRes>
     */
    @ResponseBody
    @GetMapping("/{storeId}") // (GET) 127.0.0.1:9000/stores/:storeId
    public BaseResponse<List<GetStoresRes>> getStores(@PathVariable("storeId") int storeId) {
        try{
            List<GetStoresRes> getStoresRes = storesProvider.getStoresRes(storeId);
            return new BaseResponse<>(getStoresRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 정보 수정 API
     * [PATCH] /stores/:storeId/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{storeId}/{userId}")
    public BaseResponse<String> modifyUserInfo(@PathVariable("storeId") int storeId, @PathVariable("userId") int userId, @RequestBody Stores stores){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 가게정보 변경
            GetStoresRes getStoresRes = storesProvider.getStoresInfo(storeId);
            Object[] objects = new Object[11];
            if (stores.getStoreImage1() == null) objects[0] = getStoresRes.getStoreImage1();
            else objects[0] = stores.getStoreImage1();
            if (stores.getStoreImage2() == null) objects[1] = getStoresRes.getStoreImage2();
            else objects[1] = stores.getStoreImage2();
            if (stores.getStoreImage3() == null) objects[2] = getStoresRes.getStoreImage3();
            else objects[2] = stores.getStoreImage3();
            if (stores.getStoreImage4() == null) objects[3] = getStoresRes.getStoreImage4();
            else objects[3] = stores.getStoreImage4();
            if(stores.getStoreName().equals("")){
                return new BaseResponse<>(PATCH_STORE_EMPTY_STORENAME);
            }
            if (stores.getStoreName() == null) objects[4] = getStoresRes.getStoreName();
            else objects[4] = stores.getStoreName();
            if ((Integer)stores.getMinimumOrderAmount() == null)
                objects[5] = getStoresRes.getMinimumOrderAmount();
            else objects[5] = stores.getMinimumOrderAmount();
            if (stores.getDeliveryTip() == null) objects[6] = getStoresRes.getDeliveryTip();
            else objects[6] = stores.getDeliveryTip();
            if (stores.getComment() == null) objects[7] = getStoresRes.getComment();
            else objects[7] = stores.getComment();
            if (stores.getTelephoneNumber() == null)
                objects[8] = getStoresRes.getTelephoneNumber();
            else objects[8] = stores.getTelephoneNumber();
            if(stores.getAddress().equals("")){
                return new BaseResponse<>(PATCH_STORE_EMPTY_ADDRESS);
            }
            if (stores.getAddress() == null) objects[9] = getStoresRes.getAddress();
            else objects[9] = stores.getAddress();
            if (stores.getStatus() == null) objects[10] = getStoresRes.getStatus();
            else objects[10] = stores.getStatus();

            PatchStoreReq patchStoreReq = new PatchStoreReq((String) objects[0], (String) objects[1], (String) objects[2], (String) objects[3], (String) objects[4], (Integer) objects[5], (String) objects[6], (String) objects[7], (String) objects[8], (String) objects[9], (String) objects[10]);
            storesService.modifyStoreInfo(patchStoreReq, storeId);

            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 메뉴 추가 API
     * [POST] /stores/{storeId}/storemenu
     * @return BaseResponse<PostStoreMenuRes>
     */
    @ResponseBody
    @PostMapping("/{storeId}/storemenu")
    public BaseResponse<PostStoreMenuRes> createStoreMenu(@PathVariable("storeId") int storeId, @RequestHeader("userId") int userId,  @RequestBody PostStoreMenuReq postStoreMenuReq) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(postStoreMenuReq.getMenuPrice() == 0){
                return new BaseResponse<>(POST_STORE_EMPTY_MENUPRICE);
            }
            if(postStoreMenuReq.getMenuName() == null) {
                return new BaseResponse<>(POST_STORE_EMPTY_STOREMENU);
            }
            PostStoreMenuRes postStoreMenuRes = storesService.createStoreMenu(postStoreMenuReq, storeId);
            return new BaseResponse<>(postStoreMenuRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 메뉴 수정 API
     * [PATCH] /stores/:storeId/storemenu/{storeMenuId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{storeId}/storemenu/{storeMenuId}")
    public BaseResponse<String> modifyUserInfo(@PathVariable("storeId") int storeId, @PathVariable("storeMenuId") int storeMenuId, @RequestHeader("userId") int userId, @RequestBody StoreMenu storeMenu){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 가게정보 변경
            GetStoreMenuRes getStoreMenuRes = storesProvider.getStoreMenuInfo(storeId, storeMenuId);
            Object[] objects = new Object[4];
            if (storeMenu.getMenuName() == null) objects[0] = getStoreMenuRes.getMenuName();
            else objects[0] = storeMenu.getMenuName();
            if (storeMenu.getMenuImage() == null) objects[1] = getStoreMenuRes.getMenuImage();
            else objects[1] = storeMenu.getMenuImage();
            /*if (String.valueOf(storeMenu.getMenuPrice()).equals("0")) objects[2] = getStoreMenuRes.getMenuPrice();
            else objects[2] = storeMenu.getMenuPrice();*/
            if (storeMenu.getMenuPrice() == 0) objects[2] = getStoreMenuRes.getMenuPrice();
            else objects[2] = storeMenu.getMenuPrice();
            if (storeMenu.getComment() == null) objects[3] = getStoreMenuRes.getComment();
            else objects[3] = storeMenu.getComment();
            for(int i=0; i<objects.length; i++){
                System.out.println("objects = " + objects[i]);
            }

            PatchStoreMenuReq patchStoreReq = new PatchStoreMenuReq((String) objects[0], (String) objects[1], (Integer) objects[2], (String) objects[3]);
            storesService.modifyStoreMenuInfo(patchStoreReq, storeId, storeMenuId);

            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 메뉴 삭제 API
     * [PATCH] /stores/:storeId/storemenu/:storemenuId/deletion
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{storeId}/storemenu/{storemenuId}/deletion")
    public BaseResponse<String> cancelOrder(@PathVariable("storeId") int storeId, @PathVariable("storemenuId") int storemenuId, @RequestHeader("userId") int userId){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            storesService.removeStoreMenu(storeId, storemenuId);
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가게 검색 API
     * [GET] /stores/search/:storename
     * @return BaseResponse<GetStoresRes>
     */
    @ResponseBody
    @GetMapping("/search") // (GET) 127.0.0.1:9000/stores/search/:storename
    public BaseResponse<List<com.example.demo.src.category.model.Stores>> getSearchStore(@RequestParam("keyword") String storename) {
        try{
            if(storename.equals("")){
                return new BaseResponse<>(GET_STORE_EMPTY_KEYWORD);
            }
            List<com.example.demo.src.category.model.Stores> getStores = storesProvider.getSearchStore(storename);
            return new BaseResponse<>(getStores);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
