package pl.tul.zzpj.dietmaster.util;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {

    private static final String REGEX = "^(.+)@(.+)$";

    @Override
    public boolean test(String email) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
