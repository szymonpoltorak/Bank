import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordCombinationComponent } from './password-combination.component';

describe('PasswordCombinationComponent', () => {
  let component: PasswordCombinationComponent;
  let fixture: ComponentFixture<PasswordCombinationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PasswordCombinationComponent]
    });
    fixture = TestBed.createComponent(PasswordCombinationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
