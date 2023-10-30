package com.brodygaudel.immatriculationservice.common.dto;

public record CreateVehicleRequestDTO(String numMatricule, String marque, int model, float puissanceFiscal, String ownerId) {
}
