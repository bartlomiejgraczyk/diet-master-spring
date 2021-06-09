package pl.tul.zzpj.dietmaster.logic.controllers.requests;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationRequest {
    
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
