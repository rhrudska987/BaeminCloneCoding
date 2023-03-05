package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category_Store extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "category_storeId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;
}
