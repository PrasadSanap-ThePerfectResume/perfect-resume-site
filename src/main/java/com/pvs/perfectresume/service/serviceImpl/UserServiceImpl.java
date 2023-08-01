package com.pvs.perfectresume.service.serviceImpl;

import com.pvs.perfectresume.PerfectResumeApplication;
import com.pvs.perfectresume.constants.AppConstants;
import com.pvs.perfectresume.model.*;
import com.pvs.perfectresume.repository.*;
import com.pvs.perfectresume.service.EmailService;
import com.pvs.perfectresume.service.UserService;
import com.pvs.perfectresume.util.OTPGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPValidationRepository otpValidationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ActivityCertificationRepository activityCertificationRepository;

    @Autowired
    private EmailService emailService;

    private ApiResponseBody apiResponseBody;

    private Pattern pattern = Pattern.compile("[!~?/#$\\{%^&*(\\]}|=)\\[]");

    private Logger logger = LoggerFactory.getLogger(PerfectResumeApplication.class);
    private LocalDateTime localDateTime;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(AppConstants.DATE_TIME_FORMAT);
    private Timestamp timestamp;


    @Override
    public String show() {
        return "HELLO PRASAD";
    }

    @Override
    public ApiResponseBody sendOtp(ApiRequestBody apiRequestBody) {
        logger.debug("Start of sendOtp()");
        apiResponseBody = new ApiResponseBody();
        try {
            if (pattern.matcher(apiRequestBody.getUser().getUsername()).find()) {
                apiResponseBody.setMessage(AppConstants.VALID_EMAIL);
                apiResponseBody.setStatus(AppConstants.FAILED);
                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                return apiResponseBody;
            }

            int generatedOTP = OTPGeneration.getOTP();
            //Check first is user already present in database
            User user = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            if (user == null) {
                //Save user first into table
                userRepository.save(apiRequestBody.getUser());
            }
            //Fetch user from table with id.
            user = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            OTPValidation otpValidation = otpValidationRepository.findByUsername(apiRequestBody.getUser().getUsername());
            if (otpValidation == null) {
                //Creating new record.
                otpValidation = new OTPValidation();
                otpValidation.setUser(user);
                otpValidation.setUsername(user.getUsername());
            }
            timestamp = Timestamp.from(Instant.now());
            otpValidation.setOtpGenerateDT(timestamp);
            otpValidation.setOtp(String.valueOf(generatedOTP));
            otpValidation.setVerify(false);
            //Save user with otp into table
            otpValidationRepository.save(otpValidation);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setEmailBody("This is your OTP::" + generatedOTP);
            emailDetails.setRecipient(user.getUsername());
            emailDetails.setSubject("OTP Verification");
            String emailStatus = emailService.sendEmail(emailDetails);
            logger.info("EMAIL STATUS::{}", emailStatus);
            apiResponseBody.setMessage(AppConstants.OTP_SEND);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);

        } catch (Exception e) {
            apiResponseBody.setMessage(AppConstants.OTP_NOT_SEND);
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            logger.error("Exception in sendOtp::{}", e.getMessage());

        }
        logger.debug("End of sendOtp()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody verifyOtp(OTPValidation otpValidation) {
        logger.debug("Start of verifyOtp()");
        try {
            apiResponseBody = new ApiResponseBody();
            OTPValidation presentOtpValidation = otpValidationRepository.findByUsername(otpValidation.getUsername());
            if (presentOtpValidation.getOtp().equals(AppConstants.OTP_EXPIRED) || otpValidation.getOtp().equals(AppConstants.OTP_EXPIRED)) {
                if (otpValidation.getOtp().equals(AppConstants.OTP_EXPIRED))
                    apiResponseBody.setMessage(AppConstants.EXPIRE_MESSAGE);
                else
                    apiResponseBody.setMessage(AppConstants.OTP_IS_EXPIRE);

                apiResponseBody.setStatus(AppConstants.EXPIRED);
                apiResponseBody.setStatusCode(AppConstants.EXPIRED_CODE);
            } else if (presentOtpValidation.getOtp().equals(otpValidation.getOtp())) {
                //Updating verify otp
                presentOtpValidation.setVerify(true);
                otpValidationRepository.save(presentOtpValidation);
                //Save date time when user validate otp
                User user = userRepository.findByUsername(otpValidation.getUsername());
                timestamp = Timestamp.from(Instant.now());
                user.setAccountCreateDT(timestamp);
                userRepository.save(user);
                apiResponseBody.setStatus(AppConstants.SUCCESS);
                apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
                apiResponseBody.setMessage(AppConstants.OTP_VERIFIED);
            } else {
                apiResponseBody.setStatus(AppConstants.FAILED);
                apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
                apiResponseBody.setMessage(AppConstants.OTP_NOT_VERIFIED);
            }
        } catch (Exception e) {
            logger.error("Exception in verifyOtp::{}", e.getMessage());
        }
        logger.debug("End of verifyOtp()");
        return apiResponseBody;
    }


    @Override
    public User findUser(String s) {
        User user = userRepository.findByUsername(s);
        return user;
    }

    @Override
    public ApiResponseBody saveUser(ApiRequestBody apiRequestBody) {
        apiResponseBody = new ApiResponseBody();
        User user = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
        user.setPassword(apiRequestBody.getUser().getPassword());
        timestamp = Timestamp.from(Instant.now());
        user.setAccountCreateDT(timestamp);
        userRepository.save(user);
        apiResponseBody.setMessage(AppConstants.USER_CREATED);
        apiResponseBody.setStatus(AppConstants.CREATED);
        apiResponseBody.setStatusCode(AppConstants.CREATED_CODE);
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody saveAddress(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveAddress()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Address presentAddress = addressRepository.findByUser(presentUser);
            if (presentAddress == null) {
                Address newAddress = apiRequestBody.getAddress();
                newAddress.setUser(presentUser);
                addressRepository.save(newAddress);
                apiResponseBody.setMessage(AppConstants.ADDRESS_SAVE);
            } else {
                if (!apiRequestBody.getAddress().getAddressType().equals("") && apiRequestBody.getAddress().getAddressType() != null)
                    presentAddress.setAddressType(apiRequestBody.getAddress().getAddressType());
                if (!apiRequestBody.getAddress().getCity().equals("") && apiRequestBody.getAddress().getCity() != null)
                    presentAddress.setCity(apiRequestBody.getAddress().getCity());
                if (!apiRequestBody.getAddress().getDistrict().equals("") && apiRequestBody.getAddress().getDistrict() != null)
                    presentAddress.setDistrict(apiRequestBody.getAddress().getDistrict());
                if (!apiRequestBody.getAddress().getCountry().equals("") && apiRequestBody.getAddress().getCountry() != null)
                    presentAddress.setCountry(apiRequestBody.getAddress().getCountry());
                if (!apiRequestBody.getAddress().getPin().equals("") && apiRequestBody.getAddress().getPin() != null)
                    presentAddress.setPin(apiRequestBody.getAddress().getPin());
                if (!apiRequestBody.getAddress().getState().equals("") && apiRequestBody.getAddress().getState() != null)
                    presentAddress.setState(apiRequestBody.getAddress().getState());
                if (!apiRequestBody.getAddress().getTown().equals("") && apiRequestBody.getAddress().getTown() != null)
                    presentAddress.setTown(apiRequestBody.getAddress().getTown());
                apiResponseBody.setMessage(AppConstants.ADDRESS_UPDATE);
                addressRepository.save(presentAddress);
            }
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveAddress::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ADDRESS_NOT_SAVE);
        }
        logger.debug("End of saveAddress()");
        return apiResponseBody;
    }

/*
    @Override
    public ApiResponseBody saveEducation(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Education education = apiRequestBody.getEducation();
            education.setUser(presentUser);
            educationRepository.save(education);
            apiResponseBody.setMessage(AppConstants.EDUCATION_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveEducation::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_SAVE);
        }
        logger.debug("End of saveEducation()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateEducation(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateEducation()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Education> education=presentUser.getEducation();
            education.stream().forEach(e->{
              if(e.getEducationId()==apiRequestBody.getEducation().getEducationId()){
                  if(!apiRequestBody.getEducation().getUniversity().equals("") && apiRequestBody.getEducation().getUniversity() !=null)
                    e.setUniversity(apiRequestBody.getEducation().getUniversity());
                  if(!apiRequestBody.getEducation().getCollege().equals("") && apiRequestBody.getEducation().getCollege() !=null)
                      e.setCollege(apiRequestBody.getEducation().getCollege());
                  if(!apiRequestBody.getEducation().getStream().equals("") && apiRequestBody.getEducation().getStream() !=null)
                      e.setStream(apiRequestBody.getEducation().getStream());
                  if(!apiRequestBody.getEducation().getStartYear().equals("") && apiRequestBody.getEducation().getStartYear() !=null)
                      e.setStartYear(apiRequestBody.getEducation().getStartYear());
                  if(!apiRequestBody.getEducation().getEndYear().equals("") && apiRequestBody.getEducation().getEndYear() !=null)
                      e.setEndYear(apiRequestBody.getEducation().getEndYear());
                  if(!apiRequestBody.getEducation().getStartMonth().equals("") && apiRequestBody.getEducation().getStartMonth() !=null)
                      e.setStartMonth(apiRequestBody.getEducation().getStartMonth());
                  if(!apiRequestBody.getEducation().getEndMonth().equals("") && apiRequestBody.getEducation().getEndMonth() !=null)
                      e.setEndMonth(apiRequestBody.getEducation().getEndMonth());
                  if(!apiRequestBody.getEducation().getMark().equals("") && apiRequestBody.getEducation().getMark() !=null)
                      e.setMark(apiRequestBody.getEducation().getMark());
                  if(!apiRequestBody.getEducation().getEducationType().equals("") && apiRequestBody.getEducation().getEducationType() !=null)
                      e.setEducationType(apiRequestBody.getEducation().getEducationType());
                  educationRepository.save(e);
                  apiResponseBody.setMessage(AppConstants.EDUCATION_UPDATED);
                  apiResponseBody.setStatus(AppConstants.SUCCESS);
                  apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
              }
            });
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateEducation::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_UPDATED);
        }
        logger.debug("End of updateEducation()");
        return apiResponseBody;
    }
*/

/*    @Override
    public ApiResponseBody saveExperience(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            Experience experience = apiRequestBody.getExperience();
            experience.setUser(presentUser);
            final Experience saveExperience = experienceRepository.save(experience);
            List<Achievement> achievement = experience.getAchievements();
            achievement.stream().forEach(e -> {
                e.setExperience(saveExperience);
            });
            achievementRepository.saveAll(achievement);
            apiResponseBody.setMessage(AppConstants.EDUCATION_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EDUCATION_NOT_SAVE);
        }
        logger.debug("End of saveExperience()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateExperience(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateExperience()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Experience> experience=presentUser.getExperience();
            experience.stream().forEach(e->{
                if(e.getExperienceId()==apiRequestBody.getExperience().getExperienceId()){
                    if(!apiRequestBody.getExperience().getTitle().equals("") && apiRequestBody.getExperience().getTitle() !=null)
                        e.setTitle(apiRequestBody.getExperience().getTitle());
                    if(!apiRequestBody.getExperience().getEmploymentType().equals("") && apiRequestBody.getExperience().getEmploymentType() !=null)
                        e.setEmploymentType(apiRequestBody.getExperience().getEmploymentType());
                    if(!apiRequestBody.getExperience().getCompanyName().equals("") && apiRequestBody.getExperience().getCompanyName() !=null)
                        e.setCompanyName(apiRequestBody.getExperience().getCompanyName());
                    if(!apiRequestBody.getExperience().getLocation().equals("") && apiRequestBody.getExperience().getLocation() !=null)
                        e.setLocation(apiRequestBody.getExperience().getLocation());
                    if(!apiRequestBody.getExperience().getLocationType().equals("") && apiRequestBody.getExperience().getLocationType() !=null)
                        e.setLocationType(apiRequestBody.getExperience().getLocationType());
                    if(!apiRequestBody.getExperience().getStartMonth().equals("") && apiRequestBody.getExperience().getStartMonth() !=null)
                        e.setStartMonth(apiRequestBody.getExperience().getStartMonth());
                    if(!apiRequestBody.getExperience().getStartYear().equals("") && apiRequestBody.getExperience().getStartYear() !=null)
                        e.setStartYear(apiRequestBody.getExperience().getStartYear());
                    if(!apiRequestBody.getExperience().getEndMonth().equals("") && apiRequestBody.getExperience().getEndMonth() !=null)
                        e.setEndMonth(apiRequestBody.getExperience().getEndMonth());
                    if(!apiRequestBody.getExperience().getEndYear().equals("") && apiRequestBody.getExperience().getEndYear() !=null)
                        e.setEndYear(apiRequestBody.getExperience().getEndYear());
                    if(!apiRequestBody.getExperience().getSkills().equals("") && apiRequestBody.getExperience().getSkills() !=null)
                        e.setSkills(apiRequestBody.getExperience().getSkills());
                    if(!apiRequestBody.getExperience().getDescription().equals("") && apiRequestBody.getExperience().getDescription() !=null)
                        e.setDescription(apiRequestBody.getExperience().getDescription());
                    //Achievement update section
                    List<Achievement> presentAchievement=e.getAchievements();
                    List<Achievement> requestAchievement= apiRequestBody.getExperience().getAchievements();
                    if(presentAchievement.size()==0){
                        requestAchievement.stream().forEach(a->{
                                a.setExperience(e);
                        });
                        achievementRepository.saveAll(requestAchievement);
                    }else{
                        presentAchievement.stream().forEach(a->{
                            //UPDATE EXISTING RECORDS
                            requestAchievement.stream().forEach(b->{
                                if(a.getAchievementId()==b.getAchievementId()) {
                                    if(!b.getAchievement().equals("") && b.getAchievement()!=null)
                                        a.setAchievement(b.getAchievement());
                                }
                            });
                            //ADD NEW RECORDS
                            requestAchievement.stream().forEach(b->{
                                if(b.getAchievementId()==null) {
                                    b.setExperience(e);
                                    achievementRepository.save(b);
                                }
                            });
                        });
                        achievementRepository.saveAll(presentAchievement);
                    }

                    //Project update section
                    List<Project> presentProject=e.getProject();
                    List<Project> requestProject= apiRequestBody.getExperience().getProject();
                    if(presentProject.size()==0){
                        requestProject.stream().forEach(a->{
                            a.setExperience(e);
                        });
                        projectRepository.saveAll(requestProject);
                    }else{
                        presentProject.stream().forEach(a->{
                            //UPDATE EXISTING RECORDS
                            requestProject.stream().forEach(b->{
                                if(a.getProjectId()==b.getProjectId()) {
                                    if(!b.getProjectTitle().equals("") && b.getProjectTitle()!=null)
                                        a.setProjectTitle(b.getProjectTitle());
                                    if(!b.getBackEndTech().equals("") && b.getBackEndTech()!=null)
                                        a.setBackEndTech(b.getBackEndTech());
                                    if(!b.getFrontEndTech().equals("") && b.getFrontEndTech()!=null)
                                        a.setFrontEndTech(b.getFrontEndTech());
                                }
                            });
                            //ADD NEW RECORDS
                            requestProject.stream().forEach(b->{
                                if(b.getProjectId()==null) {
                                    b.setExperience(e);
                                    projectRepository.save(b);
                                }
                            });
                        });
                        achievementRepository.saveAll(presentAchievement);
                    }

                    experienceRepository.save(e);
                    apiResponseBody.setMessage(AppConstants.EXPERIENCE_UPDATED);
                    apiResponseBody.setStatus(AppConstants.SUCCESS);
                    apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
                }
            });
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateExperience::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.EXPERIENCE_NOT_UPDATED);
        }
        logger.debug("End of updateExperience()");
        return apiResponseBody;
    }*/

    /**
     * ACTIVITY AND CERTIFICATION SECTION
     *
     * Saving Activity & Certification.
     *
     * Updating Activity & Certification.
     */


/*
    @Override
    public ApiResponseBody saveActivityCertification(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveActivityCertification()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<ActivityCertification> activityCertification = apiRequestBody.getActivityCertification();
            activityCertification.stream().forEach(e -> {
                e.setUser(presentUser);
            });
            activityCertificationRepository.saveAll(activityCertification);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveActivityCertification::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_SAVE);
        }
        logger.debug("End of saveActivityCertification()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateActivityCertification(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateActivityCertification()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<ActivityCertification> activityCertification=presentUser.getActivityCertification();
            activityCertification.stream().forEach(e->{
                List<ActivityCertification> newActivityCertification=apiRequestBody.getActivityCertification();
                newActivityCertification.stream().forEach(f->{
                    if(e.getActCerId()==f.getActCerId()) {
                        if (!f.getActCerTitle().equals("") && f.getActCerTitle() != null)
                            e.setActCerTitle(f.getActCerTitle());

                    }
                });
                newActivityCertification.stream().forEach(f->{
                    if(f.getActCerId()==null) {
                        f.setUser(presentUser);
                        activityCertificationRepository.save(f);
                    }
                });
            });
            activityCertificationRepository.saveAll(activityCertification);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateActivityCertification::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.ACT_CERT_NOT_UPDATED);
        }
        logger.debug("End of updateActivityCertification()");
        return apiResponseBody;
    }
*/

    /**
     * SKILL SECTION
     *
     * Saving Skill.
     *
     * Updating Skill.
     */

/*
    @Override
    public ApiResponseBody saveSkill(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Skill> skill = apiRequestBody.getSkill();
            skill.stream().forEach(e -> {
                e.setUser(presentUser);
            });
            skillRepository.saveAll(skill);
            apiResponseBody.setMessage(AppConstants.SKILL_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveSkill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_SAVE);
        }
        logger.debug("End of saveSkill()");
        return apiResponseBody;
    }

    @Override
    public ApiResponseBody updateSkill(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateSkill()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Skill> skill=presentUser.getSkill();
            skill.stream().forEach(e->{
                List<Skill> newSkill=apiRequestBody.getSkill();
                newSkill.stream().forEach(f->{
                    if(e.getSkillId()==f.getSkillId()) {
                        if (!f.getSkillTitle().equals("") && f.getSkillTitle() != null)
                            e.setSkillTitle(f.getSkillTitle());
                        if (f.getLevel() <= 100 && f.getLevel() >= 0)
                            e.setLevel(f.getLevel());
                    }
                });
                newSkill.stream().forEach(f->{
                    if(f.getSkillId()==null) {
                        f.setUser(presentUser);
                        skillRepository.save(f);
                    }
                });
            });
            skillRepository.saveAll(skill);
            apiResponseBody.setMessage(AppConstants.SKILL_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateSkill::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.SKILL_NOT_UPDATED);
        }
        logger.debug("End of updateSkill()");
        return apiResponseBody;
    }
*/

    /**
     * LANGUAGE SECTION
     *
     * Saving Language.
     *
     * Updating Language
     */
 /*   @Override
    public ApiResponseBody saveLanguage(ApiRequestBody apiRequestBody) {
        logger.debug("Start of saveLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Language> language = apiRequestBody.getLanguage();
            language.stream().forEach(e -> {
                e.setUser(presentUser);
            });
            languageRepository.saveAll(language);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_SAVE);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
        } catch (Exception e) {
            logger.error("Exception in saveLanguage::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_SAVE);
        }
        logger.debug("End of saveLanguage()");
        return apiResponseBody;
    }


    @Override
    public ApiResponseBody updateLanguage(ApiRequestBody apiRequestBody) {
        logger.debug("Start of updateLanguage()");
        try {
            apiResponseBody = new ApiResponseBody();
            User presentUser = userRepository.findByUsername(apiRequestBody.getUser().getUsername());
            List<Language> language=presentUser.getLanguage();
            language.stream().forEach(e->{
                List<Language> newLanguage=apiRequestBody.getLanguage();
                newLanguage.stream().forEach(f->{
                    if(e.getLanguageId()==f.getLanguageId()) {
                        if (!f.getLangTitle().equals("") && f.getLangTitle() != null)
                            e.setLangTitle(f.getLangTitle());
                        if (f.getLevel() <= 100 && f.getLevel() >= 0)
                            e.setLevel(f.getLevel());
                    }
                });
                newLanguage.stream().forEach(f->{
                    if(f.getLanguageId()==null) {
                        f.setUser(presentUser);
                        languageRepository.save(f);
                    }
                });
            });
            languageRepository.saveAll(language);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_UPDATED);
            apiResponseBody.setStatus(AppConstants.SUCCESS);
            apiResponseBody.setStatusCode(AppConstants.SUCCESS_CODE);
            return apiResponseBody;
        } catch (Exception e) {
            logger.error("Exception in updateLanguage::{}", e.getMessage());
            apiResponseBody.setStatus(AppConstants.FAILED);
            apiResponseBody.setStatusCode(AppConstants.FAILED_CODE);
            apiResponseBody.setMessage(AppConstants.LANGUAGE_NOT_UPDATED);
        }
        logger.debug("End of updateLanguage()");
        return apiResponseBody;
    }*/

    //("[a-zA-Z0-9]{6}","prasad1") -->true
}
