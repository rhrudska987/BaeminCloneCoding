package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.model.GetCategoryRes;
import com.example.demo.src.category.model.GetCategoryStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class CategoryProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryProvider(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    public List<GetCategoryRes> getCategoryRes() throws BaseException {
        try{
            List<GetCategoryRes> getCategoryRes = categoryDao.getCategoryRes();
            return getCategoryRes;
        } catch (Exception exception) {
            logger.error("App - getStores Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryStoreRes> getCategoryStoreRes(int categoryId) throws BaseException {
        try{
            List<GetCategoryStoreRes> getCategoryStoreRes = categoryDao.getCategoryStoreRes(categoryId);
            return getCategoryStoreRes;
        } catch (Exception exception) {
            logger.error("App - getCategoryStore Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryStoreRes> getCategoryStoreSortRes(int categoryId, String sortType) throws BaseException {
        try{
            List<GetCategoryStoreRes> getCategoryStoreRes = categoryDao.getCategoryStoreSortRes(categoryId, sortType);
            return getCategoryStoreRes;
        } catch (Exception exception) {
            logger.error("App - getCategoryStoreSort Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
