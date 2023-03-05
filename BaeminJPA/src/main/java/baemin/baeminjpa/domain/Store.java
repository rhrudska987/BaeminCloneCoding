package baemin.baeminjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Store extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "storeId")
    private Long id;

    private String storeImage;
    private String storeName;
    private double cntStarPoint;
    private String phoneNumber;
    private int cntReview;
    private int cntHeart;
    private int cntHostComment;
    private int deliveryTip;
    private String address;
    private String description;
    private int minimumOrderPrice;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;
    private String storeImage1;
    private String storeImage2;
    private String storeImage3;
    private String storeImage4;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreMenu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Heart> heartList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Category_Store> category_storeList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Order> orderList = new ArrayList<>();
}