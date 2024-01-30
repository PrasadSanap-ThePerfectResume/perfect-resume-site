import { Component, Inject } from '@angular/core';
import { User } from '../models/user.model';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppConstants } from '../models/AppConstants';
import { FormBuilder } from '@angular/forms';
import { UserserviceService } from '../services/userservice.service';
import { Language } from '../models/language.model';

@Component({
  selector: 'app-language-dialog',
  templateUrl: './language-dialog.component.html',
  styleUrls: ['./language-dialog.component.css']
})
export class LanguageDialogComponent {
  editData: any;
  inputData !: any;
  isUpdateFlag !: boolean
  levels = AppConstants.LEVELS
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.language) {
      this.myLanguageForm.setValue({
        languageId: this.inputData.language.languageId,
        langTitle: this.inputData.language.langTitle,
        level: this.inputData.language.level
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }


  closeLanguagePopUp() {
    this.dialogRef.close(this.inputData);
  }

  myLanguageForm = this.formBuilder.group({
    languageId: this.formBuilder.control(0),
    langTitle: this.formBuilder.control(''),
    level: this.formBuilder.control('')
  })

  updateLanguageData() {
    console.log("Before language data::", this.inputData, "\nUpdating language data::", this.myLanguageForm.value)
    //  Creating request body for language update
    console.log(this.inputData.user as User)
    console.log(this.myLanguageForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "language": this.myLanguageForm.value as Language,
    }
    console.log("App Request Body For Update language::", apiRequestBody)
    //  Update call for language
    const responce = this.userService.updateUserSinglelanguage(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myLanguageForm);
  }

  createNewLanguageData() {
    console.log("Creating language data::", this.myLanguageForm.value)
    //  Creating request body for language update
    console.log(this.myLanguageForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "language": this.myLanguageForm.value as Language,
    }
    console.log("App Request Body For Create New language::", apiRequestBody)
    //  Create call for language
    const responce = this.userService.createUserlanguage(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myLanguageForm);
  }
}
