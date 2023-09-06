package com.justshop.core.product.domain;

import com.justshop.core.BaseEntity;
import com.justshop.core.product.domain.enums.OptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Option extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 옵션 ID

    @Enumerated(EnumType.STRING)
    private OptionType type; // 옵션 타입

    private String value; // 옵션 값

}
