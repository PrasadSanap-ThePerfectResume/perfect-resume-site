import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LanguageDialogComponent } from 'src/app/language-dialog/language-dialog.component';
import { Achievements } from 'src/app/models/achievement.model';
import { UserserviceService } from 'src/app/services/userservice.service';
import { UpdateAlertDialogComponent } from 'src/app/update-alert-dialog/update-alert-dialog.component';

@Component({
  selector: 'app-achievement-dialog',
  templateUrl: './achievement-dialog.component.html',
  styleUrls: ['./achievement-dialog.component.css']
})
export class AchievementDialogComponent {
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  inputData !: any;
  achievement !: Achievements
  achievementHint=''
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Experience Dialog Component::', this.inputData)
    if (this.inputData.achievement) {
      this.myAchievementForm.setValue({
        achievementId:this.inputData.achievement.achievementId,
        achievement: this.inputData.achievement.achievement
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }

  myAchievementForm = this.formBuilder.group({
    achievementId:[0],
    achievement: ['', Validators.required],
  });


  openAchievementUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'Achievement' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateAchievementData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }
  updateAchievementData() {
    console.log("Before Achievement data::", this.inputData, "\nUpdating Achievement data::", this.myAchievementForm.value)
    const existingAchievement = this.inputData as Achievements
    console.log("Existing Achievement :-",existingAchievement)
    const updatedAchievement = this.myAchievementForm.value
    console.log("Updated Achievement :-",updatedAchievement)
    this.onSubmitAchievement()
  }

  createNewAchievementData(){
    console.log("Creating Achievement data::", this.myAchievementForm.value)
    //  Creating request body for Achievement update
    this.onSubmitAchievement()
  }

  closeAchievementPopUp() {
    this.dialogRef.close(this.inputData);
  }

  onSubmitAchievement() {
    this.achievement = this.myAchievementForm.value as Achievements
    this.achievementHint = ''
    if (this.myAchievementForm.invalid) {
      if (this.achievement.achievement.length <= 0)
        this.achievementHint = 'Field is empty !'
      else if (this.achievement.achievement.trim().length < 5)
        this.achievementHint = 'Enter proper achievement.'

    } else if (this.myAchievementForm.valid) {
      if (this.achievement.achievement.trim().length < 5) {
        this.achievementHint = 'Enter proper achievement.'
        return
      }
      this.achievementHint = ''
      console.log(this.achievement)
      this.dialogRef.close(this.myAchievementForm);
    }

  }
}
