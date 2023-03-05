package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Delivery extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "deliveryId")
    private Long id;

    private String destinationAddress;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private User user;


    public void setOrder(Order order) {
        this.order = order;
    }

    public void setDestinationAddress(User user){
        for (Address address : user.getAddressList()){
            if(address.getStatus() == AddressStatus.SELECTED){
                this.destinationAddress = address.getAddress();
            }
        }
        throw new IllegalStateException("선택된 주소가 없습니다.");
    }


}
