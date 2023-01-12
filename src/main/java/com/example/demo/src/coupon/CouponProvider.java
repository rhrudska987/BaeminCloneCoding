package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.heart.HeartDao;
import com.example.demo.src.heart.model.GetHeartRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CouponProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CouponDao couponDao;

    @Autowired
    public CouponProvider(CouponDao couponDao){
        this.couponDao = couponDao;
    }

    public List<GetCouponRes> getCoupon(int userId, String status) throws BaseException {
        try{
            List<GetCouponRes> getCouponRes = couponDao.getCouponRes(userId, status);
            for(int i=0; i<getCouponRes.size(); i++) {
                Timestamp timestampCreated = Timestamp.valueOf(getCouponRes.get(i).getCreateAt());
                Timestamp timestampExpired = Timestamp.valueOf(getCouponRes.get(i).getExpiredAt());
                LocalDateTime localDateTimeCreated = timestampCreated.toLocalDateTime();
                LocalDateTime localDateTimeExpired = timestampExpired.toLocalDateTime();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                String nowStringCreated = localDateTimeCreated.format(dateTimeFormatter);
                String nowStringExpired = localDateTimeExpired.format(dateTimeFormatter);
                getCouponRes.get(i).setCreateAt(nowStringCreated);
                getCouponRes.get(i).setExpiredAt(nowStringExpired);
            }
            return getCouponRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
