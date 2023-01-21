package com.example.demo.src.orders;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orders.model.*;
import com.example.demo.src.user.model.GetAddressRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/users")
public class OrdersController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrdersProvider ordersProvider;
    @Autowired
    private final OrdersService ordersService;
    @Autowired
    private final JwtService jwtService;

    public OrdersController(OrdersProvider ordersProvider, OrdersService ordersService, JwtService jwtService){
        this.ordersProvider = ordersProvider;
        this.ordersService = ordersService;
        this.jwtService = jwtService;
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
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
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
    public BaseResponse<String> cancelOrder(@PathVariable("userId") int userId, @PathVariable("orderId") int orderId){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchOrderReq patchOrderReq = new PatchOrderReq(orderId, userId);
            ordersService.cancelOrder(patchOrderReq);
            return new BaseResponse<>();
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
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetOrderRes> getOrderRes = ordersProvider.getUserOrder(userId, orderId);
            return new BaseResponse<>(getOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
