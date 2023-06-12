package baemin.baeminjpa.repository;

import baemin.baeminjpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);

    List<Member> findByNameAndPhoneNumber(String name, String phoneNumber);

    @Query("select m from Member m where m.name = :name and m.phoneNumber = :phoneNumber")
    List<Member> findMember(@Param("name") String name, @Param("phoneNumber") String phoneNumber);
}
