package com.justshop.member.domain.entity;

import com.justshop.jpa.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Integer amount;
    private Long productId;

    @Builder
    public Cart(Member member, Integer amount, Long productId) {
        this.member = member;
        this.amount = amount;
        this.productId = productId;
    }
}
