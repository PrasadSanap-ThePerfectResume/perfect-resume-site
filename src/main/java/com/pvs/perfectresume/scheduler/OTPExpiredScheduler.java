package com.pvs.perfectresume.scheduler;

import com.pvs.perfectresume.repository.OTPValidationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class OTPExpiredScheduler {
    private static final Logger logger = LoggerFactory.getLogger(OTPExpiredScheduler.class);
    @Autowired
    private OTPValidationRepository otpValidationRepository;


    @Value("${schedule.reset-otp}")
    private String resetValue;

    @Value("${schedule.interval}")
    private Integer interval;

    @Value("${schedule.stamp}")
    private String stamp;
    /*
     * Scheduler run by every 1 minute
     *  @Scheduled(fixedDelay = 100000) ----> Every Five Minute
     *///  @Scheduled(cron = "0 */5 * ? * *")  ----> Every Five Minute

   // @Scheduled(fixedDelay = 100000)
    void doOtpExpireProcess() {
        logger.debug("Start doOtpExpireProcess()");
        logger.info("Reset Value:{} | Interval:{} | Stamp:{}", resetValue, interval);
        otpValidationRepository.updateRecords(resetValue, interval);
        logger.debug("End doOtpExpireProcess()");
    }

}
