import { Component, Inject } from '@angular/core';
import { Experience } from '../models/experience.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ExperienceDialogComponent } from '../dialog/experience-dialog/experience-dialog.component';
import { SkillDialogComponent } from '../skill-dialog/skill-dialog.component';
import { Skill } from '../models/skill.model';
import { UserserviceService } from '../services/userservice.service';
import { User } from '../models/user.model';
import { Language } from '../models/language.model';
import { Education } from '../models/education.model';
import { Achievements } from '../models/achievement.model';
import { ActivityCertification } from '../models/activity-certification.model';
import { Address } from '../models/address.model';
import { AppRequestBody } from '../models/appRequestBody';
import { LanguageDialogComponent } from '../language-dialog/language-dialog.component';
import { DeleteAlertDialogComponent } from '../delete-alert-dialog/delete-alert-dialog.component';
import { ActivityCertificationDialogComponent } from '../activity-certification-dialog/activity-certification-dialog.component';
import { EducationDialogComponent } from '../education-dialog/education-dialog.component';
import { AddressDialogComponent } from '../dialog/address-dialog/address-dialog.component';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  constructor(public dialog: MatDialog, private userService: UserserviceService) { }

  isLinear = true;

  languages: Language[] = [];
  skills: Skill[] = [];
  experienceList: Experience[] = []
  educationList: Education[] = []
  activityCertificationList: ActivityCertification[] = []
  address: Address = {
    addressId: 0,
    addressType: '',
    pin: '',
    town: '',
    city: '',
    district: '',
    state: '',
    country: ''
  };

  user: any = {
    id: 0,
    firstname: '',
    lastname: '',
    username: ''
  }


  hideDrawer: boolean = false;
  arrowIcon: String = 'arrow_forward'



  isEducationClick: boolean = true;
  isExpereinceClick: boolean = true;
  isAddressClick: boolean = true;
  isLanguageClick: boolean = true;
  isActivityCertificationClick: boolean = true;
  isSkillClick: boolean = true;
  isPersonalDetailsClick: boolean = true;

  isAddressPresent = true
  isAddressButtonShow = false

  toggleItems = [
    'educations',
    'experiences',
    'address',
    'skills',
    'languages',
    'activity-certification'
  ];



  ngOnInit(): void {
    this.allItemsToggle(false);
    this.readAllUserDataFromDB()
  }
  readAllUserDataFromDB(): void {
    const userData = this.userService.fetchUserDetailsByUserName('prasadsanap8149@gmail.com')
    userData.subscribe(
      response => {
        if (response.statusCode = 1001) {
          console.log('Responce From DB :-', response)
          this.user = response
          if (this.user.language)
            this.languages = this.user.language
          if (this.user.skill)
            this.skills = this.user.skill
          if (this.user.experience)
            this.experienceList = this.user.experience
          if (this.user.education)
            this.educationList = this.user.education
          if (this.user.activityCertification)
            this.activityCertificationList = this.user.activityCertification
          if (this.user.address) {

            this.address = this.user.address
            if (this.address.addressId != null && this.address.addressType != null) {
              this.isAddressButtonShow = true
              this.isAddressPresent = false
            }
          }
        } else {
          console.log('This is Failed:- ', response.status)
        }
      },
      error => {
        console.log('This is error:- ', error)
      }
    )
  }
  //Profile managed
  drawerHide() {
    console.log("DrawerHide")
    this.hideDrawer = false
    this.arrowIcon = 'arrow_forward'
    this.allItemsToggle(false);
    return;
  }

  allItemsToggle(flag: boolean) {
    this.isAddressClick = this.isEducationClick =
      this.isExpereinceClick =
      this.isLanguageClick =
      this.isSkillClick = this.isActivityCertificationClick = this.isPersonalDetailsClick = flag
    return;
  }

  showDrawer() {
    if (this.hideDrawer) {
      this.hideDrawer = false
      this.arrowIcon = 'arrow_forward'
      return;
    }
    this.hideDrawer = true
    this.arrowIcon = 'arrow_back'
    return;
  }


  showAndHideToggle(caseValue: String) {
    this.allItemsToggle(true);
    switch (caseValue) {
      case this.toggleItems[0]:
        this.isEducationClick = !this.isEducationClick;
        break;
      case this.toggleItems[1]:
        this.isExpereinceClick = !this.isExpereinceClick;
        break;
      case this.toggleItems[2]:
        this.isAddressClick = !this.isAddressClick;
        break;
      case this.toggleItems[3]:
        this.isSkillClick = !this.isSkillClick;
        break;
      case this.toggleItems[4]:
        this.isLanguageClick = !this.isLanguageClick;
        break;
      case this.toggleItems[5]:
        this.isActivityCertificationClick = !this.isActivityCertificationClick;
        break;

    }

  }

  formatLabel(value: number): string {
    if (value >= 1000) {
      return Math.round(value / 1000) + 'k';
    }

    return `${value}`;
  }



// -----------------------------------------A D D R E S S-----------------------------

  // Update existing address
  openPopUpUpdateAddress(address: Address) {
    const _popUpAddressData = this.dialog.open(AddressDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { address: address, user: this.user, isUpdateCreateFlag: true }
    });
    _popUpAddressData.afterClosed().subscribe(item => {
      // This is calling data from db after update address data
      console.log(item.value)
  
    })
  }

  //Adding new address
  openPopUpNewAddress() {
    const _popUpAddressData = this.dialog.open(AddressDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpAddressData.afterClosed().subscribe(item => {

      // This is calling data from db after update address data
      console.log(item.value)
      this.readAllUserDataFromDB()
    })
  }

  //Delete address
  openAddressDeleteAlertPopUp(address: Address) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'address' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteAddress(address)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  deleteAddress(address: Address) {
    const apiRequestBody = {
      "user": this.user,
      "address": address,
      "language": null
    }
    console.log('Deleting address :', apiRequestBody)
    const result = this.userService.deleteAddress(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }




  // ----------------------------------E X P E R I E N C E--------------------------------

  openPopUpUpdateExperience(experience: Experience) {
    const _popUpExperienceData = this.dialog.open(ExperienceDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { experience: experience, user: this.user, isUpdateCreateFlag: true }
    });
    _popUpExperienceData.afterClosed().subscribe(item => {
      // This is calling data from db after update Experience data
      console.log(item.value)
      this.readAllUserDataFromDB()
    })
  }

  //Adding new Experience
  openPopUpNewExperience() {
    const _popUpExperienceData = this.dialog.open(ExperienceDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpExperienceData.afterClosed().subscribe(item => {

      // This is calling data from db after update skill data
      console.log(item.value)
      this.readAllUserDataFromDB()
    })
  }

  //Delete Experience
  openExperienceDeleteAlertPopUp(experience: Experience) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'experience' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteExperience(experience)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  deleteExperience(experience: Experience) {
    const apiRequestBody = {
      "user": this.user,
      "experience": experience,
      "language": null,
      "skill": null
    }
    console.log('Deleting Experience :', apiRequestBody)
    const result = this.userService.deleteExperience(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }



  // -----------------------------------------S K I L L-----------------------------

  // Update existing skill
  openPopUpUpdateSkill(skill: Skill) {
    const _popUpSkillData = this.dialog.open(SkillDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { skill: skill, user: this.user, isUpdateCreateFlag: true }
    });
    _popUpSkillData.afterClosed().subscribe(item => {

      // This is calling data from db after update skill data
      console.log(item.value)
      this.skills.forEach(
        skill => {
          if (skill.skillId === item.value.skillId) {
            skill.skillTitle = item.value.skillTitle;
            skill.level = item.value.level
          }
        }
      )


    })
  }

  //Adding new skill
  openPopUpNewSkill() {
    const _popUpSkillData = this.dialog.open(SkillDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpSkillData.afterClosed().subscribe(item => {

      // This is calling data from db after update skill data
      console.log(item.value)
      this.skills.forEach(
        skill => {
          if (skill.skillId === item.value.skillId) {
            skill.skillTitle = item.value.skillTitle;
            skill.level = item.value.level
          }
        }

      )
      this.readAllUserDataFromDB()
    })
  }

  //Delete skill
  openSkillDeleteAlertPopUp(skill: Skill) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'skill' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteSkill(skill)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  deleteSkill(skill: Skill) {
    const apiRequestBody = {
      "user": this.user,
      "skill": skill,
      "language": null
    }
    console.log('Deleting skill :', apiRequestBody)
    const result = this.userService.deleteSkill(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }


  // -----------------------------------------L A N G U A G E-----------------------------

  openPopUpUpdateLanuage(language: Language) {
    const _popUpSkillData = this.dialog.open(LanguageDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { language: language, user: this.user, isUpdateCreateFlag: true }
    });
    _popUpSkillData.afterClosed().subscribe(item => {

      // This is calling data from db after update skill data
      console.log(item.value)
      this.languages.forEach(
        language => {
          if (language.languageId === item.value.languageId) {
            language.langTitle = item.value.langTitle;
            language.level = item.value.level
          }
        }
      )
    })
  }

  //Adding new language
  openPopUpNewLanguage() {
    const _popUpSkillData = this.dialog.open(LanguageDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpSkillData.afterClosed().subscribe(item => {

      // This is calling data from db after update skill data
      console.log(item.value)
      this.languages.forEach(
        language => {
          if (language.languageId === item.value.languageId) {
            language.langTitle = item.value.langTitle;
            language.level = item.value.level
          }
        }
      )
      this.readAllUserDataFromDB()
    })
  }

  //Delete language
  openLanguageDeleteAlertPopUp(language: Language) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'language' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteLanguage(language)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  //Delete language
  deleteLanguage(language: Language) {
    const apiRequestBody = {
      "user": this.user,
      "language": language
    }
    console.log('Deleting language :', apiRequestBody)
    const result = this.userService.deleteLanguage(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }



  // --------------------------A C T I V I T Y & C E R T I F I C A T I O N------------------

  // Update existing activity-csrtification
  openPopUpUpdateActivityCertification(activityCertfication: ActivityCertification) {
    const _popUpActivityCertificationData = this.dialog.open(ActivityCertificationDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { activityCertfication: activityCertfication, user: this.user, isUpdateCreateFlag: true }
    });
    _popUpActivityCertificationData.afterClosed().subscribe(item => {

      // This is calling data from db after update ActivityCertificationData 
      this.readAllUserDataFromDB()

    })
  }

  //Adding new ActivityCertification
  openPopUpNewActivityCertification() {
    const _popUpActivityCertificationData = this.dialog.open(ActivityCertificationDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpActivityCertificationData.afterClosed().subscribe(item => {

      // This is calling data from db after update ActivityCertification
      this.readAllUserDataFromDB()
    })
  }


  //Delete Activity
  openActivityCertificationDeleteAlertPopUp(activityCertfication: ActivityCertification) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'activityCertification' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteActivityCertification(activityCertfication)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  deleteActivityCertification(activityCertfication: ActivityCertification) {
    const apiRequestBody = {
      "user": this.user,
      "activityCertification": activityCertfication,
      "language": null,
      "skill": null
    }
    console.log('Deleting ActivityCertification :', apiRequestBody)
    const result = this.userService.deleteActivityCertification(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }



  // -------------------------------------- E D U C A T I O N --------------------------------

  openPopUpUpdateEducation(education: Education) {
    const _popUpEducationData = this.dialog.open(EducationDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { education: education, user: this.user, isUpdateCreateFlag: true }
    });

    _popUpEducationData.afterClosed().subscribe(item => {

      // This is calling data from db after update education data
      console.log(item.value)
      this.readAllUserDataFromDB()
    })
  }

  //Adding new education
  openPopUpNewEducation() {
    const _popUpEducationData = this.dialog.open(EducationDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { user: this.user, isUpdateCreateFlag: false }
    });
    _popUpEducationData.afterClosed().subscribe(item => {

      // This is calling data from db after update education data
      console.log(item.value)
      this.readAllUserDataFromDB()
    })
  }

  //Delete education
  openEducationDeleteAlertPopUp(education: Education) {
    const _popUpDeleteData = this.dialog.open(DeleteAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'education' }
    });
    _popUpDeleteData.afterClosed().subscribe(item => {
      if (item) {
        this.deleteEducation(education)
      } else {
        console.log("Not Deleting..!")
      }
    })
  }

  //Delete education
  deleteEducation(education: Education) {
    const apiRequestBody = {
      "user": this.user,
      "education": education
    }
    console.log('Deleting education :', apiRequestBody)
    const result = this.userService.deleteEducation(apiRequestBody)
    result.subscribe(response => {
      console.log(response)
      this.readAllUserDataFromDB()
    },
      error => {
        console.log(error)
      })
  }


}

