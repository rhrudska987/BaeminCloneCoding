package baemin.baeminjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Address extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "addressId")
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private AddressStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
}
