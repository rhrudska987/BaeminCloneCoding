package com.example.demo.src.stores;

import com.example.demo.config.BaseException;
import com.example.demo.src.stores.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class StoresService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoresDao storesDao;
    private final StoresProvider storesProvider;

    @Autowired
    StoresService(StoresDao storesDao, StoresProvider storesProvider){
        this.storesDao = storesDao;
        this.storesProvider = storesProvider;
    }

    //POST
    public PostStoresRes createStore(PostStoresReq postStoresReq) throws BaseException {
        try{
            int storeId = storesDao.createStore(postStoresReq);
            return new PostStoresRes(storeId);
        } catch (Exception exception) {
            logger.error("App - createStore Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyStoreInfo(PatchStoreReq patchStoreReq, int storeId) throws BaseException {
        try {
            int result = storesDao.modifyStoreInfo(patchStoreReq, storeId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STOREINFO);
            }
        } catch(Exception exception){
            logger.error("App - modifyStoreInfo Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void removeStore(int storeId) throws BaseException {
        try{
            int result = storesDao.removeStore(storeId);
            if(result == 0){
                throw new BaseException(CANCEL_FAIL_STORE);
            }
        } catch(Exception exception){
            logger.error("App - leaveUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    public PostStoreMenuRes createStoreMenu(PostStoreMenuReq postStoreMenuReq, int storeId) throws BaseException {
        try{
            int storeMenuId = storesDao.createStoreMenu(postStoreMenuReq, storeId);
            return new PostStoreMenuRes(storeMenuId);
        } catch (Exception exception) {
            logger.error("App - createStoreMenu Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyStoreMenuInfo(PatchStoreMenuReq patchStoreMenuReq, int storeId, int storeMenuId) throws BaseException {
        try {
            int result = storesDao.modifyStoreMenuInfo(patchStoreMenuReq, storeId, storeMenuId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_STOREMENUINFO);
            }
        } catch(Exception exception){
            logger.error("App - modifyStoreMenuInfo Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void removeStoreMenu(int storeId, int storemenuId) throws BaseException {
        try{
            int result = storesDao.removeStoreMenu(storeId, storemenuId);
            if(result == 0){
                throw new BaseException(REMOVE_FAIL_STOREMENU);
            }
        } catch(Exception exception){
            logger.error("App - cancelReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
