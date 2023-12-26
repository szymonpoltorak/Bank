import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from "@angular/forms";
import { Combination } from "@core/data/auth/combination";

@Component({
    selector: 'app-password-combination',
    templateUrl: './password-combination.component.html',
    styleUrls: ['./password-combination.component.scss']
})
export class PasswordCombinationComponent implements OnInit {
    combinations: Combination[] = [
        {
            index: 1,
            controlName: "first"
        },
        {
            index: 2,
            controlName: "second"
        },
        {
            index: 3,
            controlName: "third"
        },
        {
            index: 4,
            controlName: "fourth"
        },
        {
            index: 5,
            controlName: "fifth"
        },
        {
            index: 6,
            controlName: "sixth"
        }
    ];
    @Input() combinationGroup !: FormGroup;

    ngOnInit(): void {
    }
}
