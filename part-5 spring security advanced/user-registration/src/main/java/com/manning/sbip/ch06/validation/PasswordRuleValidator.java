package com.manning.sbip.ch06.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordRuleValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(8, 30));
        rules.add(new RepeatCharacterRegexRule(3));
        List<CharacterRule> characterRules = new ArrayList<>();
        characterRules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        characterRules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        characterRules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        characterRules.add(new CharacterRule(EnglishCharacterData.Special, 2));

        CharacterCharacteristicsRule characterCharacteristicsRule =
                new CharacterCharacteristicsRule(2, characterRules);
        rules.add(characterCharacteristicsRule);

        // how to use Passey
        // b1: instance PasswordValidator
        // b2 init PasswordData object with password str as input
        // b3 PasswordValidator.validate
        PasswordValidator passwordValidator = new PasswordValidator(rules);
        PasswordData passwordData = new PasswordData(password);
        RuleResult result = passwordValidator.validate(passwordData);
        return result.isValid();
    }
}
