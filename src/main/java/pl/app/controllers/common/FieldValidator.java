package pl.app.controllers.common;

import com.jfoenix.controls.JFXPasswordField;
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

    public static void setRequiredValidator(String message, JFXPasswordField passwordField) {
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator(message);
        passwordField.getValidators().add(requiredFieldValidator);
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) passwordField.validate();
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

    public static void setPasswordValidator(JFXTextField textField) {
        String message = "Długość hasła musi wynosić od 8 do 20 znaków oraz zawierać minimum jedną dużą i jedną małą lierę";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        setRegexValidator(message, textField, passwordRegex);

    }

    public static void setEmailValidator(JFXTextField textField) {
        String message = "Nieprawidłowy adres email";
        String emailRegex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        setRegexValidator(message, textField, emailRegex);
    }

    public static void setPeselValidator(JFXTextField textField) {
        String message = "Długość peselu musi wynosić 11 znaków";
        String peselRegex = "^[0-9]{11,11}$";
        setRegexValidator(message, textField, peselRegex);
    }

}
