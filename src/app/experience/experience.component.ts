import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserserviceService } from '../services/userservice.service';
import { Experience } from '../models/experience.model';
import { AppConstants } from '../models/AppConstants';
import { Achievements } from '../models/achievement.model';
import { Project } from '../models/project.model';


@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.css']
})
export class ExperienceComponent {
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
  projectInputReset=''
  constructor(private router: Router, private formBuilder: FormBuilder, private userService: UserserviceService) {
    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: 30 }, (_, i) => currentYear - i);
  }
  experienceForm !: FormGroup
  projectForm !: FormGroup
  achievementForm !: FormGroup

  ngOnInit(): void {
    this.experienceForm = this.formBuilder.group({
      title: ['', Validators.required],
      companyName: ['', Validators.required],
      employmentType: ['', Validators.required],
      startMonth: ['', Validators.required],
      startYear: ['', Validators.required],
      endMonth: ['', Validators.required],
      endYear: ['', Validators.required],
      location: ['', Validators.required],
      workingMode: ['', Validators.required],
      skills: ['', Validators.required],
      description: ['', Validators.required],
    });
    this.projectForm = this.formBuilder.group({
      projectTitle: ['', Validators.required],
      frontEndTech: ['', Validators.required],
      backEndTech: ['', Validators.required]
    });
    // this.projectForm = this.formBuilder.group({
    //   projects: this.formBuilder.array([this.createProjects()])
    // });
    this.achievementForm = this.formBuilder.group({
      achievement: ['', Validators.required],
    });
  }

  employmentTypeList = AppConstants.EMPLOYMENT_TYPE
  workingModeList = AppConstants.WORKING_MODE
  monthList = AppConstants.MONTHS



  // Achievement Logic

  achivement !: Achievements
  achivementList: Achievements[] = []
  experienceList:Experience[]=[]

  onSubmitAchievement() {
    this.achivement = this.achievementForm.value as Achievements
    this.achievementHint = ''
    if (this.achievementForm.invalid) {
      if (this.achivement.achievement.length <= 0)
        this.achievementHint = 'Field is empty !'
      else if (this.achivement.achievement.trim().length < 5)
        this.achievementHint = 'Enter proper achievement.'

    } else if (this.achievementForm.valid) {
      if (this.achivement.achievement.trim().length < 5) {
        this.achievementHint = 'Enter proper achievement.'
        return
      }
      this.achievementHint = ''

      console.log(this.achivement)
      this.achivementList.push(this.achivement)
      this.achievementForm.reset()
      console.log(this.achivementList)
    }

  }

  removeAchivementItem(achievementValue: string) {
    this.achivementList = this.achivementList.filter(achievementList => achievementList.achievement !== achievementValue);
    console.log(this.achivementList)

  }


  // Project Logic
  project !: Project
  projectList: Project[] = []
  onSubmitProject() {
    this.project = this.projectForm.value as Project
    this.projectTitleHint = ''
    this.frontEndTechHint = 'If not applicable put NA'
    this.backEndTechHint = 'If not applicable put NA'
    if (this.projectForm.invalid) {
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

    } else if (this.projectForm.valid) {
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
      this.projectForm.reset()
      console.log(this.projectList)
    }


  }

  removeProjectItem(projectValue: string) {
    this.projectList = this.projectList.filter(projectAlias => projectAlias.projectTitle !== projectValue);
    console.log(this.projectList)

  }

  // For Experience Logic
  panelOpenState = false
  experience !: Experience


  saveExperience() {
    this.experience = this.experienceForm.value as Experience
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



    if (this.experienceForm.invalid) {
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
    else if (this.experienceForm.valid) {

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
      else if (this.experience.startYear == 0 ) {
        this.startYearHint = 'Select start year'
        return
      }
      else if (this.experience.endMonth.trim().length <= 2) {
        this.endMonthHint = 'Select end month'
        return
      }
      else if (this.experience.endYear == 0) {
        this.endYearHint = 'Select end year'
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
      console.log('Valid - Experience', this.experience)

    }
  }


}
