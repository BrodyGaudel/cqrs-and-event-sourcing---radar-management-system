package com.brodygaudel.immatriculationservice.common.dto;

import java.util.Date;

public record CreateOwnerRequestDTO(String name, Date birthDate, String email) {
}
