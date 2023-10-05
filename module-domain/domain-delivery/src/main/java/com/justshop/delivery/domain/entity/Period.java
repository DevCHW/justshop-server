package com.justshop.delivery.domain.entity;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period extends Delivery {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
