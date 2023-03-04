package com.example.demo.src.stores;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.model.Stores;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.stores.model.GetStoreMenuRes;
import com.example.demo.src.stores.model.GetStoresRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAILED_TO_SEARCH;

@Service
@Transactional(readOnly = true)
public class StoresProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoresDao storesDao;

    @Autowired
    public StoresProvider(StoresDao storesDao){
        this.storesDao = storesDao;
    }

    public List<GetStoresRes> getStoresRes(int storeId) throws BaseException {
        try{
            List<GetStoresRes> getStoresRes = storesDao.getStoresRes(storeId);
            for(int i=0; i<getStoresRes.size(); i++) {
                if (getStoresRes.get(i).getStatus().equals("O"))
                    getStoresRes.get(i).setStatus("OPEN");
                else
                    getStoresRes.get(i).setStatus("CLOSE");
            }

            return getStoresRes;
        } catch (Exception exception) {
            logger.error("App - getStores Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetStoresRes getStoresInfo(int storeId) throws BaseException {
        try{
            GetStoresRes getStoresRes = storesDao.getStoresInfo(storeId);
            return getStoresRes;
        } catch (Exception exception) {
            logger.error("App - getStores Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetStoreMenuRes getStoreMenuInfo(int storeId, int storeMenuId) throws BaseException {
        try{
            GetStoreMenuRes getStoreMenuRes = storesDao.getStoreMenuInfo(storeId, storeMenuId);
            return getStoreMenuRes;
        } catch (Exception exception) {
            logger.error("App - getStoresMenu Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Stores> getSearchStore(String keyword) throws BaseException {
        try{
            List<Stores> stores = storesDao.getSearchStore();
            List<Stores> searchedStores = new ArrayList<>();
            for(int i=0; i<stores.size(); i++){
                if(stores.get(i).getStoreName().contains(keyword))
                    searchedStores.add(stores.get(i));
            }
            if(searchedStores.size() == 0){
                throw new BaseException(FAILED_TO_SEARCH);
            }
            for(int i=0; i<searchedStores.size(); i++) {
                if (stores.get(i).getStatus().equals("O"))
                    stores.get(i).setStatus("OPEN");
                else
                    stores.get(i).setStatus("CLOSE");
            }
            return searchedStores;
        } catch (Exception exception) {
            logger.error("App - getSearchStore Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
