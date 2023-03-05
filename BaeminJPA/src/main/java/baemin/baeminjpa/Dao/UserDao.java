package baemin.baeminjpa.Dao;

import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import baemin.baeminjpa.dto.PostUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public GetUserRes findUserInfo(int userId){
        User user =  em.find(User.class, userId);
        GetUserRes getUserRes = new GetUserRes(user.getUserName(), user.getProfileImage(), user.getGrade(), user.getPhoneNumber(), user.getStatus(), user.getEmail(), user.getMailReceive(), user.getSMSReceive());
        return getUserRes;
    }

}
