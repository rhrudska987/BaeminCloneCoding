package baemin.baeminjpa.service;

import baemin.baeminjpa.Dao.UserDao;
import baemin.baeminjpa.config.BaseException;
import baemin.baeminjpa.config.BaseResponseStatus;
import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import baemin.baeminjpa.dto.PostUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static baemin.baeminjpa.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void createUser(User user) {
        userDao.save(user);
        System.out.println("=================================");
        //System.out.println("userId = " + user.getId());
    }

    @Transactional(readOnly = true)
    public GetUserRes findUserInfo(int userId){
        GetUserRes getUserRes = userDao.findUserInfo(userId);
        return getUserRes;
    }
}
