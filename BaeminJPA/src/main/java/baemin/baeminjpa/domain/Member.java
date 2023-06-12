package baemin.baeminjpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "memberId")
    private Long id;
    private String name;
    private String phoneNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberReview> review = new ArrayList<>();

    public Member(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
