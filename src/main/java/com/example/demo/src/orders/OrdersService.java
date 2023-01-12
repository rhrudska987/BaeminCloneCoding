package com.example.demo.src.orders;

import com.example.demo.config.BaseException;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.orders.model.PostOrderRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostAddressReq;
import com.example.demo.src.user.model.PostAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class OrdersService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrdersDao ordersDao;
    private final OrdersProvider ordersProvider;

    @Autowired
    OrdersService(OrdersDao ordersDao, OrdersProvider ordersProvider){
        this.ordersDao = ordersDao;
        this.ordersProvider = ordersProvider;
    }

    //POST
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException {
        try{
            int orderId = ordersDao.createOrder(postOrderReq);
            return new PostOrderRes(orderId);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelOrder(PatchOrderReq patchOrderReq) throws BaseException {
        if(ordersProvider.checkCanceled(patchOrderReq.getOrderId()) == 1){
            throw new BaseException(CANCELED_ORDER);
        }
        try{
            int result = ordersDao.cancelOrder(patchOrderReq);
            if(result == 0){
                throw new BaseException(CANCEL_FAIL_ORDER);
            }
        } catch(Exception exception){
            logger.error("App - leaveUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
