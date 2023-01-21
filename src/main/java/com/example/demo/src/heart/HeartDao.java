package com.example.demo.src.heart;

import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.heart.model.Heart;
import com.example.demo.src.heart.model.PatchHeartReq;
import com.example.demo.src.heart.model.PostHeartReq;
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

    public int checkHeart(int userId, int storeId){
        String checkHeartQuery = "select exists(select status from Heart where userId = ? and storeId = ?);";
        return this.jdbcTemplate.queryForObject(checkHeartQuery,
        int.class,
        userId, storeId);
    }

    public int createHeart(int userId, int storeId){
        String createHeartQuery = "insert into Heart (userId, storeId) VALUES (?,?)";
        Object[] createHeartParams = new Object[]{userId, storeId};
        this.jdbcTemplate.update(createHeartQuery, createHeartParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public int doHeart(int userId, int storeId){
        String cancelHeartQuery = "update Heart set status = 'N' where storeId = ? and userId = ?";
        Object[] cancelHeartParams = new Object[]{storeId, userId};
        return this.jdbcTemplate.update(cancelHeartQuery, cancelHeartParams);
    }

    public int cancelHeart(int userId, int storeId){
        String cancelHeartQuery = "update Heart set status = 'Y' where storeId = ? and userId = ?";
        Object[] cancelHeartParams = new Object[]{storeId, userId};
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

    public GetHeartRes getHeart(int userId, int storeId){
        String getHeartResQuery = "select status from Heart where userId = ? and storeId = ?;";
        return this.jdbcTemplate.queryForObject(getHeartResQuery,
                (rs, rowNum) -> new GetHeartRes(
                        rs.getString("status")),
                userId, storeId);
    }
}
