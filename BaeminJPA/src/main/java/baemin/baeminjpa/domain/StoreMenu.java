package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class StoreMenu extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "menuId")
    private Long id;

    private String menuName;
    private int price;
    private String description;
    private String menuImage;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    public void setOrder(Order order){
        this.order = order;
    }


}
