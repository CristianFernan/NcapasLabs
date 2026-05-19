package org.example.sheikaregistry.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sheikaregistry.domain.dto.request.CreateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.request.UpdateSpecimenRequest;
import org.example.sheikaregistry.domain.dto.response.GeneralResponse;
import org.example.sheikaregistry.services.implementations.SpecimenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/specimen")
@RequiredArgsConstructor
public class SpecimenController {
    private final SpecimenServiceImpl specimenService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createService(@RequestBody @Valid CreateSpecimenRequest specimen){
        return buildResponse(
                "Specimen created sucessfully",
                HttpStatus.CREATED,
                specimenService.createSpecimen(specimen)
        );
    };

    @GetMapping("/getAll")
    public ResponseEntity<GeneralResponse> getAllSpecimens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
            ) {
                return buildResponse(
                        "All specimens found",
                        HttpStatus.OK,
                        specimenService.getAllSpecimens(page, size, sortBy, sortOrder)
                );
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<GeneralResponse> getSpecimenById(@PathVariable UUID id){
        return buildResponse(
                "Specimen found",
                HttpStatus.OK,
                specimenService.getSpecimenById(id)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> updateSpecimen(
            @PathVariable UUID id,
            @RequestBody UpdateSpecimenRequest specimen
    ) {
        return buildResponse(
                "Specimen updated successfully",
                HttpStatus.OK,
                specimenService.updateSpecimen(id, specimen));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> deleteSpecimen(@PathVariable UUID id){
        return buildResponse(
                "Specimen deleted successfully",
                HttpStatus.OK,
                specimenService.deleteSpecimen(id)
        );
    }

    public ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                    .uri(uri)
                    .message(message)
                    .status(status.value())
                    .time(LocalDateTime.now())
                    .data(data)
                    .build()
                );
    }
}

