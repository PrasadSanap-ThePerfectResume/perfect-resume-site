import { Injectable } from '@angular/core';
import baseUrl from './server-service';
import { HttpClient } from '@angular/common/http';
import { Response } from '../models/response.model';
import { Skill } from '../models/skill.model';
import { User } from '../models/user.model';
import { Language } from '../models/language.model';

@Injectable({
  providedIn: 'root'
})
export class UserserviceService {


  response = Response;
  constructor(private http: HttpClient) { }
  isLoggedIn: boolean = false;
  isLoggedInValidating(value: boolean) {
    this.isLoggedIn = value;
    return this.isLoggedIn;
  }

  loginUser(formData: any) {
    return this.http.post<any>(`${baseUrl}/user/login`, formData);
  }

  // Fetching user all details using username
  fetchUserDetailsByUserName(username: string) {
    return this.http.get<any>(`${baseUrl}/user/` + username);
  }

  // -----------------------------------------A D D R E S S-------------------------------------------

  // Update Address using AddressId and User
  updateUserSingleAddress(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/address/update1Address`, apiRequestBody);
  }

  // Creating Address
  createUserAddress(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/address/create`, apiRequestBody);
  }


  // Delete Address using AddressId and User
  deleteAddress(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/address/delete`, apiRequestBody);
  }


  // ----------------------------------------- S K I L L-------------------------------------------


  // Update skill using skillId and User
  updateUserSingleSkill(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/skill/update1Skill`, apiRequestBody);
  }

  // Creating skill
  createUserSkill(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/skill/create`, apiRequestBody);
  }


  // Delete skill using skillId and User
  deleteSkill(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/skill/delete`, apiRequestBody);
  }



  // ----------------------------------------- L A N G U A G E-------------------------------------------

  // Create language
  createUserlanguage(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/language/create`, apiRequestBody);
  }

  // Update Language using languageId and User
  updateUserSinglelanguage(apiRequestBody: { user: User; language: Language }) {
    return this.http.post<any>(`${baseUrl}/user/language/update1Language`, apiRequestBody);
  }

  // Delete language using languageId and User
  deleteLanguage(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/language/delete`, apiRequestBody);
  }


  // ----------------------------------------- L A N G U A G E-------------------------------------------

  // Create language
  createUserActivityCertification(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/activity-certificate/create`, apiRequestBody);
  }

  // Update Language using languageId and User
  updateUserSingleActivityCertification(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/activity-certificate/update1ActivityCertificate`, apiRequestBody);
  }

  // Delete language using languageId and User
  deleteActivityCertification(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/activity-certificate/delete`, apiRequestBody);
  }


  // ----------------------------------------- E D U C A T I O N-------------------------------------------

  // Create language
  createUserEducation(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/education/create`, apiRequestBody);
  }

  // Update Language using languageId and User
  updateUserSingleEducation(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/education/update1Education`, apiRequestBody);
  }

  // Delete language using languageId and User
  deleteEducation(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/education/delete`, apiRequestBody);
  }


  // ------------------------------------ E X P E R I E N C E ----------------------------------------

  // Create Experience
  createUserExperience(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/experience/create`, apiRequestBody);
  }

  // Update Experience using ExperienceID and User
  updateUserSingleExperience(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/experience/update1Experience`, apiRequestBody);
  }

  // Delete Experience using ExperienceId and User
  deleteExperience(apiRequestBody: any) {
    return this.http.post<any>(`${baseUrl}/user/experience/delete`, apiRequestBody);
  }


  verifyUserEmail(formData: any) {
    return this.http.post<any>(`${baseUrl}/user/sendOtp`, formData).subscribe(
      response => {
        if (response.statusCode = 1001) {
          console.log('This is responce:- ', response.message)

        } else {
          console.log('This is Failed:- ', response.status)
        }
      },
      error => {
        this.response = error
        console.log('This is error:- ', error)
      }
    )
  }

  verifyUserOtp(formData: any) {
    this.http.post<any>(`${baseUrl}/user/verifyOtp`, formData).subscribe(
      response => {
        console.log('This is responce:- ', response)
      },
      error => {
        console.log('This is error:- ', error)
      }
    )
  }
}
