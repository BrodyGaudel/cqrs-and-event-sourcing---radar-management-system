package com.brodygaudel.immatriculationservice.common.dto;

public record UpdateVehicleRequestDTO(String numMatricule, String marque, int model, float puissanceFiscal, String ownerId) {
}
