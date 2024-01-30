import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LanguageDialogComponent } from 'src/app/language-dialog/language-dialog.component';
import { Project } from 'src/app/models/project.model';
import { UserserviceService } from 'src/app/services/userservice.service';
import { UpdateAlertDialogComponent } from 'src/app/update-alert-dialog/update-alert-dialog.component';

@Component({
  selector: 'app-project-dialog',
  templateUrl: './project-dialog.component.html',
  styleUrls: ['./project-dialog.component.css']
})
export class ProjectDialogComponent {
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  inputData !: any;
  project !: Project

  projectTitleHint = ''
  frontEndTechHint = 'If not applicable put NA'
  backEndTechHint = 'If not applicable put NA'
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog, private dialogRef: MatDialogRef<LanguageDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Experience Dialog Component::', this.inputData)
    if (this.inputData.project) {
      this.myProjectForm.setValue({
        projectId:this.inputData.project.projectId,
        projectTitle: this.inputData.project.projectTitle,
        frontEndTech: this.inputData.project.frontEndTech,
        backEndTech: this.inputData.project.backEndTech
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }

  myProjectForm=this.formBuilder.group({
    projectId:[0],
    projectTitle: ['', Validators.required],
    frontEndTech: ['', Validators.required],
    backEndTech: ['', Validators.required]
  })

  
  openProjectUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'Project' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateProjectData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }
  updateProjectData() {
    console.log("Before project data::", this.inputData, "\nUpdating project data::", this.myProjectForm.value)
    const existingProject = this.inputData as Project
    console.log("Existing Project :-",existingProject)
    const updatedProject = this.myProjectForm.value
    console.log("Updated Project :-",updatedProject)
    this.onSubmitProject()
  }

  createNewProjectData(){
    console.log("Creating Project data::", this.myProjectForm.value)
    //  Creating request body for Project update
    this.onSubmitProject()
  }

  closeProjectPopUp() {
    this.dialogRef.close(this.inputData);
  }
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
      this.dialogRef.close(this.myProjectForm);
    }
  }
}
