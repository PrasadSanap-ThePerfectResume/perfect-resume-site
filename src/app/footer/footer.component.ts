import { Component } from '@angular/core';
import { AppConstants } from '../models/AppConstants';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {
  BrandName=AppConstants.BRAND_NAME
}
