import { Language } from "./language.model";
import { Skill } from "./skill.model";
import { User } from "./user.model";

export interface AppRequestBody {
    user: User;
    skill:Skill;
    language:Language
}