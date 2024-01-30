import { Component, ViewChild } from '@angular/core';
import { AppConstants } from '../models/AppConstants';
import { UserserviceService } from '../services/userservice.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  BrandName=AppConstants.BRAND_NAME
  showDropDown=true;
  constructor(){}
}
