import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggedFormComponent } from './logged-form.component';

describe('LoggedFormComponent', () => {
  let component: LoggedFormComponent;
  let fixture: ComponentFixture<LoggedFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoggedFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoggedFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
