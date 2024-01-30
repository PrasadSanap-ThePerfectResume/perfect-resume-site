import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LanguageDialogComponent } from '../language-dialog/language-dialog.component';
import { UserserviceService } from '../services/userservice.service';

@Component({
  selector: 'app-update-alert-dialog',
  templateUrl: './update-alert-dialog.component.html',
  styleUrls: ['./update-alert-dialog.component.css']
})
export class UpdateAlertDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  saveCurrent(flag:boolean){
    this.dialogRef.close(flag);
  }
  closeUpdatePopUp(flag:boolean) {
    this.dialogRef.close(flag);
  }
}
