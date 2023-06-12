package baemin.baeminjpa.repository;

import baemin.baeminjpa.domain.MemberReview;
import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public GetUserRes findUserInfo(long userId){
        User user =  em.find(User.class, userId);
        GetUserRes getUserRes = new GetUserRes(user.getUserName(), user.getProfileImage(), user.getGrade(), user.getPhoneNumber(), user.getStatus(), user.getEmail(), user.getMailReceive(), user.getSMSReceive());
        return getUserRes;
    }

    public MemberReview findMemberReview(long reviewId){
        MemberReview memberReview = em.find(MemberReview.class, reviewId);
        System.out.println(memberReview.getMember().getId());
        System.out.println(memberReview.getMember().getName());
        return memberReview;
    }

    public void deleteUser(long userId) {
        User user = em.find(User.class, userId);
        em.remove(user);
    }
}
