import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http'
import { OAuthModule } from 'angular-oauth2-oidc';
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { HeaderComponent } from './header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { SignupComponent } from './signup/signup.component';
import { MatSelectModule } from '@angular/material/select';
import { MatGridListModule } from '@angular/material/grid-list';
import { FooterComponent } from './footer/footer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { ProfileComponent } from './profile/profile.component';
import { ExperienceComponent } from './experience/experience.component';
import { CommingSoonComponent } from './comming-soon/comming-soon.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatStepperModule } from '@angular/material/stepper';
import { MatBadgeModule } from '@angular/material/badge';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSliderModule } from '@angular/material/slider';
import { MatTableModule } from '@angular/material/table';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { ExperienceDialogComponent } from './dialog/experience-dialog/experience-dialog.component';
import { ExperienceDialogComponentOLD } from './experience-dialog/experience-dialog.component';
import { SkillDialogComponent } from './skill-dialog/skill-dialog.component';
import { LanguageDialogComponent } from './language-dialog/language-dialog.component';
import { DeleteAlertDialogComponent } from './delete-alert-dialog/delete-alert-dialog.component';
import { UpdateAlertDialogComponent } from './update-alert-dialog/update-alert-dialog.component';
import { ActivityCertificationDialogComponent } from './activity-certification-dialog/activity-certification-dialog.component';
import { EducationDialogComponent } from './education-dialog/education-dialog.component';
import { ProjectDialogComponent } from './dialog/project-dialog/project-dialog.component';
import { AchievementDialogComponent } from './dialog/achievement-dialog/achievement-dialog.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { AddressDialogComponent } from './dialog/address-dialog/address-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    HeaderComponent,
    SignupComponent,
    FooterComponent,
    ProfileComponent,
    ExperienceComponent,
    CommingSoonComponent,
    ExperienceDialogComponent,
    SkillDialogComponent,
    LanguageDialogComponent,
    DeleteAlertDialogComponent,
    UpdateAlertDialogComponent,
    ActivityCertificationDialogComponent,
    EducationDialogComponent,
    ExperienceDialogComponent,
    ExperienceDialogComponentOLD,
    ProjectDialogComponent,
    AchievementDialogComponent,
    AddressDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    OAuthModule.forRoot(),
    BrowserAnimationsModule,
    MatIconModule,
    MatDividerModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatSelectModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressBarModule,
    MatTabsModule,
    MatDialogModule,
    MatStepperModule,
    MatBadgeModule,
    MatTooltipModule,
    MatSliderModule,
    MatTableModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatCheckboxModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
