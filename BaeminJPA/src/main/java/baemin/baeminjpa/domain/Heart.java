package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Heart extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "heartId")
    private Long id;

    @Enumerated(EnumType.STRING)
    private HeartStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;
}
