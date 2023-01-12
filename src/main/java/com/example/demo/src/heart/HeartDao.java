package com.example.demo.src.heart;

import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.heart.model.PatchHeartReq;
import com.example.demo.src.heart.model.PostHeartReq;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.orders.model.OrderMenu;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HeartDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createHeart(int userId, int storeId){
        String createHeartQuery = "insert into Heart (userId, storeId) VALUES (?,?)";
        Object[] createHeartParams = new Object[]{userId, storeId};
        this.jdbcTemplate.update(createHeartQuery, createHeartParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int cancelHeart(PatchHeartReq patchHeartReq){
        String cancelHeartQuery = "update Heart set status = ? where heartId = ? and userId = ?";
        Object[] cancelHeartParams = new Object[]{patchHeartReq.getStatus(), patchHeartReq.getHeartId(), patchHeartReq.getUserId()};
        return this.jdbcTemplate.update(cancelHeartQuery, cancelHeartParams);
    }

    public List<GetHeartRes> getHeartRes(int userId, String status){
        String getHeartQuery = "select storeImage, storeName, totalStarPoint, minimumOrderAmount, deliveryTip, S.status from Heart inner join Store S on Heart.storeId = S.storeId inner join User U on Heart.userId = U.userId where U.userId = ? and Heart.status = ?;";
        Object[] getHeartParams = new Object[]{userId, status};
        return this.jdbcTemplate.query(getHeartQuery,
                (rs,rowNum) -> new GetHeartRes(
                        rs.getString("storeImage"),
                        rs.getString("storeName"),
                        rs.getDouble("totalStarPoint"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip"),
                        rs.getString("status")),
                getHeartParams);
    }
}
