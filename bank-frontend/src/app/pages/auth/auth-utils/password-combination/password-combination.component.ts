import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { Combination } from "@core/data/auth/combination";

@Component({
    selector: 'app-password-combination',
    templateUrl: './password-combination.component.html',
    styleUrls: ['./password-combination.component.scss']
})
export class PasswordCombinationComponent implements OnInit {
    @Input() combinationGroup !: FormGroup;
    @Input() combinations!: Combination[];

    ngOnInit(): void {
    }
}
