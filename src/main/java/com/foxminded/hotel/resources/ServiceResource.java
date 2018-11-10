package com.foxminded.hotel.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.foxminded.hotel.enums.ChargePeriod;
import com.foxminded.hotel.model.AdditionalService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceResource {
    private Long serviceId;
    private String serviceName;
    private int price;
    private ChargePeriod chargePeriod;

    @JsonCreator
    public ServiceResource(AdditionalService service) {
        this.serviceId = service.getServiceId();
        this.serviceName = service.getServiceName();
        this.price = service.getPrice()/100;
        this.chargePeriod = service.getChargePeriod();
    }
}
