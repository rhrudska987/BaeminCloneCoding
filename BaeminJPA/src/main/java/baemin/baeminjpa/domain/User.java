package baemin.baeminjpa.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/*@DynamicInsert*/
public class User extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "userId")
    private Long id;

    private String userName;
    private String profileImage;

    /*@Column(columnDefinition = "varchar(40) default '고마운분'")*/
    /*@ColumnDefault("'고마운분'")*/
    private String grade;
    private double point;

    /*@Column(columnDefinition = "varchar(32) default 'JOIN'")*/
    /*@ColumnDefault("'JOIN'")*/
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String phoneNumber;
    private String email;
    private String loginId;
    private String password;
    private int cntReview;
    private double avgReview;
    private int cnt5Point;
    private int cnt4Point;
    private int cnt3Point;
    private int cnt2Point;
    private int cnt1Point;

    @Enumerated(EnumType.STRING)
    private ReceiveStatus mailReceive;

    @Enumerated(EnumType.STRING)
    private ReceiveStatus SMSReceive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notice> noticeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<User_Coupon> user_couponList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Heart> heartList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    public User(String email, String userName, String loginId, String password, ReceiveStatus mailReceive, String phoneNumber, ReceiveStatus smsReceive) {
        this.email = email;
        this.userName = userName;
        this.loginId = loginId;
        this.password = password;
        this.mailReceive = mailReceive;
        this.phoneNumber = phoneNumber;
        this.SMSReceive = smsReceive;
    }

    @PrePersist
    public void prePersist() {
        this.grade = this.grade == null ? "고마운분":this.grade;
        this.status = this.status == null ? UserStatus.JOIN:this.status;
    }

    /*@PostPersist
    public void postPersist(){
        this.grade = this.grade == null ? "고마운분":this.grade;
        this.status = this.status == null ? UserStatus.JOIN:this.status;
    }*/
}
