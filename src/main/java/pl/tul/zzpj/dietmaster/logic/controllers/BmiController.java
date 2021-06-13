package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.BmiService;
import pl.tul.zzpj.dietmaster.model.mappers.RequestDietMapper;

import java.io.IOException;

@RestController
@RequestMapping(path = "bmi")
@AllArgsConstructor
public class BmiController {

    private final BmiService bmiService;
    private final RequestDietMapper mapper;


    @GetMapping(path = "raw")
    public ResponseEntity<?> getRawData() {
        try {
            var a = bmiService.getRawData("POL" );
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping(path = "compare")
    public ResponseEntity<?> getCompare() {
        try {
            var a = bmiService.getRawData("POL" );
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
