package com.foxminded.hotel.service.test_data;

import com.foxminded.hotel.enums.ChargePeriod;
import com.foxminded.hotel.model.AdditionalService;
import com.foxminded.hotel.resources.ServiceResource;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class AdditionalServicesTestDataFactory {
    public static AdditionalService getService1(){
        AdditionalService service = new AdditionalService("Breakfast", 15000, ChargePeriod.DAILY);
        service.setServiceId(1L);
        return service;
    }

    public static AdditionalService getService2(){
        AdditionalService service = new AdditionalService("Cleaning", 10000, ChargePeriod.DAILY);
        service.setServiceId(2L);
        return service;
    }

    public static AdditionalService getService3(){
        AdditionalService service = new AdditionalService("Laundry", 5000, ChargePeriod.ONE_TIME);
        service.setServiceId(3L);
        return service;
    }

    public static List<ServiceResource> getAllServices() {
        return Lists.newArrayList(getService1(), getService2(), getService3())
                .stream()
                .map(ServiceResource::new)
                .collect(Collectors.toList());
    }
}
