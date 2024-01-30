import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AppConstants } from '../models/AppConstants';
import { Education } from '../models/education.model';
import { User } from '../models/user.model';
import { UserserviceService } from '../services/userservice.service';
import { UpdateAlertDialogComponent } from '../update-alert-dialog/update-alert-dialog.component';

@Component({
  selector: 'app-education-dialog',
  templateUrl: './education-dialog.component.html',
  styleUrls: ['./education-dialog.component.css']
})
export class EducationDialogComponent {
  years: number[];
  educationModes=AppConstants.EDUCATION_MODE;
  monthList = AppConstants.MONTHS

  editData: any;
  inputData !: any;
  isUpdateFlag !: boolean
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  isCheckboxChecked: boolean = false
  headerTitleText:String= 'CREATE'
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog,private dialogRef: MatDialogRef<EducationDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) {
    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: 30 }, (_, i) => currentYear - i);
   }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.education) {
      this.myEducationForm.setValue({
        educationId: this.inputData.education.educationId,
        university: this.inputData.education.university,
        college: this.inputData.education.college,
        stream: this.inputData.education.stream,
        startYear: this.inputData.education.startYear,
        startMonth: this.inputData.education.startMonth,
        presentCollege:this.inputData.education.presentCollege,
        endMonth: this.inputData.education.endMonth,
        endYear: this.inputData.education.endYear,
        percentage: this.inputData.education.percentage,
        educationMode: this.inputData.education.educationMode
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
      this.isCheckboxChecked = this.inputData.education.presentCollege
      this.onLoadChangeCheckbox()
    }
  }
  
  onLoadChangeCheckbox():void{
    const selectedEndMonthValueControl = this.myEducationForm.get('endMonth');
    const selectedEndYearValueControl = this.myEducationForm.get('endYear');
    if ((selectedEndYearValueControl) && (selectedEndMonthValueControl)) {
      if (this.isCheckboxChecked) {
        selectedEndMonthValueControl.disable();
        selectedEndYearValueControl.disable();
      } else {
        selectedEndMonthValueControl.enable();
        selectedEndYearValueControl.enable();
      }
    }
  }

  onChangeCheckbox(): void {
    const selectedEndMonthValueControl = this.myEducationForm.get('endMonth');
    const selectedEndYearValueControl = this.myEducationForm.get('endYear');
    console.log('Checkbox checked start value :', this.isCheckboxChecked)
    this.isCheckboxChecked = !this.isCheckboxChecked;
    console.log('Checkbox checked after value :', this.isCheckboxChecked)

    if ((selectedEndYearValueControl) && (selectedEndMonthValueControl)) {
      if (this.isCheckboxChecked) {
        selectedEndMonthValueControl.disable();
        selectedEndYearValueControl.disable();
      } else {
        selectedEndMonthValueControl.enable();
        selectedEndYearValueControl.enable();
      }
    }
  }

  closeEducationPopUp() {
    this.dialogRef.close(this.inputData);
  }

  myEducationForm = this.formBuilder.group({
    educationId: this.formBuilder.control(0),
    university: this.formBuilder.control(''),
    college: this.formBuilder.control(''),
    stream: this.formBuilder.control(''),
    startYear: this.formBuilder.control(0),
    endYear: this.formBuilder.control(0),
    startMonth: this.formBuilder.control(''),
    presentCollege:false,
    endMonth: this.formBuilder.control(''),
    percentage : this.formBuilder.control(''),
    educationMode : this.formBuilder.control(''),
  })


  openEducationUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'education' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateEducationData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }

  updateEducationData() {
    console.log("Before education data::", this.inputData, "\nUpdating education data::", this.myEducationForm.value)
    //  Creating request body for education update
    const existingEducation = this.inputData as Education
    console.log("Existing Education :-",existingEducation)
    const updatedEducation = this.myEducationForm.value
    console.log("Updated Education :-",updatedEducation)

    const apiRequestBody = {
      "user": this.inputData.user as User,
      "education": this.myEducationForm.value as Education,
    }
    console.log("App Request Body For Update Education::", apiRequestBody)
    //  Update call for education
    const responce = this.userService.updateUserSingleEducation(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myEducationForm);
  }

  createNewEducationData() {
    console.log("Creating education data::", this.myEducationForm.value)
    //  Creating request body for education update
    console.log(this.myEducationForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "education": this.myEducationForm.value as Education,
    }
    console.log("App Request Body For Create New SKILL::", apiRequestBody)
    //  Create call for education
    const responce = this.userService.createUserEducation(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myEducationForm);
  }

}
