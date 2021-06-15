package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GenerateCriteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetGeneratedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.CostGeneratorService;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;

import java.util.List;

@RestController
@RequestMapping(path = "cost_generator")
@AllArgsConstructor
public class CostGeneratorController {

    private final CostGeneratorService costGeneratorService;

    @GetMapping(path = "{id}")
    public ResponseEntity<?> calculateStats(@PathVariable Long id) {
        try {
            GetPricedDietDto diet = costGeneratorService.calculateStats(id);
            return new ResponseEntity<>(diet, HttpStatus.OK);
        } catch (DietNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> generateDiet(@RequestBody GenerateCriteria criteria) {
        try {
            List<GetGeneratedDietDto> diets = costGeneratorService.generateDiet(criteria);
            return new ResponseEntity<>(diets, HttpStatus.OK);
        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

}
