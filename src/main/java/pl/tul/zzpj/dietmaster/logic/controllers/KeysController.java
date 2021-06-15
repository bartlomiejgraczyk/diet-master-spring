package pl.tul.zzpj.dietmaster.logic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.UseKeyDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.KeyService;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.DietitianClient;
import pl.tul.zzpj.dietmaster.model.entities.Key;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.model.exception.KeyValidationException;
import pl.tul.zzpj.dietmaster.model.exception.NoPermissionKeyDeleteException;
import pl.tul.zzpj.dietmaster.model.exception.exists.KeyExistsException;

import pl.tul.zzpj.dietmaster.model.exception.notfound.ClientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.KeyNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "keys")
public class KeysController {

    private final KeyService keyService;

    @Autowired
    public KeysController(KeyService keyService) {
        this.keyService = keyService;
    }


    @GetMapping
    public ResponseEntity<?> getMyKeys() {
        try {
            var keys = keyService.getMyKeys();
            return ResponseEntity.status(HttpStatus.OK).body(keys);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateKey(@RequestBody CreateKeyDto createKeyDto) {

            try {
                keyService.generateKey(createKeyDto);
            } catch (UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (KeyExistsException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            } catch (KeyValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }

        return ResponseEntity.status(HttpStatus.CREATED).body("Key generated!");

    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteKey(@PathVariable Long id) {

        try {
            keyService.deleteKey(id);
        }catch (NotFoundException | UserNotFoundException | KeyNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (NoPermissionKeyDeleteException | KeyValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok("Key deleted!");
    }


    @PostMapping(path = "/use")
    public ResponseEntity<String> useKey(@RequestBody UseKeyDto useKeyDto) {
        try {
            keyService.useKey(useKeyDto);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Key used!");
    }

    @DeleteMapping(path = "/dietitian/{dietitian}")
    public ResponseEntity<String> deleteKey(@PathVariable String dietitian) {
        try {
            keyService.deleteDietitian(dietitian);
        }catch (NotFoundException | UserNotFoundException | ClientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Key deleted!");
    }
}
