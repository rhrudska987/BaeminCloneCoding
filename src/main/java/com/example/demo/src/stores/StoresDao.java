package com.example.demo.src.stores;

import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.orders.model.OrderMenu;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.stores.model.PostStoresReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoresDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createStore(PostStoresReq postStoresReq){
        String createStoreQuery = "insert into Store (storeName, telephoneNumber, address, comment, minimumOrderAmount, storeImage, storeImage1, storeImage2, storeImage3, storeImage4, deliveryTip) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createStoreParams = new Object[]{postStoresReq.getStoreName(), postStoresReq.getTelephoneNumber(), postStoresReq.getAddress(), postStoresReq.getComment(), postStoresReq.getMinimumOrderAmount(), postStoresReq.getStoreImage(), postStoresReq.getStoreImage1(), postStoresReq.getStoreImage2(), postStoresReq.getStoreImage3(), postStoresReq.getStoreImage4(), postStoresReq.getDeliveryTip()};
        this.jdbcTemplate.update(createStoreQuery, createStoreParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    /*public int cancelOrder(PatchOrderReq patchOrderReq){
        String cancelOrderQuery = "update Orders set cancelStatus = ? where userId = ? and orderId = ?";
        Object[] cancelOrderParams = new Object[]{patchOrderReq.getCancelStatus(), patchOrderReq.getUserId(), patchOrderReq.getOrderId()};
        return this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
    }

    public int checkCanceled(int orderId){
        String checkCanceledQuery = "select exists(select cancelStatus from Orders where cancelStatus = 'N' and orderId = ?)";
        int checkCanceledParams = orderId;
        return this.jdbcTemplate.queryForObject(checkCanceledQuery,
                int.class,
                checkCanceledParams
        );
    }*/

    /*public List<GetOrderRes> getOrderRes(int userId, int orderId){
        String getUserOrderMenuQuery = "select menuName, price from OrderedMenu where orderId = ?";
        List<OrderMenu> menuList = jdbcTemplate.query(getUserOrderMenuQuery,
                (rs, rowNum) -> new OrderMenu(
                  rs.getString("menuName"),
                        rs.getInt("price")),
                orderId);

        String getUserOrderQuery = "select orderStatus, storeName, O.createAt, O.orderNumber, orderPrice, deliveryTip, totalPrice, totalPrice, destinationAddress, commentStore, commentRider from Orders O inner join Delivery D on O.orderId = D.orderId where O.userId = ? and O.orderId = ?;";
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
    }*/
}
