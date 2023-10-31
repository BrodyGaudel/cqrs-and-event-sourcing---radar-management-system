package com.brodygaudel.infractionservice.common.dto;

import java.util.Date;

public record CreateInfractionRequestDTO(Date date, double speed, double amount, String vehicleId, String radarId) {
}
