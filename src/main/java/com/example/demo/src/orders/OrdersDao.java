package com.example.demo.src.orders;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.orders.model.OrderMenu;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.user.model.GetAddressRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostAddressReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.CANCELED_ORDER;

@Repository
public class OrdersDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createOrder(PostOrderReq postOrderReq){
        String createOrderQuery = "insert into Orders (userId, storeName, orderPrice, deliveryTip, totalPrice, receipt, commentStore, commentRider, orderNumber) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] createOrderParams = new Object[]{postOrderReq.getUserId(), postOrderReq.getStoreName(), postOrderReq.getOrderPrice(), postOrderReq.getDeliveryTip(), postOrderReq.getOrderPrice(), postOrderReq.getReceipt(), postOrderReq.getCommentStore(), postOrderReq.getCommentRider(), postOrderReq.getOrderNumber()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int cancelOrder(PatchOrderReq patchOrderReq){
        String cancelOrderQuery = "update Orders set cancelStatus = 'N' where userId = ? and orderId = ?";
        Object[] cancelOrderParams = new Object[]{patchOrderReq.getUserId(), patchOrderReq.getOrderId()};
        return this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
    }

    public int checkCanceled(int orderId){
        String checkCanceledQuery = "select exists(select cancelStatus from Orders where cancelStatus = 'N' and orderId = ?)";
        int checkCanceledParams = orderId;
        return this.jdbcTemplate.queryForObject(checkCanceledQuery,
                int.class,
                checkCanceledParams
        );
    }

    public List<GetOrderRes> getOrderRes(int userId, int orderId){
        String getUserOrderMenuQuery = "select menuName, price from OrderedMenu where orderId = ?";
        List<OrderMenu> menuList = jdbcTemplate.query(getUserOrderMenuQuery,
                (rs, rowNum) -> new OrderMenu(
                  rs.getString("menuName"),
                        rs.getInt("price")),
                orderId);

        String getUserOrderQuery = "select distinct orderStatus, storeName, O.createAt, O.orderNumber, orderPrice, deliveryTip, totalPrice, totalPrice, destinationAddress, commentStore, commentRider from Orders O inner join Delivery D on O.orderId = D.orderId where O.userId = ? and O.orderId = ?;";
        Object[] getUserOrderParams = new Object[]{userId, orderId};
        return this.jdbcTemplate.query(getUserOrderQuery,
                (rs,rowNum) -> new GetOrderRes(
                        rs.getString("orderStatus"),
                        rs.getString("storeName"),
                        rs.getString("createAt"),
                        rs.getString("orderNumber"),
                        rs.getInt("orderPrice"),
                        rs.getInt("deliveryTip"),
                        rs.getInt("totalPrice"),
                        rs.getString("destinationAddress"),
                        rs.getString("commentStore"),
                        rs.getString("commentRider"),
                        menuList.toArray(new OrderMenu[menuList.size()])),
                userId, orderId);
    }
}
