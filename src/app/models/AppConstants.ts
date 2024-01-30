export class AppConstants {
    static BRAND_NAME = "THE PERFECT DETAILS";
    static IS_LOGGED_IN: boolean;
    static FAILED_CODE = 1004;
    static WRONG_CODE = 1004;
    static UPDATED_CODE = 1003;
    static SUCCESS_CODE = 1001;
    static CREATED_CODE = 1002;
    static EXPIRED_CODE = 1009;
    static WRONG_PASSWORD_CODE = 1006;
    static FIRST_NAME_ERROR = 'PLEASE PROVIDE FIRST NAME.'
    static LAST_NAME_ERROR = 'PLEASE PROVIDE LAST NAME.'
    static OTP_6_DIGIT_ERROR = 'PLEASE ENTER 6 DIGIT OTP.'
    static OTP_ERROR = 'PLEASE ENTER OTP.'
    static EMAIL_ERROR = 'PLEASE ENTER EMAIL.';
    static PASSWORD_ERROR = 'PLEASE ENTER PASSWORD.';
    static PASSWORD_EMPTY_ERROR = 'PASSWORD IS BLANK.';
    static PASSWORD_LENGTH_ERROR = 'PLEASE ENTER AT LEASE 8 DIGIT PASSWORD.';
    static PASSWORD_SAME_ERROR = 'PLEASE ENTER SAME PASSWORD.';
    static LOGIN_PAGE = 'signin';
    static LOGIN_HOME_PAGE = 'login';
    static SIGNUP_PAGE = 'signup';
    static HOME_PAGE = 'home';
    static FORGET_PAGE = 'forget';
    static EXPERIENCE_PAGE = 'experience';


    //MESSAGES
    public static OTP_VERIFIED = "YOUR OTP IS VERIFIED";
    public static USER_CREATED = "USER IS CREATED SUCCESSFULLY";
    public static OTP_NOT_SEND = "YOUR OTP NOT SEND";
    public static OTP_NOT_VERIFIED = "YOUR OTP NOT VERIFIED";
    public static OTP_SEND = "YOUR OTP IS SEND";
    public static VALID_EMAIL = "PLEASE ENTER VALID EMAIL";
    public static OTP_IS_EXPIRE = "YOUR OTP IS EXPIRE. PLEASE RE-SEND AGAIN";
    public static EXPIRE_MESSAGE = "THIS OTP IS NOT ALLOW TO VALIDATE. PLEASE ENTER CORRECT OTP";
    public static UNKNOWN_USER = "PLEASE ENTER VALID USERNAME";
    public static PASSWORD_NOT_MATCH = "PLEASE ENTER CORRECT PASSWORD";
    public static LOGIN_SUCCESS = "YOU LOGIN SUCCESSFULLY";


    //SAVE MESSAGE
    public static ADDRESS_SAVE = "YOUR ADDRESS IS SAVE";
    public static SKILL_SAVE = "YOUR SKILL IS SAVE";
    public static ACT_CERT_SAVE = "YOUR ACTIVITY OR CERTIFICATION IS SAVE";
    public static LANGUAGE_SAVE = "YOUR LANGUAGE IS SAVE";
    public static EDUCATION_SAVE = "YOUR EDUCATION IS SAVE";


    //NOT SAVE MESSAGE
    public static ADDRESS_NOT_SAVE = "YOUR ADDRESS IS NOT SAVE";
    public static LANGUAGE_NOT_SAVE = "YOUR LANGUAGE IS NOT SAVE";
    public static SKILL_NOT_SAVE = "YOUR SKILL IS NOT SAVE";
    public static ACT_CERT_NOT_SAVE = "YOUR ACTIVITY OR CERTIFICATION IS NOT SAVE";
    public static EDUCATION_NOT_SAVE = "YOUR EDUCATION IS NOT SAVE";


    //UPDATE MESSAGE
    public static EDUCATION_UPDATED = "YOUR EDUCATION IS UPDATED";
    public static ADDRESS_UPDATE = "YOUR ADDRESS IS UPDATED";
    public static EXPERIENCE_UPDATED = "YOUR EXPERIENCE IS UPDATED";
    public static LANGUAGE_UPDATED = "YOUR LANGUAGE IS UPDATED";
    public static ACT_CERT_UPDATED = "YOUR ACTIVITY OR CERTIFICATION IS UPDATED";
    public static SKILL_UPDATED = "YOUR SKILL IS UPDATED";
    public static PASSWORD_UPDATED = "YOUR PASSWORD IS UPDATED";



    //NOT UPDATE MESSAGE
    public static EDUCATION_NOT_UPDATED = "YOUR EDUCATION IS NOT UPDATED";
    public static EXPERIENCE_NOT_UPDATED = "YOUR EXPERIENCE IS NOT UPDATED";
    public static LANGUAGE_NOT_UPDATED = "YOUR LANGUAGE IS NOT UPDATED";
    public static ACT_CERT_NOT_UPDATED = "YOUR ACTIVITY OR CERTIFICATION IS NOT UPDATED";
    public static SKILL_NOT_UPDATED = "YOUR SKILL IS NOT UPDATED";


    //STATUS MESSAGE
    public static SUCCESS = "SUCCESS";
    public static FAILED = "FAILED";
    public static EXPIRED = "EXPIRED";
    public static CREATED = "CREATED";
    public static WRONG_PASSWORD = "WRONG PASSWORD";
    public static UPDATED = "UPDATED";

    //FINAL VALUES
    public static OTP_EXPIRED = "EXPIRED-OTP";
    public static DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";


    public static EMPLOYMENT_TYPE = [
        { value: 'Full-time', viewValue: 'Full-time' },
        { value: 'Permanent', viewValue: 'Permanent' },
        { value: 'Part-time', viewValue: 'Part-time' },
        { value: 'Apprenticeship', viewValue: 'Apprenticeship' },
        { value: 'Traineeship', viewValue: 'Traineeship' },
        { value: 'Internship', viewValue: 'Internship' },
        { value: 'Contract', viewValue: 'Contract' },
        { value: 'Casual', viewValue: 'Casual' },
        { value: 'Probation', viewValue: 'Probation' },
        { value: 'Seasonal', viewValue: 'Seasonal' },
        { value: 'Leased', viewValue: 'Leased' },
        { value: 'Contingent', viewValue: 'Contingent' }
    ];

    public static EDUCATION_MODE = ["FULL-TIME","PART-TIME"];
    public static ADDRESS_TYPE = ["Permanent","Temporary"];
    public static WORKING_MODE = [
        { value: 'In-Person', viewValue: 'In-Person' },
        { value: 'On-site', viewValue: 'On-site' },
        { value: 'Hybrid', viewValue: 'Hybrid' },
        { value: 'Remote', viewValue: 'Remote' }
    ]
    public static LEVELS = [
    
        { value: 'Intermediate', viewValue: 'Intermediate' },
        { value: 'Beginner', viewValue: 'Beginner' },
        { value: 'Proficient', viewValue: 'Proficient' },
        { value: 'Fluent', viewValue: 'Fluent' },
        { value: 'Advanced', viewValue: 'Advanced' },
        { value: 'Basic', viewValue: 'Basic' },
        { value: 'Native', viewValue: 'Native' }
    ]

    public static MONTHS = [
        { value: 'JAN', viewValue: 'JAN' },
        { value: 'FEB', viewValue: 'FEB' },
        { value: 'MAR', viewValue: 'MAR' },
        { value: 'APR', viewValue: 'APR' },
        { value: 'MAY', viewValue: 'MAY' },
        { value: 'JUN', viewValue: 'JUN' },
        { value: 'JUL', viewValue: 'JUL' },
        { value: 'AUG', viewValue: 'AUG' },
        { value: 'SEP', viewValue: 'SEP' },
        { value: 'OCT', viewValue: 'OCT' },
        { value: 'NOV', viewValue: 'NOV' },
        { value: 'DEC', viewValue: 'DEC' }
      ];


    constructor() { }
}