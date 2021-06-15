package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.GetMyBmiDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.BmiService;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoDataFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoMeasurementFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;
import pl.tul.zzpj.dietmaster.model.mappers.RequestDietMapper;

import javax.ws.rs.QueryParam;
import java.io.IOException;

@RestController
@RequestMapping(path = "bmi")
@AllArgsConstructor
public class BmiController {

    private final BmiService bmiService;
    private final EnumStringJpaConverter<BmiCategory> converter = new BmiCategory.Converter();

    @PostMapping(path = "my")
    public ResponseEntity<?> getMyBmi(@RequestBody GetMyBmiDto getMyBmiDto) {
        try {
            var a = bmiService.getMyBmi(getMyBmiDto);
            return ResponseEntity.ok(a);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "raw")
    public ResponseEntity<?> getRawData(@QueryParam("country") String country, @QueryParam("category") String category) {
        try {
            var a = bmiService.getRawCountry(country, converter.convertToEntityAttribute(category));
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "filtered")
    public ResponseEntity<?> getRawDataFiltered(@QueryParam("country") String country, @QueryParam("year") int year, @QueryParam("sex") String sex, @QueryParam("category") String category) {
        try {
            var a = bmiService.getRawFiltered(country, year, sex, converter.convertToEntityAttribute(category));
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "compare")
    public ResponseEntity<?> getCompare(@RequestBody  GetMyBmiDto getMyBmiDto) {
        try {
            var a = bmiService.getCompare(getMyBmiDto);

            return ResponseEntity.ok(a);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UserNotFoundException | NoDataFoundException | NoMeasurementFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
