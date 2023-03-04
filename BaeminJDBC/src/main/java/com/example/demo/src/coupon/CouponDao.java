package com.example.demo.src.coupon;

import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.heart.model.PatchHeartReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CouponDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCouponRes> getCouponRes(int userId){
        String getCouponQuery = "select distinct couponPrice, couponName, minimumOrderAmount, Coupon.createAt, expiredAt, couponImage from Coupon inner join User_Coupon join User_Coupon UC on Coupon.couponId = UC.couponId inner join User U on User_Coupon.userId = U.userId where U.userId = ? and Coupon.status = ?;";
        Object[] getCouponParams = new Object[]{userId};
        return this.jdbcTemplate.query(getCouponQuery,
                (rs,rowNum) -> new GetCouponRes(
                        rs.getInt("couponPrice"),
                        rs.getString("couponName"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("createAt"),
                        rs.getString("expiredAt"),
                        rs.getString("couponImage")),
                getCouponParams);
    }
}
