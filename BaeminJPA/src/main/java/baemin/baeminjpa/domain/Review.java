package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Review extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "reviewId")
    private Long id;

    private double starPoint;
    private String menuName;
    private String hostComment;
    private String userComment;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String reviewImage1;
    private String reviewImage2;
    private String reviewImage3;
    private String reviewImage4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

}
