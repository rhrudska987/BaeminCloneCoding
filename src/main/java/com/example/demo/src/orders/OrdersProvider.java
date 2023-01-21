package com.example.demo.src.orders;

import com.example.demo.config.BaseException;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.user.model.GetAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class OrdersProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrdersDao ordersDao;

    @Autowired
    public OrdersProvider(OrdersDao ordersDao){
        this.ordersDao = ordersDao;
    }

    public int checkCanceled(int orderId) throws BaseException {
        try{
            return ordersDao.checkCanceled(orderId);
        } catch (Exception exception){
            logger.error("App - checkCanceledOrder Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetOrderRes> getUserOrder(int userId, int orderId) throws BaseException {
        try{
            List<GetOrderRes> getOrderRes = ordersDao.getOrderRes(userId, orderId);
            for(int i=0; i<getOrderRes.size(); i++){
                Timestamp timestamp = Timestamp.valueOf(getOrderRes.get(i).getCreateAt());
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a h:m");
                String nowString = localDateTime.format(dateTimeFormatter);
                getOrderRes.get(i).setCreateAt(nowString);
                if(getOrderRes.get(i).getOrderStatus().equals("Y"))
                    getOrderRes.get(i).setOrderStatus("배달이 완료되었어요");
                else
                    getOrderRes.get(i).setOrderStatus("주문이 취소되었어요");
            }
            return getOrderRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
