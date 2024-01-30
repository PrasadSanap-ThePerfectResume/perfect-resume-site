import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Skill } from 'src/app/models/skill.model';
import baseUrl from '../server-service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SkillServiceService {
  constructor(private http:HttpClient) { }
  GetSkillList(): Skill[]{
   return [
      {
        "skillId": 34,
        "skillTitle": "Java",
        "level": 60
      },
      {
        "skillId": 35,
        "skillTitle": "HTML",
        "level": 70
      },
        {
          "skillId": 36,
          "skillTitle": "Angular",
          "level": 50
        },
      {
        "skillId": 37,
        "skillTitle": "SQL",
        "level": 80
      }
    ]
  }

  getSkill(username:string):Observable<Skill[]>{
    return this.http.get<Skill[]>(`${baseUrl}/user/skill/`+username)
  }

}
