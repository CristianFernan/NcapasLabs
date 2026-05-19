package org.example.sheikaregistry.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sheikaregistry.common.mappers.SpecimenMapper;
import org.example.sheikaregistry.domain.dto.request.CreateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.request.UpdateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.response.PageableResponse;
import org.example.sheikaregistry.domain.dto.response.specimen.SpecimenResponse;
import org.example.sheikaregistry.domain.entities.Specimen;
import org.example.sheikaregistry.exceptions.ResourceNotFoundException;
import org.example.sheikaregistry.repositories.SpecimenRepository;
import org.example.sheikaregistry.services.SpecimenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {
    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest specimen) {
        return specimenMapper.toDto(
                specimenRepository.save(specimenMapper.toEntityCreate(specimen))
        );
    }

    @Override
    public PageableResponse<SpecimenResponse> getAllSpecimens(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SpecimenResponse> specimenPage = specimenMapper.toDtoList(specimenRepository.findAll(pageable));
        if (specimenPage.getTotalElements() == 0)
            throw  new ResourceNotFoundException("No specimen were registered");

        return PageableResponse.<SpecimenResponse>builder()
                .content(specimenPage.getContent())
                .page(specimenPage.getNumber())
                .size(specimenPage.getSize())
                .totalElements(specimenPage.getTotalElements())
                .last(specimenPage.isLast())
                .build();
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {
        return specimenMapper.toDto(specimenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specimen not found in  Sheikah Slate  Records"))
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request) {
        this.getSpecimenById(id);
        return specimenMapper.toDto(specimenRepository.save(specimenMapper.toEntityUpdate(request, id)));
    }

    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {
        SpecimenResponse existSpecimen = this.getSpecimenById(id);
        specimenRepository.deleteById(id);
        return existSpecimen;
    }
}