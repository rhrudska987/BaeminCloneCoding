package baemin.baeminjpa.service;

import baemin.baeminjpa.repository.UserRepository;
import baemin.baeminjpa.domain.MemberReview;
import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import baemin.baeminjpa.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository2 userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public GetUserRes findUserInfo(Long userId){
        User user = userRepository.findById(userId).get();
        GetUserRes getUserRes = new GetUserRes(user.getUserName(), user.getProfileImage(), user.getGrade(), user.getPhoneNumber(), user.getStatus(), user.getEmail(), user.getMailReceive(), user.getSMSReceive());
        return getUserRes;
    }

//    @Transactional(readOnly = true)
//    public MemberReview findMemberReview(long userId){
//        return userRepository.findMemberReview(userId);
//    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
    }
}
