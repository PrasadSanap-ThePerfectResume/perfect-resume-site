import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LanguageDialogComponent } from '../language-dialog/language-dialog.component';
import { UserserviceService } from '../services/userservice.service';
import { ActivityCertification } from '../models/activity-certification.model';
import { UpdateAlertDialogComponent } from '../update-alert-dialog/update-alert-dialog.component';
import { User } from '../models/user.model';

@Component({
  selector: 'app-activity-certification-dialog',
  templateUrl: './activity-certification-dialog.component.html',
  styleUrls: ['./activity-certification-dialog.component.css']
})
export class ActivityCertificationDialogComponent {
  
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  inputData !: any;

  myAcitivityCertForm=this.formBuilder.group({
    actCerId: this.formBuilder.control(0),
    actCerTitle: this.formBuilder.control('')
  })

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.activityCertfication) {
      this.myAcitivityCertForm.setValue({
        actCerId: this.inputData.activityCertfication.actCerId,
        actCerTitle: this.inputData.activityCertfication.actCerTitle,
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }

  openAcitivityCertUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'Activity Certification' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateActivityCertData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }
  updateActivityCertData() {
    console.log("Before  data::", this.inputData, "\nUpdating Activity Certification data::", this.myAcitivityCertForm.value)
    //  Creating request body for Activity Certification update
    const existingAcitivityCert = this.inputData as ActivityCertification
    console.log("Existing AcitivityCert :-",existingAcitivityCert)
    const updatedAcitivityCert = this.myAcitivityCertForm.value
    console.log("Updated AcitivityCert :-",updatedAcitivityCert)

    const apiRequestBody = {
      "user": this.inputData.user as User,
      "activityCertification": this.myAcitivityCertForm.value as ActivityCertification,
    }
    console.log("App Request Body For Update AcitivityCert::", apiRequestBody)
    //  Update call for Activity Certification
    const responce = this.userService.updateUserSingleActivityCertification(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myAcitivityCertForm);
  }

  createNewActivityCerData(){
    console.log("Creating AcitivityCert data::", this.myAcitivityCertForm.value)
    //  Creating request body for AcitivityCert update
    console.log(this.myAcitivityCertForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "activityCertification": this.myAcitivityCertForm.value as ActivityCertification,
    }
    console.log("App Request Body For Create New SKILL::", apiRequestBody)
    //  Create call for AcitivityCert
    const responce = this.userService.createUserActivityCertification(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myAcitivityCertForm);
  }


  closeActivityCertPopUp() {
    this.dialogRef.close(this.inputData);
  }
}
