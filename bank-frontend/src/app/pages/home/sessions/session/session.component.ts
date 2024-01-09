import { Component, Input } from '@angular/core';
import { DeviceType } from "@enums/home/DeviceType";
import { Session } from "@core/data/home/session";

@Component({
  selector: 'app-session',
  templateUrl: './session.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionComponent {
    @Input() session !: Session;
    protected readonly DeviceType = DeviceType;
}
