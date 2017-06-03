package info.anatolko.tl.service;

import info.anatolko.tl.domain.Color;
import info.anatolko.tl.domain.ColorLog;
import info.anatolko.tl.domain.TrafficLightState;
import info.anatolko.tl.repository.ColorLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Service for working with traffic light
 */
@Service
public class TrafficLightService {

    private final Logger logger = LoggerFactory.getLogger(TrafficLightService.class);

    private final int RED_LIGHT_TIMER_MAX = 15;
    private final int YELLOW_LIGHT_TIMER_MAX = 2;
    private final int GREEN_LIGHT_TIMER_MIN = 30;
    private final int TIMER_DELAY = 1000;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ColorLogRepository colorLogRepository;

    private TrafficLightState tls;

    @Autowired
    public TrafficLightService(SimpMessagingTemplate simpMessagingTemplate, ColorLogRepository colorLogRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.colorLogRepository = colorLogRepository;

        tls = new TrafficLightState();
    }

    @PostConstruct
    private void afterInit() {
        colorLogRepository.save(new ColorLog(tls.getCurrentLight()));
    }

    public Color getCurrentLight() {
        return tls.getCurrentLight();
    }

    public void pushTheButton() {
        tls.setButtonState(true);
        logger.info("Pushed button for RED Light");
    }

    /**
     * Switching light of Traffic Light
     * @param nextLight Light that should be turned on next
     */
    private void switchLight(Color nextLight) {
        tls.setTimer(0);
        tls.setCurrentLight(Color.YELLOW);
        tls.setNextLight(nextLight);

        colorLogRepository.save(new ColorLog(tls.getCurrentLight()));
        logger.info("Switching light to " + nextLight);
    }

    /**
     * Turn On/Turn Off "On Service" Mode
     *
     * @return current status of "On Service" Mode
     */
    public boolean switchServiceMode() {
        tls.setOnServiceMode(!tls.isOnServiceMode());

        if (tls.isOnServiceMode()) {
            tls.setCurrentLight(Color.YELLOW);

            colorLogRepository.save(new ColorLog(tls.getCurrentLight()));
            logger.info("Light is " + tls.getCurrentLight());
        }

        return tls.isOnServiceMode();
    }


    /**
     * Main worker
     * By timer checking current status of traffic light, button and service mode flag
     *
     * When service mode is TRUE - light is always YELLOW.
     * GREEN light should be turned on not less than 30 seconds, if it was turned on less than 30 second it should wait
     * before switching.
     * When button was pushed light should be switched to RED for 15 seconds then switched back to GREEN.
     * Switching from RED to GREEN and back should working throw YELLOW light (minimum 2 seconds).
     */
    @Scheduled(fixedDelay = TIMER_DELAY, initialDelay = TIMER_DELAY)
    public void tikTak () {
        tls.incTimer();

        if (tls.isOnServiceMode()) {
            tls.setCurrentLight(Color.YELLOW);
        } else {
            switch (tls.getCurrentLight()) {
                case RED:
                    if (tls.getTimer() >= RED_LIGHT_TIMER_MAX) {
                        switchLight(Color.GREEN);
                    }
                    break;
                case GREEN:
                    if (tls.isButtonState() && tls.getTimer() >= GREEN_LIGHT_TIMER_MIN) {
                        switchLight(Color.RED);
                        tls.setButtonState(false);
                    }
                    break;
                case YELLOW:
                    if (tls.getTimer() >= YELLOW_LIGHT_TIMER_MAX) {
                        tls.setTimer(0);
                        tls.setCurrentLight(tls.getNextLight());

                        colorLogRepository.save(new ColorLog(tls.getCurrentLight()));
                        logger.info("Light is " + tls.getCurrentLight());
                    }
                    break;
            }
        }

        simpMessagingTemplate.convertAndSend("/tl-state", tls);
    }
}
