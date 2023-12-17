package com.example.users.rest.utils;

import com.example.users.rest.exception.PatternException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class RegexUtil {

    private final Map<String, String> regexPatterns = new HashMap<>();

    public RegexUtil(@Value("${regex.email}") String emailRegex,
                     @Value("${regex.password}") String passwordRegex) {
        regexPatterns.put("email", emailRegex);
        regexPatterns.put("password", passwordRegex);
        // Agrega más patrones según sea necesario
    }

    public void addPattern(String key, String regex) {
        regexPatterns.put(key, regex);
    }

    public void validRegexPattern(String value, String key){
        String regexPattern = regexPatterns.get(key);
        if (regexPattern == null) {
            throw new IllegalArgumentException("No se encontró un patrón para la clave: " + key);
        }
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            throw new PatternException(key);
        }
    }

}
