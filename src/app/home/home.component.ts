import { Component } from '@angular/core';
import { AppConstants } from '../models/AppConstants';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  BrandName=AppConstants.BRAND_NAME

  signInWithGoogle() {}

  createNewAccount() {
    // Implement account creation logic here using Firebase Authentication createUserWithEmailAndPassword method
  }
}
