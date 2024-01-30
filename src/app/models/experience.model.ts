import { Achievements } from "./achievement.model";
import { Project } from "./project.model";

export interface Experience {
    experienceId : number,
    title : string,
    employmentType : string,
    companyName : string,
    location : string,
    workingMode : string,
    startMonth : string,
    startYear : number,
    endMonth : string,
    endYear : number,
    presentCompany:boolean,
    skills : string,
    description :string,
    achievements : Achievements[],
    projects : Project[]
}