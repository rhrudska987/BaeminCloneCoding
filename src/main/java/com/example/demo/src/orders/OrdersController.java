package com.example.demo.src.orders;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orders.model.*;
import com.example.demo.src.user.model.GetAddressRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.CANCELED_ORDER;
import static com.example.demo.config.BaseResponseStatus.LEAVE_USER;

@RestController
@RequestMapping("/users")
public class OrdersController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrdersProvider ordersProvider;
    @Autowired
    private final OrdersService ordersService;

    public OrdersController(OrdersProvider ordersProvider, OrdersService ordersService){
        this.ordersProvider = ordersProvider;
        this.ordersService = ordersService;
    }

    /**
     * 유저 주문 생성 API
     * [PATCH] /users/:userId/orders
     * @return BaseResponse<PostOrderRes>
     */
    @ResponseBody
    @PostMapping("/{userId}/orders")
    public BaseResponse<PostOrderRes> createOrder(@PathVariable("userId") int userId, @RequestBody PostOrderReq postOrderReq) {
        try{
            postOrderReq.setUserId(userId);
            PostOrderRes postOrderRes = ordersService.createOrder(postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주문 취소 API
     * [PATCH] /users/:userId/orders/:orderId
     * @return BaseResponse<String>
     */
    @ResponseBody
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
    }

    /**
     * 특정 주문내역 조회 API
     * [GET] /users/:userId/orders/:orderId
     * @return BaseResponse<List<GetOrderRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}/orders/{orderId}") // (GET) 127.0.0.1:9000/users/:userid/orders/:orderId
    public BaseResponse<List<GetOrderRes>> getUserOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId) {
        try{
            List<GetOrderRes> getOrderRes = ordersProvider.getUserOrder(userId, orderId);
            return new BaseResponse<>(getOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
