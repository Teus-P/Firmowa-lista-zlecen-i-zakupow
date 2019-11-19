package pl.app.controllers.common;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;

public class FieldValidator {

    public static void setNumberValidator(String message, JFXTextField textField) {
        NumberValidator numberValidator = new NumberValidator(message);
        textField.getValidators().add(numberValidator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) textField.validate();
        });
    }

    public static void setRequiredValidator(String message, JFXTextField textField) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator(message);
        textField.getValidators().add(requiredFieldValidator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) textField.validate();
        });
    }

    public static void setRegexValidator(String message, JFXTextField textField, String regex) {
        RegexValidator regexValidator = new RegexValidator(message);
        regexValidator.setRegexPattern(regex);
        textField.getValidators().add(regexValidator);
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) textField.validate();
        });
    }


}
