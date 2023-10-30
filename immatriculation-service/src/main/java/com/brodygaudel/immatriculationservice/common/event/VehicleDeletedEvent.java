package com.brodygaudel.immatriculationservice.common.event;

public class VehicleDeletedEvent extends BaseEvent<String> {
    public VehicleDeletedEvent(String id) {
        super(id);
    }
}
