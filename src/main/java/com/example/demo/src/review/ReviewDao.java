package com.example.demo.src.review;

import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.orders.model.OrderMenu;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createReview(PostReviewReq postReviewReq){
        String createReviewQuery = "insert into Review (userId, storeId, starPoint, menuName, comment, reviewImage1, reviewImage2, reviewImage3, reviewImage4) VALUES (?,?,?,?,?,?,?,?,?);";
        Object[] createReviewParams = new Object[]{postReviewReq.getUserId(), postReviewReq.getStoreId(), postReviewReq.getStarPoint(), postReviewReq.getMenuName(), postReviewReq.getComment(), postReviewReq.getReviewImage1(), postReviewReq.getReviewImage2(), postReviewReq.getReviewImage3(), postReviewReq.getReviewImage4()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int cancelReview(PatchReviewReq patchReviewReq){
        String cancelReviewQuery = "update Review set status = 'N' where reviewId = ? and userId = ?";
        Object[] cancelReviewParams = new Object[]{patchReviewReq.getReviewId(), patchReviewReq.getUserId()};
        return this.jdbcTemplate.update(cancelReviewQuery, cancelReviewParams);
    }

    public List<GetUserReviewRes> getUserReviewRes(int userId){
        String getUserReviewQuery = "select S.storeName, R.starPoint, R.createAt, R.reviewImage1, R.reviewImage2, R.reviewImage3, R.reviewImage4, R.comment, R.hostComment, R.menuName from User U inner join Review R on U.userId = R.userId inner join Store S on R.storeId = S.storeId where U.userId = ?;";
        List<Review> reviewList = jdbcTemplate.query(getUserReviewQuery,
                (rs, rowNum) -> new Review(
                        rs.getString("storeName"),
                        rs.getDouble("starPoint"),
                        rs.getString("createAt"),
                        rs.getString("reviewImage1"),
                        rs.getString("reviewImage2"),
                        rs.getString("reviewImage3"),
                        rs.getString("reviewImage4"),
                        rs.getString("comment"),
                        rs.getString("menuName"),
                        rs.getString("hostComment")),
                userId);

        for(int i=0; i<reviewList.size(); i++){
            Timestamp timestamp = Timestamp.valueOf(reviewList.get(i).getCreateAt());
            LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
            LocalDate currentTime = LocalDate.now();
            Period period = Period.between(localDate, LocalDate.now());
            if(period.getYears() >= 1)
                reviewList.get(i).setCreateAt("작년");
            else if(period.getMonths() >= 1)
                reviewList.get(i).setCreateAt("한달 전");
            else if(period.getDays() >= 7)
                reviewList.get(i).setCreateAt("한주 전");
            else if(period.getDays() >= 2)
                reviewList.get(i).setCreateAt("그제");
            else if(period.getDays() >= 1)
                reviewList.get(i).setCreateAt("어제");
            else
                reviewList.get(i).setCreateAt("오늘");
        }

        String getUserQuery = "select profileImage, grade, userName, totalReview, avgReview, 5point, 4point, 3point, 2point, 1point from User where userId = ?;";
        return this.jdbcTemplate.query(getUserQuery,
                (rs,rowNum) -> new GetUserReviewRes(
                        rs.getString("profileImage"),
                        rs.getString("grade"),
                        rs.getString("userName"),
                        rs.getInt("totalReview"),
                        rs.getDouble("avgReview"),
                        rs.getInt("5Point"),
                        rs.getInt("4Point"),
                        rs.getInt("3Point"),
                        rs.getInt("2Point"),
                        rs.getInt("1Point"),
                        reviewList.toArray(new Review[reviewList.size()])),
                userId);
    }
}
