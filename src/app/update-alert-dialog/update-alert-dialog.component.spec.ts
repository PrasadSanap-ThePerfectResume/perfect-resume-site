import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAlertDialogComponent } from './update-alert-dialog.component';

describe('UpdateAlertDialogComponent', () => {
  let component: UpdateAlertDialogComponent;
  let fixture: ComponentFixture<UpdateAlertDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateAlertDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateAlertDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
