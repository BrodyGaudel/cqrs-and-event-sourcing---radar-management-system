package com.brodygaudel.immatriculationservice.common.command;

import lombok.Getter;
import java.util.Date;

@Getter
public class UpdateOwnerCommand extends BaseCommand<String>{

    private final String name;
    private final Date birthDate;
    private final String email;

    public UpdateOwnerCommand(String commandId, String name, Date birthDate, String email) {
        super(commandId);
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }
}
