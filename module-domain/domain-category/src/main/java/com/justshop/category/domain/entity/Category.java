package com.justshop.category.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 카테고리 명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; // 부모 카테고리

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>(); // 자식 카테고리
    private Integer depth; // 깊이

    @Builder
    public Category(String name, Category parent, Integer depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }

    // 연관관계 편의 메서드
    public void addChild(Category child) {
        this.children.add(child);
    }
}
