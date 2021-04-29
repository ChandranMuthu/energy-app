package com.example.crudwithvaadin.view;

import com.example.crudwithvaadin.model.Role;
import com.example.crudwithvaadin.model.UserDetails;
import com.example.crudwithvaadin.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.StringUtils.isEmpty;


@Route(value = "new-user", layout = MainLayout.class)
@PageTitle("Energy App - Sign up")
public class SignupView extends VerticalLayout {
    private PasswordField passwordField1;
    private PasswordField passwordField2;
    private UserService userService;
    private BeanValidationBinder<UserDetails> binder;

    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    public SignupView(@Autowired UserService userService) {
        this.userService = userService;
        H2 title = new H2("Create New User");
        Span errorMessage = new Span();
        TextField firstName = new TextField("First name");
        firstName.setRequired(true);
        firstName.setRequiredIndicatorVisible(true);
        firstName.setErrorMessage("First name cannot be empty");


        TextField lastName = new TextField("Last name");
        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);
        lastName.setErrorMessage("Last name cannot be empty");

        TextField handleField = new TextField("Username");
        handleField.setRequired(true);
        handleField.setRequiredIndicatorVisible(true);
        handleField.setErrorMessage("Username cannot be empty");

        EmailField emailField = new EmailField("Email");
        emailField.setRequiredIndicatorVisible(true);
        emailField.setErrorMessage("Username cannot be empty");

        emailField.setVisible(true);
        passwordField1 = new PasswordField("Enter password");
        passwordField2 = new PasswordField("Confirm Password");

        Button submitButton = new Button("Submit");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //submitButton.setEnabled(false);

        FormLayout formLayout = new FormLayout(title, firstName, lastName, handleField, passwordField1, passwordField2,
                emailField, errorMessage, submitButton);

        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components take full width regardless if we use one column or two (it
        // just looks better that way)
        formLayout.setColspan(title, 2);
        formLayout.setColspan(errorMessage, 2);
        formLayout.setColspan(submitButton, 2);

        // Add some styles to the error message to make it pop out
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

        // Add the form to the page
        add(formLayout);







//        binder = new BeanValidationBinder<User>(User.class);
//
//        // Basic name fields that are required to fill in
//        binder.forField(firstName).asRequired().bind("firstname");
//        binder.forField(lastName).asRequired().bind("lastname");
//
//        // The handle has a custom validator, in addition to being required. Some values
//        // are not allowed, such as 'admin'; this is checked in the validator.
//        binder.forField(handleField).withValidator(this::validateHandle).asRequired().bind("handle");
//
//        binder.forField(emailField).asRequired(new VisibilityEmailValidator("Value is not a valid email address")).bind("email");
//
//        // Another custom validator, this time for passwords
//        binder.forField(passwordField1).asRequired().withValidator(this::passwordValidator).bind("password");
//        // We won't bind passwordField2 to the Binder, because it will have the same
//        // value as the first field when correctly filled in. We just use it for
//        // validation.
//
//        // The second field is not connected to the Binder, but we want the binder to
//        // re-check the password validator when the field value changes. The easiest way
//        // is just to do that manually.
//        passwordField2.addValueChangeListener(e -> {
//
//            // The user has modified the second field, now we can validate and show errors.
//            // See passwordValidator() for how this flag is used.
//            enablePasswordValidation = true;
//
//            binder.validate();
//        });
//
//        // A label where bean-level error messages go
//        binder.setStatusLabel(errorMessage);


        submitButton.addClickListener(e -> {
            try {
                //TODO need to add validation here as the Binder validation is not working.
                String message = new String();
                if(isEmpty(firstName.getValue()))
                {
                   message = message + "First name" + ", ";
                }
                if(isEmpty(lastName.getValue()))
                {
                    message = message + "Last name" + ", ";
                }
                if(isEmpty(handleField.getValue()))
                {
                    message = message + "Username" + ", ";
                }
                if(isEmpty(passwordField1.getValue()) || isEmpty(passwordField2.getValue()))
                {
                    message = message + "Password" + ", ";
                }
                if(isEmpty(emailField.getValue()))
                {
                    message = message + "Email id";
                }
                if(StringUtils.isNotEmpty(message))
                {
                    errorMessage.setText(
                            "Please enter the following details : " + message);
                    return;
                }
                if(!passwordField1.getValue().equals(passwordField2.getValue()))
                {
                    errorMessage.getElement().setProperty("innerHtml","Password did not match");

                    return;
                }
                if(userService.checkIfUserNameAlreadyExist(handleField.getValue()))
                {
                    errorMessage.setText("Username with id " + handleField.getValue() + " already exist. Please enter a different username");
                    return;
                }
                // Create empty bean to store the details into
                if(isEmpty(message)) {
                    UserDetails userDetails = new UserDetails(firstName.getValue(), lastName.getValue(), handleField.getValue(), passwordField2.getValue(), emailField.getValue(), Role.USER);
                    // Call backend to store the data
                    userService.saveUser(userDetails);
                    // Show success message if everything went well
                    showSuccess(userDetails);
                }

            } catch (Exception e2) {

                // For some reason, the save failed in the back end.

                // First, make sure we store the error in the server logs (preferably using a
                // logging framework)
                e2.printStackTrace();

                // Notify, and let the user try again.
                errorMessage.setText("Saving the data failed, please try again");
            }
        });

    }

    private void showSuccess(UserDetails userDetails) {
        Notification notification = Notification.show("Data saved, welcome " + userDetails.getUserName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private ValidationResult passwordValidator(String pass1, ValueContext valueContext) {
        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = passwordField2.getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private ValidationResult validateHandle(String handle, ValueContext valueContext) {
        String errorMsg = userService.validateHandle(handle);

        if (errorMsg == null) {
            return ValidationResult.ok();
        }

        return ValidationResult.error(errorMsg);
    }

    public class VisibilityEmailValidator extends EmailValidator {

        public VisibilityEmailValidator(String errorMessage) {
            super(errorMessage);
        }

        @Override
        public ValidationResult apply(String value, ValueContext context) {
            // normal email validation
            return super.apply(value, context);
        }
    }

}


