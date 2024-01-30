import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityCertificationDialogComponent } from './activity-certification-dialog.component';

describe('ActivityCertificationDialogComponent', () => {
  let component: ActivityCertificationDialogComponent;
  let fixture: ComponentFixture<ActivityCertificationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivityCertificationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivityCertificationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
