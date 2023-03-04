package com.example.demo.src.stores;

import com.example.demo.src.category.model.Stores;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.orders.model.OrderMenu;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.stores.model.*;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostAddressReq;
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

    public int removeStore(int storeId){
        String cancelOrderQuery = "delete from Store where storeId = ?";
        return this.jdbcTemplate.update(cancelOrderQuery, storeId);
    }

    public List<GetStoresRes> getStoresRes(int storeId){
        String getStoreMenuQuery = "select menuName, menuImage, price, comment from StoreMenu where storeId = ?";
        List<StoreMenu> menuList = jdbcTemplate.query(getStoreMenuQuery,
                (rs, rowNum) -> new StoreMenu(
                        rs.getString("menuName"),
                        rs.getString("menuImage"),
                        rs.getInt("price"),
                        rs.getString("comment")),
                storeId);

        String getStoreQuery = "select distinct storeImage1, storeImage2, storeImage3, storeImage4, storeName, totalStarPoint, totalReview, totalHostComment, totalHeart, minimumOrderAmount, deliveryTip, S.comment, telephoneNumber, address, S.status from Store S where S.storeId = ?;";
        return this.jdbcTemplate.query(getStoreQuery,
                (rs,rowNum) -> new GetStoresRes(
                        rs.getString("storeImage1"),
                        rs.getString("storeImage2"),
                        rs.getString("storeImage3"),
                        rs.getString("storeImage4"),
                        rs.getString("storeName"),
                        rs.getDouble("totalStarPoint"),
                        rs.getInt("totalReview"),
                        rs.getInt("totalHostComment"),
                        rs.getInt("totalHeart"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip"),
                        rs.getString("comment"),
                        rs.getString("telephoneNumber"),
                        rs.getString("address"),
                        rs.getString("status"),
                        menuList.toArray(new StoreMenu[menuList.size()])),
                storeId);
    }

    public List<Stores> getSearchStore(){
        String getSearchStoreQuery = "select storeImage, storeName, totalStarPoint, minimumOrderAmount, deliveryTip, status from Store;";
        return this.jdbcTemplate.query(getSearchStoreQuery,
                (rs, rowNum) -> new Stores(
                        rs.getString("storeImage"),
                        rs.getString("storeName"),
                        rs.getDouble("totalStarPoint"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip"),
                        rs.getString("status"))
                );
    }

    public GetStoresRes getStoresInfo(int storeId){
        String getStoreQuery = "select distinct storeImage1, storeImage2, storeImage3, storeImage4, storeName, minimumOrderAmount, deliveryTip, comment, telephoneNumber, address, status from Store where storeId = ?;";
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs,rowNum) -> new GetStoresRes(
                        rs.getString("storeImage1"),
                        rs.getString("storeImage2"),
                        rs.getString("storeImage3"),
                        rs.getString("storeImage4"),
                        rs.getString("storeName"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip"),
                        rs.getString("comment"),
                        rs.getString("telephoneNumber"),
                        rs.getString("address"),
                        rs.getString("status")),
                storeId);
    }

    public GetStoreMenuRes getStoreMenuInfo(int storeId, int storeMenuId){
        String getStoreMenuQuery = "select distinct menuName, menuImage, price, comment from StoreMenu where storeId = ? and menuId = ?;";
        Object[] getStoreMenuParam = new Object[]{storeId, storeMenuId};
        return this.jdbcTemplate.queryForObject(getStoreMenuQuery,
                (rs,rowNum) -> new GetStoreMenuRes(
                        rs.getString("menuName"),
                        rs.getString("menuImage"),
                        rs.getInt("price"),
                        rs.getString("comment")),
                getStoreMenuParam);
    }

    public int modifyStoreInfo(PatchStoreReq patchStoreReq, int storeId){
        String modifyStoreInfoQuery = "update Store set storeImage1 = ?, storeImage2 = ?, storeImage3 = ?, storeImage4 = ?, storeName = ?, minimumOrderAmount = ?, deliveryTip = ?, comment = ?, telephoneNumber = ?, address = ?, status = ? where storeId = ?";
        Object[] modifyStoreInfoParams = new Object[]{patchStoreReq.getStoreImage1(), patchStoreReq.getStoreImage2(), patchStoreReq.getStoreImage3(), patchStoreReq.getStoreImage4(), patchStoreReq.getStoreName(), patchStoreReq.getMinimumOrderAmount(), patchStoreReq.getDeliveryTip(), patchStoreReq.getComment(), patchStoreReq.getTelephoneNumber(), patchStoreReq.getAddress(), patchStoreReq.getStatus(), storeId};
        return this.jdbcTemplate.update(modifyStoreInfoQuery, modifyStoreInfoParams);
    }

    public int modifyStoreMenuInfo(PatchStoreMenuReq patchStoreMenuReq, int storeId, int storeMenuId){
        String modifyStoreMenuInfoQuery = "update StoreMenu set menuName = ?, menuImage = ?, price = ?, comment = ? where storeId = ? and menuId = ?";
        Object[] modifyStoreMenuInfoParams = new Object[]{patchStoreMenuReq.getMenuName(), patchStoreMenuReq.getMenuImage(), patchStoreMenuReq.getMenuPrice(), patchStoreMenuReq.getComment(), storeId, storeMenuId};
        return this.jdbcTemplate.update(modifyStoreMenuInfoQuery, modifyStoreMenuInfoParams);
    }

    public int createStoreMenu(PostStoreMenuReq postStoreMenuReq, int storeId){
        String createStoreMenuQuery = "insert into StoreMenu (menuName, menuImage, price, comment, storeId) VALUES (?,?,?,?,?)";
        Object[] createStoreMenuParams = new Object[]{postStoreMenuReq.getMenuName(), postStoreMenuReq.getMenuImage(), postStoreMenuReq.getMenuPrice(), postStoreMenuReq.getComment(), storeId};
        this.jdbcTemplate.update(createStoreMenuQuery, createStoreMenuParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int removeStoreMenu(int storeId, int storemenuId){
        String removeStoreMenuQuery = "update StoreMenu set status = 'N' where storeId = ? and menuId = ?";
        Object[] removeStoreMenuParams = new Object[]{storeId, storemenuId};
        return this.jdbcTemplate.update(removeStoreMenuQuery, removeStoreMenuParams);
    }
}
