package com.example.demo.src.category;

import com.example.demo.src.category.model.GetCategoryRes;
import com.example.demo.src.category.model.GetCategoryStoreRes;
import com.example.demo.src.category.model.Stores;
import com.example.demo.src.stores.model.GetStoresRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCategoryRes> getCategoryRes(){
        String getCategoryQuery = "select distinct categoryName, categoryImage from StoreCategory;";
        return this.jdbcTemplate.query(getCategoryQuery,
                (rs,rowNum) -> new GetCategoryRes(
                        rs.getString("categoryName"),
                        rs.getString("categoryImage")));
    }

    public List<GetCategoryStoreRes> getCategoryStoreRes(int categoryId){
        String getCategoryStoreQuery = "select storeImage, storeName, totalStarPoint, minimumOrderAmount, deliveryTip from Store inner join StoreCategory_Store SCS on Store.storeId = SCS.storeId inner join StoreCategory SC on SCS.categoryID = SC.categoryId where SC.categoryId = ?;";
        List<com.example.demo.src.category.model.Stores> storeList = jdbcTemplate.query(getCategoryStoreQuery,
                (rs, rowNum) -> new com.example.demo.src.category.model.Stores(
                        rs.getString("storeImage"),
                        rs.getString("storeName"),
                        rs.getDouble("totalStarPoint"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip")),
                categoryId);

        String getStoreQuery = "select categoryName from StoreCategory where categoryId = ?;";
        return this.jdbcTemplate.query(getStoreQuery,
                (rs,rowNum) -> new GetCategoryStoreRes(
                        rs.getString("categoryName"),
                        storeList.toArray(new Stores[storeList.size()])), categoryId);
    }

    public List<GetCategoryStoreRes> getCategoryStoreSortRes(int categoryId, String sortType){
        String getCategoryStoreQuery = "select storeImage, storeName, totalStarPoint, minimumOrderAmount, deliveryTip from Store inner join StoreCategory_Store SCS on Store.storeId = SCS.storeId inner join StoreCategory SC on SCS.categoryID = SC.categoryId where SC.categoryId = ? order by ? desc;";
        Object getCategoryStoreParam[] = new Object[]{categoryId, sortType};
        List<Stores> storeList = jdbcTemplate.query(getCategoryStoreQuery,
                (rs, rowNum) -> new com.example.demo.src.category.model.Stores(
                        rs.getString("storeImage"),
                        rs.getString("storeName"),
                        rs.getDouble("totalStarPoint"),
                        rs.getInt("minimumOrderAmount"),
                        rs.getString("deliveryTip")),
                getCategoryStoreParam);
        for(int i=1; i<storeList.size(); i++){
            Stores targetStore = storeList.get(i);
            double target = storeList.get(i).getTotalStarPoint();
            int j = i - 1;
            while(j >= 0 && target > storeList.get(j).getTotalStarPoint()){
                storeList.set(j + 1, storeList.get(j));
                j--;
            }
            storeList.set(j + 1, targetStore);
        }

        String getStoreQuery = "select categoryName from StoreCategory where categoryId = ?;";
        return this.jdbcTemplate.query(getStoreQuery,
                (rs,rowNum) -> new GetCategoryStoreRes(
                        rs.getString("categoryName"),
                        storeList.toArray(new Stores[storeList.size()])), categoryId);
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

}
