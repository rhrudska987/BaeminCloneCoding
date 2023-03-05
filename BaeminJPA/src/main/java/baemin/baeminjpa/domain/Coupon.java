package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Coupon extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "couponId")
    private Long id;

    private String couponCode;
    private String couponName;
    private int price;
    private int minimumOrderPrice;

    @Enumerated(EnumType.STRING)
    private baemin.baeminjpa.domain.CouponStatus status;

    private String couponImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_ID")
    private Store store;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<User_Coupon> user_couponList = new ArrayList<>();
}
