package com.brodygaudel.immatriculationservice.common.dto;

import java.util.Date;

public record UpdateOwnerRequestDTO(String name, Date birthDate, String email) {
}
