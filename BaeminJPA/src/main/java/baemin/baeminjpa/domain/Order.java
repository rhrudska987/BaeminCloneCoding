package baemin.baeminjpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "orderId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int orderPrice;
    private int deliveryTip;
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private ReceiveWay receiveWay;

    private String commentStore;
    private String commentRider;

    @Column(unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToMany(mappedBy = "order")
    private List<StoreMenu> menuList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;


    public void setUser(User user){
        this.user = user;
        user.getOrderList().add(this);
    }

    public void setStore(Store store){
        this.store = store;
        store.getOrderList().add(this);
    }

    public void addMenu(StoreMenu storeMenu){
        menuList.add(storeMenu);
        storeMenu.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void setStatus(OrderStatus orderStatus){
        this.status = orderStatus;
    }

    public static Order createOrder(User user, Store store, Delivery delivery, StoreMenu... storeMenus){
        Order order = new Order();
        order.setUser(user);
        order.setStore(store);
        order.setDelivery(delivery);
        for (StoreMenu storeMenu: storeMenus) {
            order.addMenu(storeMenu);
        }
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
    }

    public void setOrderPrice(){
        int orderPrice = 0;
        for(StoreMenu storeMenu : menuList){
            orderPrice += storeMenu.getPrice();
        }
        this.orderPrice = orderPrice;
    }

    public void setDeliveryTip(){
        this.deliveryTip = store.getDeliveryTip();
    }

    public void setTotalPrice(){
        this.totalPrice = this.orderPrice + this.deliveryTip;
    }

}
