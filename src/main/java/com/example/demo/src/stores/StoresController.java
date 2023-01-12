package com.example.demo.src.stores;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.OrdersService;
import com.example.demo.src.orders.model.*;
import com.example.demo.src.stores.model.PostStoresReq;
import com.example.demo.src.stores.model.PostStoresRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoresController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoresProvider storesProvider;
    @Autowired
    private final StoresService storesService;

    public StoresController(StoresProvider storesProvider, StoresService storesService){
        this.storesProvider = storesProvider;
        this.storesService = storesService;
    }

    /**
     * 유저 주문 생성 API
     * [PATCH] /users/:userId/orders
     * @return BaseResponse<PostOrderRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostStoresRes> createOrder(@RequestBody PostStoresReq postStoresReq) {
        try{
            PostStoresRes postStoresRes = storesService.createStore(postStoresReq);
            return new BaseResponse<>(postStoresRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문 취소 API
     * [PATCH] /users/:userId/orders/:orderId
     * @return BaseResponse<String>
     */
    /*@ResponseBody
    @PatchMapping("/{userId}/orders/{orderId}")
    public BaseResponse<String> cancelOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId, @RequestBody Order order){
        try{
            PatchOrderReq patchOrderReq = new PatchOrderReq(orderId, userId, order.getCancelStatus());
            ordersService.cancelOrder(patchOrderReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/

    /**
     * 특정 주문내역 조회 API
     * [GET] /users/:userId/orders/:orderId
     * @return BaseResponse<List<GetOrderRes>>
     */
    /*@ResponseBody
    @GetMapping("/{userId}/orders/{orderId}") // (GET) 127.0.0.1:9000/users/:userid/orders/:orderId
    public BaseResponse<List<GetOrderRes>> getUserOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) {
        try{
            List<GetOrderRes> getOrderRes = ordersProvider.getUserOrder(userId, orderId);
            return new BaseResponse<>(getOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/
}
