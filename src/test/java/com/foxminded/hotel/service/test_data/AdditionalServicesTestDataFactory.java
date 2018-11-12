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

    public static List<AdditionalService> getServicesForRoom1() {
        return Lists.newArrayList(getService1(), getService2());
    }

    public static AdditionalService getServiceById(long id){
        if(id == 1){
            return getService1();
        } else if (id == 2) {
            return getService2();
        } else {
            return getService3();
        }

    }

    public static List<AdditionalService> getServices(AdditionalService... services){
        return Lists.newArrayList(services);
    }

    public static List<AdditionalService> getAllServices(){
        return Lists.newArrayList(getService1(), getService2(), getService3());
    }

//    public static List<ServiceResource> getServiceResources(AdditionalService... services){
//        return Lists.newArrayList(services)
//                .stream()
//                .map(ServiceResource::new)
//                .collect(Collectors.toList());
//    }

    public static List<ServiceResource> getAllServiceResource() {
        return Lists.newArrayList(getService1(), getService2(), getService3())
                .stream()
                .map(ServiceResource::new)
                .collect(Collectors.toList());
    }
}
