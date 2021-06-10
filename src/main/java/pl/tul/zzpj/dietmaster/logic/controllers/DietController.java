package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.CreateDietRequest;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.DietService;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.mappers.RequestDietMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "diets")
@AllArgsConstructor
public class DietController {

    private final DietService dietService;
    private final RequestDietMapper mapper;

    @PostMapping
    public ResponseEntity<?> addDiet(@RequestBody CreateDietRequest createDietRequest) {
        try {
            dietService.addDiet(mapper.newDietFromDto(createDietRequest));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Diet created!");
    }

    @GetMapping//(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAvailableDiets() {
        try {
            List<Diet> diets = dietService.getAllAvailableDiets();
            return ResponseEntity.ok(diets.stream().map(Diet::getId).collect(Collectors.toList()));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
    }

    @GetMapping(path = "{type}")
    public ResponseEntity<?> getDietsByType(@PathVariable int type) {
        return ResponseEntity.ok(dietService.getDietsByType(type).stream().map(Diet::getId).collect(Collectors.toList()));
    }

    @PutMapping
    public ResponseEntity<?> updateDiet(@RequestBody UpdateDietRequest updateDietRequest) {
        try {
            dietService.updateDiet(updateDietRequest);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiet(@PathVariable Long id) {
        try {
            dietService.deleteDiet(id);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Diet deleted!");
    }
}
