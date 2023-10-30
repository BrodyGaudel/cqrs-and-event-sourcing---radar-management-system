package com.brodygaudel.immatriculationservice.common.event;

import lombok.Getter;
import java.util.Date;

@Getter
public class OwnerUpdatedEvent extends BaseEvent<String> {
    private final String name;

    private final Date birthDate;

    private final String email;

    public OwnerUpdatedEvent(String id, String name, Date birthDate, String email) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }
}
