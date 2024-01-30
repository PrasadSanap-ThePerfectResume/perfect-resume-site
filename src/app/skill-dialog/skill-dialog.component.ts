import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Skill } from '../models/skill.model';
import { AppRequestBody } from '../models/appRequestBody';
import { UserserviceService } from '../services/userservice.service';
import { delay } from 'rxjs';
import { User } from '../models/user.model';
import { AppConstants } from '../models/AppConstants';
import { UpdateAlertDialogComponent } from '../update-alert-dialog/update-alert-dialog.component';


@Component({
  selector: 'app-skill-dialog',
  templateUrl: './skill-dialog.component.html',
  styleUrls: ['./skill-dialog.component.css']
})
export class SkillDialogComponent {
  editData: any;
  inputData !: any;
  isUpdateFlag !: boolean
  levels = AppConstants.LEVELS
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog,private dialogRef: MatDialogRef<SkillDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.skill) {
      this.mySkillForm.setValue({
        skillId: this.inputData.skill.skillId,
        skillTitle: this.inputData.skill.skillTitle,
        level: this.inputData.skill.level
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }

  setPopUpData() {
    this
  }

  closeSkillPopUp() {
    this.dialogRef.close(this.inputData);
  }

  mySkillForm = this.formBuilder.group({
    skillId: this.formBuilder.control(0),
    skillTitle: this.formBuilder.control(''),
    level: this.formBuilder.control('')
  })

  openSkillUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'skill' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateSkillData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }

  updateSkillData() {
    console.log("Before skill data::", this.inputData, "\nUpdating skill data::", this.mySkillForm.value)
    //  Creating request body for skill update
    const existingSkill = this.inputData as Skill
    console.log("Existing Skill :-",existingSkill)
    const updatedSkill = this.mySkillForm.value
    console.log("Updated Skill :-",updatedSkill)

    const apiRequestBody = {
      "user": this.inputData.user as User,
      "skill": this.mySkillForm.value as Skill,
    }
    console.log("App Request Body For Update Skill::", apiRequestBody)
    //  Update call for skill
    const responce = this.userService.updateUserSingleSkill(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.mySkillForm);
  }

  createNewSkillData() {
    console.log("Creating skill data::", this.mySkillForm.value)
    //  Creating request body for skill update
    console.log(this.mySkillForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "skill": this.mySkillForm.value as Skill,
    }
    console.log("App Request Body For Create New SKILL::", apiRequestBody)
    //  Create call for skill
    const responce = this.userService.createUserSkill(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.mySkillForm);
  }
}
