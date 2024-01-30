import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LanguageDialogComponent } from '../language-dialog/language-dialog.component';
import { UserserviceService } from '../services/userservice.service';

@Component({
  selector: 'app-delete-alert-dialog',
  templateUrl: './delete-alert-dialog.component.html',
  styleUrls: ['./delete-alert-dialog.component.css']
})
export class DeleteAlertDialogComponent {
  fieldName: string='item';
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }
  ngOnInit(): void {
    this.fieldName=this.data.triggerFor
  }
  deleteCurrent(flag:boolean){
    this.dialogRef.close(flag);
  }
  closeDeletePopUp(flag:boolean) {
    this.dialogRef.close(flag);
  }
}
