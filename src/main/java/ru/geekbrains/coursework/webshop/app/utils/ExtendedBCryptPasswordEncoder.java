package ru.geekbrains.coursework.webshop.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtendedBCryptPasswordEncoder extends BCryptPasswordEncoder {

    public boolean isBCryptPassword(CharSequence checkedText) {
        if (checkedText == null || checkedText.length() == 0)
            return false;
        Pattern bcryptPattern = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
        Matcher matcher = bcryptPattern.matcher(checkedText);
        return matcher.matches();
    }
}
