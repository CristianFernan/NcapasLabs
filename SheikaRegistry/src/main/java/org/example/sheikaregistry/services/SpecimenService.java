package org.example.sheikaregistry.services;

import org.example.sheikaregistry.domain.dto.request.CreateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.request.UpdateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.response.PageableResponse;
import org.example.sheikaregistry.domain.dto.response.specimen.SpecimenResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest specimen);
    PageableResponse<SpecimenResponse> getAllSpecimens(int page, int size, String sortBy, String sortOrder);
    SpecimenResponse getSpecimenById(UUID id);
    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest specimen);
    SpecimenResponse deleteSpecimen(UUID id);
}
