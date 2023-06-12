package baemin.baeminjpa.repository;

import baemin.baeminjpa.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional

class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Test
    public void findByNameTest() throws Exception{
        Member member1 = new Member("영희", "010-1111-1111");
        memberRepository.save(member1);

        Thread.sleep(1000);
        member1.setName("철수");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member1.getId()).get();

        System.out.println("findMember.getCreateAt() = " + findMember.getCreateAt());
        System.out.println("findMember.getUpdateAt() = " + findMember.getUpdateAt());
    }
}