import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user.model';
import { FormBuilder, FormGroup, MinLengthValidator, Validators } from '@angular/forms';
import { UserserviceService } from '../services/userservice.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  currentUser: User = new User();
  textField: string = 'example-half-width'; // CSS classes or styles to be applied
  firstNameHint = "";
  lastNameHint = "";
  emailHint = "";
  otpHint = "";
  confirmPasswordHint = "";
  textSendOTP: String = "Send OTP";
  hide = true; //Password hide and show

  readOnly: boolean = false;// 
  readOnlyOTP: boolean = true;// 
  readOnlyPassword: boolean = true;

  isDisableSendOTP: boolean = false;// 
  isDisableVerifyOTP: boolean = true;// 
  isDisableCreateAccount: boolean = true;// 
  //Signup form building
  signupFormWithGmail !: FormGroup

  signupFormWithOTP !: FormGroup
  signupFormWithPassword !: FormGroup

  constructor(private router: Router, private formBuilder: FormBuilder,private userService:UserserviceService) { }

  ngOnInit(): void {
    this.signupFormWithGmail = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
    });

    this.signupFormWithOTP = this.formBuilder.group({
      otp: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
    });

    this.signupFormWithPassword = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]],
    });
  }

  sendOTP(): void {
    this.currentUser = this.signupFormWithGmail.value as User
    if (this.signupFormWithGmail.invalid) {

      this.validateUserFormData(this.currentUser)

    } else if (this.signupFormWithGmail.valid) {
      this.emailHint = "";
      // this.validateUserFormData(userData)

      //Set fields read only
      this.readOnly = true;

      //Un-Set OTP field read only
      this.readOnlyOTP = false;
      this.isDisableVerifyOTP = false;

      //calling countdown function
      this.startCountDown();

      console.log('User data:', this.currentUser);
      // Here, you can send the userData to your backend or perform other actions
      this.userService.verifyUserEmail(this.currentUser)
      console.log('DATA SEND');
      
    }
  }


  verifyOTP(): void {
    const userData: User = this.signupFormWithOTP.value as User;
    if (this.signupFormWithOTP.invalid) {
      if (userData.otp == "") {
        this.otpHint = "OTP required..!";
      } else if (userData.otp.length < 6) {
        this.otpHint = "Please enter valid OTP.";
      }
    }
    else if (this.signupFormWithOTP.valid) {
      this.currentUser.otp = userData.otp
      this.otpHint = "";

      //Set OTP field read only
      this.readOnlyOTP = true;
      this.isDisableVerifyOTP = true;
      this.readOnlyPassword = false;
      this.isDisableCreateAccount = false;
      this.isDisableSendOTP = true;

      console.log('User data:', this.currentUser);
      // Here, you can send the userData to your backend or perform other actions
      this.userService.verifyUserOtp(this.currentUser)
      console.log('DATA SEND');
    }
  }

  public confirmPassword = "";
  public disable = false;
  public sendOtpButton = false;

  varificationOTP = "";


  createAccount() {
    const userData: User = this.signupFormWithPassword.value as User;
    if (this.signupFormWithPassword.invalid) {
      if (userData.password == "") {
        this.confirmPasswordHint = "Please enter valid password.";
      } else if (userData.password != userData.confirmPassword) {
        this.confirmPasswordHint = "Please enter same password.";
      }
      else if (userData.password.length <= 7) {
        this.confirmPasswordHint = "Password length should more than 8.";
      }
    } else if (this.signupFormWithPassword.valid) {
      this.currentUser.password = userData.password
      this.confirmPasswordHint = "";
      console.log("User Data:", this.currentUser)
    }
  }

  startCountDown() {
    this.isDisableSendOTP = true;
    let seconds = 10;
    const interval = setInterval(() => {
      seconds--;
      this.textSendOTP = "Send OTP ( " + seconds + " )"
      if (seconds <= 0) {
        clearInterval(interval);
        this.isDisableSendOTP = false;
        this.textSendOTP = "Re-Send OTP"
      }
    }, 1000);
  }

  validateUserFormData(userData: User) {
    if (userData.firstname == "") {
      this.firstNameHint = "Enter first name.";
    } else {
      this.firstNameHint = "";
    }
    if (userData.lastname == "") {
      this.lastNameHint = "Enter last name.";
    } else {
      this.lastNameHint = "";
    }
    if (this.currentUser.username == "") {
      this.emailHint = "Enter valid email.";
    } else {
      this.emailHint = "";
    }
  }

}