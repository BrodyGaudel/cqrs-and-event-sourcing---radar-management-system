package com.brodygaudel.immatriculationservice.common.event;

public class OwnerDeletedEvent extends BaseEvent<String> {
    public OwnerDeletedEvent(String id) {
        super(id);
    }
}
