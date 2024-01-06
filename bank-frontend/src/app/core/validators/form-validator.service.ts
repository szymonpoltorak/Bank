import { Injectable } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { FormValidation } from "@enums/auth/FormValidation";
import { PasswordValidatorService } from "@core/validators/password-validator.service";

@Injectable({
    providedIn: 'root'
})
export class FormValidatorService {
    readonly emailControl: FormControl = new FormControl(
        FormValidation.EMAIL_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.EMAIL_PATTERN)
        ]
    );

    readonly passwordControl: FormControl = new FormControl(
        FormValidation.PASSWORD_VALUE,
        [
            Validators.required,
            Validators.minLength(10),
            Validators.maxLength(30),
            this.passwordValidator.passwordEntropyValidator(),
            Validators.pattern(FormValidation.PASSWORD_PATTERN),
        ]
    );

    readonly repeatPasswordControl: FormControl = new FormControl(
        FormValidation.PASSWORD_VALUE,
        [
            Validators.required,
            Validators.minLength(10),
            Validators.maxLength(30),
            this.passwordValidator.passwordEntropyValidator(),
            Validators.pattern(FormValidation.PASSWORD_PATTERN),
        ]
    );
    private readonly passwordName: string = "userPassword";
    private readonly repeatPassword: string = "repeatPassword";

    readonly passwordCombinationGroup: FormGroup = this.formBuilder.group({
        first: this.combinationControlNewInstance(),
        second: this.combinationControlNewInstance(),
        third: this.combinationControlNewInstance(),
        fourth: this.combinationControlNewInstance(),
        fifth: this.combinationControlNewInstance(),
        sixth: this.combinationControlNewInstance(),
    });

    constructor(private formBuilder: FormBuilder,
                private passwordValidator: PasswordValidatorService) {
    }

    buildLoginFormGroup(): FormGroup {
        return this.formBuilder.group({
            email: this.emailControl,
            passwordCombination: this.passwordCombinationGroup,
            telephone: new FormControl("", [])
        });
    }

    buildResetPasswordFormGroup(): FormGroup {
        return this.formBuilder.group({
            passwordInputs: this.formBuilder.group({
                    userPassword: this.passwordControl,
                    repeatPassword: this.repeatPasswordControl
                },
                {
                    validator: this.passwordValidator.passwordMatchValidator(this.passwordName, this.repeatPassword)
                }
            ),
            date: new FormControl("", [])
        });
    }

    buildForgotPasswordForm(): FormGroup {
        return this.formBuilder.group({
            email: this.emailControl,
            dateTimeLocal: new FormControl("", [])
        });
    }

    private combinationControlNewInstance(): FormControl {
        return new FormControl("", [
            Validators.required, Validators.minLength(1), Validators.maxLength(1)
        ]);
    }
}
