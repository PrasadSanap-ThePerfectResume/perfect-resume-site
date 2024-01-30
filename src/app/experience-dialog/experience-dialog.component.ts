import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { UserserviceService } from '../services/userservice.service';
import { AppConstants } from '../models/AppConstants';
import { Achievements } from '../models/achievement.model';
import { Experience } from '../models/experience.model';

@Component({
  selector: 'app-experience-dialog',
  templateUrl: './experience-dialog.component.html',
  styleUrls: ['./experience-dialog.component.css']
})
export class ExperienceDialogComponentOLD {
  @Output() closeDialog = new EventEmitter<void>();
  @Output() updateValue = new EventEmitter<any>();

  years: number[];
  titleHint = 'ex. Software Engineer'
  employmentTypeHint = 'ex. Intership'
  companyNameHint = 'ex.XYZ Ltd'
  locationNameHint = 'ex. Mumbai'
  workingModeHint = 'ex. Remote'
  skillHint = 'ex. Java,HTML'
  descriptionHint = 'ex. Working with dev and ops team.'
  projectTitleHint = ''
  frontEndTechHint = 'If not applicable put NA'
  backEndTechHint = 'If not applicable put NA'
  achievementHint = ''
  startMonthHint = ''
  startYearHint = ''
  endMonthHint = ''
  endYearHint = ''
  projectInputReset = ''

  constructor(public dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) public data: any, private router: Router, private formBuilder: FormBuilder, private userService: UserserviceService) {
    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: 30 }, (_, i) => currentYear - i);
  }

  originalData=this.data

  projectForm !: FormGroup
  achievementForm !: FormGroup

  ngOnInit(): void {

    this.projectForm = this.formBuilder.group({
      projectTitle: ['', Validators.required],
      frontEndTech: ['', Validators.required],
      backEndTech: ['', Validators.required]
    });

    this.achievementForm = this.formBuilder.group({
      achievement: ['', Validators.required],
    });
  }

  employmentTypeList = AppConstants.EMPLOYMENT_TYPE
  workingModeList = AppConstants.WORKING_MODE
  monthList = AppConstants.MONTHS


  onSubmitProject() {
    throw new Error('Method not implemented.');
  }

  removeProjectItem(arg0: any) {
    throw new Error('Method not implemented.');
  }



  onSubmitAchievement() {
    const currentAchivement = this.achievementForm.value as Achievements
    this.achievementHint = ''
    if (this.achievementForm.invalid) {
      if (currentAchivement.achievement.length <= 0)
        this.achievementHint = 'Field is empty !'
      else if (currentAchivement.achievement.trim().length < 5)
        this.achievementHint = 'Enter proper achievement.'

    } else if (this.achievementForm.valid) {
      if (currentAchivement.achievement.trim().length < 5) {
        this.achievementHint = 'Enter proper achievement.'
        return
      }
      this.achievementHint = ''
      this.data.achievements.push(currentAchivement)
    }
  }

  removeAchivementItem(achievementValue: string) {
    this.data.achievements = this.data.achievements.filter((achievements: { achievement: string; }) => achievements.achievement !== achievementValue);
  }


  // onCloseClick(data: any): void {
  //   this.dialogRef.close(this.originalData); // Return the selected value on button click
  // }
  onCloseClick(): void {
    this.dialogRef.close(this.originalData);
  }

  onUpdateClick(data:any): void {
    this.dialogRef.close(data);
  }
}
