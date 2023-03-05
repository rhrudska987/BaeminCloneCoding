package baemin.baeminjpa.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "categoryId")
    private Long id;

    private String categoryName;
    private String categoryImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Category_Store> category_storeList = new ArrayList<>();
}
