import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../models/user.model';
import { UserserviceService } from '../services/userservice.service';
import { AppRequestBody } from '../models/appRequestBody';
import { AppConstants } from '../models/AppConstants';
import { Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  signInForm !: FormGroup;
  hide = true;
  emailHint:string=""
  passwordHint:string=""
  currentUser !: User;
  appRequestBody !:AppRequestBody ;
  constructor(private formBuilder: FormBuilder,private userservice:UserserviceService,private routes:Router ) { }

  ngOnInit(): void {
    this.signInForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    const userData: User = this.signInForm.value as User;
    if(this.signInForm.invalid){
        if(userData.username==""){
          this.emailHint="Please enter email."
        }
        if(userData.password==""){
          this.passwordHint="Please enter password."
        }
    }
    else if (this.signInForm.valid) {
      this.currentUser=userData;
      console.log('User data:', this.currentUser);
      this.appRequestBody.user=this.currentUser;
      // Here, you can send the userData to your backend or perform other actions
      const responce=this.userservice.loginUser(this.appRequestBody); 
      responce.subscribe(
        response => {
          if(response.statusCode=1001){            
            console.log('This is responce:- ', response)
          
            if(AppConstants.FAILED==response.status){
              this.emailHint=response.message;
            }
            else if(AppConstants.WRONG_PASSWORD==response.status){
              this.passwordHint=response.message;
            }
            else{
              this.passwordHint=response.message;
              const pageName="profile"
              this.userservice.isLoggedInValidating(true)
              this.routes.navigate([`${[pageName]}`]);
            }
          
          }else{
            console.log('This is Failed:- ', response.status)
            this.passwordHint=response.message;
          }
        },
        error => {
          this.passwordHint = error
          console.log('This is error:- ', error)
        }
      )
    }
  }
}
