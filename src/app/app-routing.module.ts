import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ProfileComponent } from './profile/profile.component';
import { ExperienceComponent } from './experience/experience.component';
import { CommingSoonComponent } from './comming-soon/comming-soon.component';
import { AuthGuard } from './auth/auth.guard';


const routes: Routes = [
  
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'signup',
    component: SignupComponent,
    pathMatch: 'full'
  }
  ,
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate:[AuthGuard],
    pathMatch: 'full',
    children:[
      {
        path: 'profile/experience',
        component:ExperienceComponent,
        pathMatch:'full'
      }
    ]
  }
  ,
  {
    path: 'experience',
    component: ExperienceComponent,
    pathMatch: 'full'
  }
  ,
  {
    path: 'coming-soon',
    component: CommingSoonComponent,
    pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
