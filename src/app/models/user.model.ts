
import { Address } from "./address.model";
import { Experience } from "./experience.model";
import { Skill } from "./skill.model";
import { Language } from "./language.model";
import { Education } from "./education.model";
import { ActivityCertification } from "./activity-certification.model";

export class User {
    id  !: number;
    firstname !: string;
    lastname !: string;
    username !: string;
    otp !: string;
    password !: string;
    confirmPassword !: string;
    address !: Address;
    education !: Education[];
    experience !: Experience[];
    language !: Language[];
    skill !: Skill[];
    activityCertification !: ActivityCertification[];

}