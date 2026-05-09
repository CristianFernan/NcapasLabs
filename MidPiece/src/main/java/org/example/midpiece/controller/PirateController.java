package org.example.midpiece.controller;

import lombok.AllArgsConstructor;
import org.example.midpiece.domain.entity.Pirate;
import org.example.midpiece.service.impl.PirateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PirateController {
    private final PirateServiceImpl pirateService;

    @PostMapping("/pirates")
    public ResponseEntity<Pirate> createPirate(
            @RequestBody Pirate pirate
    ){
        pirateService.createPirate(pirate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pirate);
    }

    @GetMapping("/pirates")
    public ResponseEntity<List<Pirate>> getAllPirates(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pirateService.getAll());
    }

    @GetMapping("/pirates/{id}")
    public ResponseEntity<Pirate> getPiratebyId(
            @PathVariable UUID id
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pirateService.getPirateById(id));
    }

    @PutMapping("/pirates/{id}")
    public ResponseEntity<Pirate> updatePirate(
            @PathVariable UUID id,
            @RequestBody Pirate pirate
    ) {
        pirateService.updatePirate(id, pirate);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pirate);
    }

    @DeleteMapping("/pirates/{id}")
    public ResponseEntity<Pirate> deletePirate(
            @PathVariable UUID id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pirateService.deletePirate(id));
    }
}
