package com.justshop.core.delivery.command;

import com.justshop.core.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryManager {

    private final DeliveryRepository deliveryRepository;
}
