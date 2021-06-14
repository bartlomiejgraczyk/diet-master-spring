package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.CreateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.GetMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.UpdateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MeasurementService;
import pl.tul.zzpj.dietmaster.model.exception.MeasurementSavedException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import javax.ws.rs.NotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "measurements")
@AllArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @GetMapping
    public ResponseEntity<List<GetMeasurementDto>> getAllMeasurements(){
        List<GetMeasurementDto> measurements = measurementService.getAllMeasurements();
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @GetMapping(path = "{user}")
    public ResponseEntity<?> getUserMeasurements(@PathVariable String user){
        try {
            List<GetMeasurementDto> measurements = measurementService.getMeasurementsOfClient(user);
            return ResponseEntity.ok(measurements);
        } catch (EnumConstantNotPresentException  exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMeasurement(@RequestBody UpdateMeasurementDto updateMeasurementDto) {
        try {
            measurementService.updateMeasurement(updateMeasurementDto);
        } catch (NotFoundException | UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (MeasurementSavedException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.ok("Measurement updated");
    }

    @PostMapping
    public ResponseEntity<String> addMeasurement(@RequestBody CreateMeasurementDto createMeasurementDto) {
        try {
            measurementService.createMeasurement(createMeasurementDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body("Request contains mull non-null field(s)");
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (MeasurementSavedException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Measurement added");
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteMeasurement(@PathVariable Long id) {
        try {
            measurementService.deleteMeasurement(id);
        }catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Measurement deleted");
    }

}
