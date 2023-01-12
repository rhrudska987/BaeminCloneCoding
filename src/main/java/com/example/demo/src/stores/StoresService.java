package com.example.demo.src.stores;

import com.example.demo.config.BaseException;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.orders.model.PostOrderRes;
import com.example.demo.src.stores.model.PostStoresReq;
import com.example.demo.src.stores.model.PostStoresRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class StoresService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoresDao storesDao;
    private final StoresProvider storesProvider;

    @Autowired
    StoresService(StoresDao storesDao, StoresProvider storesProvider){
        this.storesDao = storesDao;
        this.storesProvider = storesProvider;
    }

    //POST
    public PostStoresRes createStore(PostStoresReq postStoresReq) throws BaseException {
        try{
            int storeId = storesDao.createStore(postStoresReq);
            return new PostStoresRes(storeId);
        } catch (Exception exception) {
            logger.error("App - createStore Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*public void cancelOrder(PatchOrderReq patchOrderReq) throws BaseException {
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
    }*/
}
