import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/models/AppConstants';
import { Achievements } from 'src/app/models/achievement.model';
import { Experience } from 'src/app/models/experience.model';
import { Project } from 'src/app/models/project.model';
import { User } from 'src/app/models/user.model';
import { UserserviceService } from 'src/app/services/userservice.service';
import { UpdateAlertDialogComponent } from 'src/app/update-alert-dialog/update-alert-dialog.component';
import { ProjectDialogComponent } from '../project-dialog/project-dialog.component';
import { AchievementDialogComponent } from '../achievement-dialog/achievement-dialog.component';

@Component({
  selector: 'app-experience-dialog',
  templateUrl: './experience-dialog.component.html',
  styleUrls: ['./experience-dialog.component.css']
})
export class ExperienceDialogComponent {

  employmentTypeList = AppConstants.EMPLOYMENT_TYPE
  workingModeList = AppConstants.WORKING_MODE
  monthList = AppConstants.MONTHS
  years: number[];
  inputData !: any;
  isUpdateFlag !: boolean
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  isCheckboxChecked: boolean = false
  headerTitleText: String = 'CREATE'

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


  constructor(public dialogRef: MatDialogRef<any>, @Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private userService: UserserviceService) {
    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: 30 }, (_, i) => currentYear - i);
  }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.experience) {
      this.myExperienceForm.setValue({
        experienceId: this.inputData.experience.experienceId,
        title: this.inputData.experience.title,
        employmentType: this.inputData.experience.employmentType,
        companyName: this.inputData.experience.companyName,
        location: this.inputData.experience.location,
        workingMode: this.inputData.experience.workingMode,
        startMonth: this.inputData.experience.startMonth,
        startYear: this.inputData.experience.startYear,
        endMonth: this.inputData.experience.endMonth,
        endYear: this.inputData.experience.endYear,
        presentCompany: this.inputData.experience.presentCompany,
        skills: this.inputData.experience.skills,
        description: this.inputData.experience.description
      });
      this.projectList = this.inputData.experience.projects;
      this.achivementList = this.inputData.experience.achievements;
    }

    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
      this.isCheckboxChecked = this.inputData.experience.presentCompany
      this.onLoadChangeCheckbox()
    }
  }


  achivement !: Achievements
  achivementList: Achievements[] = []
  experience !: Experience


  myExperienceForm = this.formBuilder.group({
    experienceId: [0],
    title: ['', Validators.required],
    companyName: ['', Validators.required],
    employmentType: ['', Validators.required],
    startMonth: ['', Validators.required],
    startYear: [0, Validators.required],
    endMonth: [{ value: '', disabled: false }],
    endYear: [{ value: 0, disabled: false }],
    presentCompany: [false],
    location: ['', Validators.required],
    workingMode: ['', Validators.required],
    skills: ['', Validators.required],
    description: ['', Validators.required],
  });
  myProjectForm = this.formBuilder.group({
    projectTitle: ['', Validators.required],
    frontEndTech: ['', Validators.required],
    backEndTech: ['', Validators.required]
  });
  myAchievementForm = this.formBuilder.group({
    achievement: ['', Validators.required],
  });

  removeAchivementItem(achievementValue: string) {
    this.achivementList = this.achivementList.filter(achievementList => achievementList.achievement !== achievementValue);
    console.log(this.achivementList)

  }

  removeProjectItem(projectValue: string) {
    this.projectList = this.projectList.filter(projectAlias => projectAlias.projectTitle !== projectValue);
    console.log(this.projectList)
  }


  onLoadChangeCheckbox():void{
    const selectedEndMonthValueControl = this.myExperienceForm.get('endMonth');
    const selectedEndYearValueControl = this.myExperienceForm.get('endYear');
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
    const selectedEndMonthValueControl = this.myExperienceForm.get('endMonth');
    const selectedEndYearValueControl = this.myExperienceForm.get('endYear');
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

  project !: Project
  projectList: Project[] = []
  onSubmitProject() {

    this.project = this.myProjectForm.value as Project
    this.projectTitleHint = ''
    this.frontEndTechHint = 'If not applicable put NA'
    this.backEndTechHint = 'If not applicable put NA'
    if (this.myProjectForm.invalid) {
      if (this.project.projectTitle.length <= 0)
        this.projectTitleHint = 'Field is empty !'
      else if (this.project.frontEndTech.length <= 0)
        this.frontEndTechHint = 'Field is empty !'
      else if (this.project.backEndTech.length <= 0)
        this.backEndTechHint = 'Field is empty !'
      else if (this.project.projectTitle.trim().length <= 3)
        this.projectTitleHint = 'Enter proper achievement.'
      else if (this.project.frontEndTech.trim().length <= 3)
        this.frontEndTechHint = 'Enter proper achievement.'
      else if (this.project.backEndTech.trim().length <= 3)
        this.backEndTechHint = 'Field is empty !'

    } else if (this.myProjectForm.valid) {
      if (this.project.projectTitle.trim().length <= 3) {
        this.projectTitleHint = 'Enter proper project title.'
        return
      }
      if (this.project.frontEndTech.trim().length <= 3 && this.project.frontEndTech.toUpperCase() != 'NA') {
        this.frontEndTechHint = 'Enter proper front end tech.'
        return
      }
      if (this.project.backEndTech.trim().length <= 3 && this.project.backEndTech.toUpperCase() != 'NA') {
        this.backEndTechHint = 'Enter proper back end tech.'
        return
      }
      this.projectTitleHint = ''
      this.frontEndTechHint = ''
      this.backEndTechHint = ''

      console.log(this.project)
      this.projectList.push(this.project)
      this.myProjectForm.reset()
      console.log(this.projectList)
    }
  }

  onSubmitAchievement() {
    this.achivement = this.myAchievementForm.value as Achievements
    this.achievementHint = ''
    if (this.myAchievementForm.invalid) {
      if (this.achivement.achievement.length <= 0)
        this.achievementHint = 'Field is empty !'
      else if (this.achivement.achievement.trim().length < 5)
        this.achievementHint = 'Enter proper achievement.'

    } else if (this.myAchievementForm.valid) {
      if (this.achivement.achievement.trim().length < 5) {
        this.achievementHint = 'Enter proper achievement.'
        return
      }
      this.achievementHint = ''

      console.log(this.achivement)
      this.achivementList.push(this.achivement)
      this.myAchievementForm.reset()
      console.log(this.achivementList)
    }

  }


  openExperienceUpdateAlertPopUp() {
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'experience' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.saveExperience(false)
      } else {
        console.log("Not Updating..!")
      }
    })
  }

  updateExperienceData() {
    console.log("Before experience data::", this.inputData, "\nUpdating experience data::", this.myExperienceForm.value)
    //  Creating request body for experience update
    const existingExperience = this.inputData as Experience
    console.log("Existing Experience :-", existingExperience)
    const updatedExperience = this.myExperienceForm.value as Experience

    updatedExperience.achievements = this.achivementList
    updatedExperience.projects = this.projectList
    console.log("Updated Experience :-", updatedExperience)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "experience": this.myExperienceForm.value,
    }
    console.log("App Request Body For Update Experience::", apiRequestBody)
    //  Update call for experience
    const responce = this.userService.updateUserSingleExperience(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myExperienceForm);
  }

  editAchivementItem(achievementValue: string) {
    console.log("Edit Achivement::", achievementValue);
  }

  editProjectItem(projectValue: any) {
    console.log("Edit Project::", projectValue);
  }

  createNewExperienceData() {
    console.log("Creating experience data::", this.myExperienceForm.value)
    this.saveExperience(true)
  }

  saveExperience(flag: boolean) {
    this.experience = this.myExperienceForm.value as Experience
    this.titleHint = 'ex. Software Engineer'
    this.employmentTypeHint = 'ex. Intership'
    this.companyNameHint = 'ex.XYZ Ltd'
    this.locationNameHint = 'ex. Mumbai'
    this.workingModeHint = 'ex. Remote'
    this.skillHint = 'ex. Java,HTML'
    this.descriptionHint = 'ex. Working with dev and ops team.'
    this.startMonthHint = ''
    this.startYearHint = ''
    this.endMonthHint = ''
    this.endYearHint = ''
    // Adding projectList into experience
    this.experience.projects = this.projectList

    // Adding achivementList into experience
    this.experience.achievements = this.achivementList

    if (this.myExperienceForm.invalid) {
      console.log('Invalid - Experience', this.experience)
      if (this.experience.title.length <= 0)
        this.titleHint = 'Field is empty !'
      else if (this.experience.companyName.length <= 0)
        this.companyNameHint = 'Field is empty !'
      else if (this.experience.employmentType.length <= 0)
        this.employmentTypeHint = 'Field is empty !'
      else if (this.experience.startMonth.length <= 0)
        this.startMonthHint = 'Field is empty !'
      else if (this.experience.startYear == 0)
        this.startYearHint = 'Field is empty !'
      else if (this.experience.endMonth.length <= 0)
        this.endMonthHint = 'Field is empty !'
      else if (this.experience.endYear == 0)
        this.endYearHint = 'Field is empty !'
      else if (this.experience.location.length <= 0)
        this.locationNameHint = 'Field is empty !'
      else if (this.experience.workingMode.length <= 0)
        this.workingModeHint = 'Field is empty !'
      else if (this.experience.skills.length <= 0)
        this.skillHint = 'Field is empty !'
      else if (this.experience.description.length <= 0)
        this.descriptionHint = 'Field is empty !'
    }
    else if (this.myExperienceForm.valid) {

      if (this.experience.title.trim().length <= 5) {
        this.titleHint = 'Enter proper title.'
        return
      }
      else if (this.experience.companyName.trim().length <= 3) {
        this.companyNameHint = 'Enter proper company name.'
        return
      }
      else if (this.experience.employmentType.trim().length <= 5) {
        this.employmentTypeHint = 'Select employment type '
        return
      }
      else if (this.experience.startMonth.trim().length <= 2) {
        this.startMonthHint = 'Select start month'
        return
      }
      else if (this.experience.startYear == 0) {
        this.startYearHint = 'Select start year'
        return
      }
      else if (this.experience.location.trim().length <= 3) {
        this.locationNameHint = 'Enter proper location'
        return
      }
      else if (this.experience.workingMode.trim().length <= 4) {
        this.workingModeHint = 'Select working mode'
        return
      }
      else if (this.experience.skills.trim().length <= 5) {
        this.skillHint = 'Enter proper skills '
        return
      }
      else if (this.experience.description.trim().length <= 5) {
        this.descriptionHint = 'Enter proper description'
        return
      }  
      else {
        //  Creating request body for experience update
        console.log("Experience :-", this.experience)
        const apiRequestBody = {
          "user": this.inputData.user as User,
          "experience": this.experience,
        }

        if (flag) {
          console.log("App Request Body For Create New Experience::", apiRequestBody)
          //  Create call for experience
          const responce = this.userService.createUserExperience(apiRequestBody)
          responce.subscribe(result => {
            console.log(result)
          }, error => {
            console.log(error)
          })
        } else {
          console.log("App Request Body For Update Experience::", apiRequestBody)
          //  Update call for experience
          const responce = this.userService.updateUserSingleExperience(apiRequestBody)
          responce.subscribe(result => {
            console.log(result)
          }, error => {
            console.log(error)
          })
        }

        this.dialogRef.close(this.myExperienceForm);
      }
      console.log('Valid - Experience', this.experience)

    }
  }


  closeExperiencePopUp() {
    this.dialogRef.close(this.inputData);
  }

  // PROJECT DIALOG SECTION 

  openNewProjectDialog() {
    const _popUpProjectNewData = this.dialog.open(ProjectDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'Project', isUpdateCreateFlag: false }
    });
    _popUpProjectNewData.afterClosed().subscribe(item => {

      console.log("Create project data recieved from project dialog :: ", item.value)
      const createProject = item.value as Project
      this.projectList.push(createProject)
    })
  }

  openPopUpUpdateProject(project: Project) {
    const _popUpProjectUpdateData = this.dialog.open(ProjectDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { project: project, isUpdateCreateFlag: true }
    });
    _popUpProjectUpdateData.afterClosed().subscribe(item => {
      // This is calling data from db after update Experience data
      console.log("Update project data recieved from project dialog :: ", item.value)
      const updateProject = item.value as Project
      this.projectList.map(project => {
        if (project.projectId == updateProject.projectId) {
          project.backEndTech = updateProject.backEndTech
          project.frontEndTech = updateProject.frontEndTech
          project.projectTitle = updateProject.projectTitle
        }
      });
      console.log(this.projectList)

    })
  }


  // ACHIEVEMENT DIALOG SECTION 

  openNewAchievementDialog() {
    const _popUpAchievementNewData = this.dialog.open(AchievementDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'Achievement', isUpdateCreateFlag: false }
    });
    _popUpAchievementNewData.afterClosed().subscribe(item => {

      console.log("Create achievement data recieved from achievement dialog :: ", item.value)
      const createAchievements = item.value as Achievements
      this.achivementList.push(createAchievements)
    })
  }

  openPopUpUpdateAchievement(achievement: Achievements) {
    const _popUpProjectUpdateData = this.dialog.open(AchievementDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { achievement: achievement, isUpdateCreateFlag: true }
    });
    _popUpProjectUpdateData.afterClosed().subscribe(item => {
      // This is calling data from db after update Experience data
      console.log("Update achievement data recieved from project dialog :: ", item.value)
      const updateAchievement = item.value as Achievements
      this.achivementList.map(project => {
        if (project.achievementId == updateAchievement.achievementId) {
          project.achievement = updateAchievement.achievement
        }
      });
      console.log(this.achivementList)

    })
  }

}
