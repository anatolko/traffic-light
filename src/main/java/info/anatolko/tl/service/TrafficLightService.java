package info.anatolko.tl.service;

import info.anatolko.tl.domain.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    private Color currentLight;
    private Color nextLight;
    private int timer;
    private boolean buttonState;
    private boolean onServiceMode;

    public TrafficLightService() {
        currentLight = Color.GREEN;
        nextLight = Color.GREEN;
    }

    public Color getCurrentLight() {
        return currentLight;
    }

    public void pushTheButton() {
        buttonState = true;
        logger.info("Pushed button for RED Light");
    }

    /**
     * Switching light of Traffic Light
     * @param nextLight Light that should be turned on next
     */
    private void switchLight(Color nextLight) {
        timer = 0;
        currentLight = Color.YELLOW;
        this.nextLight = nextLight;
        logger.info("Switching light to " + nextLight);
    }

    /**
     * Turn On/Turn Off "On Service" Mode
     *
     * @return current status of "On Service" Mode
     */
    public boolean switchServiceMode() {
        onServiceMode = !onServiceMode;

        if (onServiceMode) {
            currentLight = Color.YELLOW;
        }

        return onServiceMode;
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
        timer++;

        if (onServiceMode) {
            currentLight = Color.YELLOW;
        } else {
            switch (currentLight) {
                case RED:
                    if (timer >= RED_LIGHT_TIMER_MAX) {
                        switchLight(Color.GREEN);
                    }
                    break;
                case GREEN:
                    if (buttonState && timer >= GREEN_LIGHT_TIMER_MIN) {
                        switchLight(Color.RED);
                        buttonState = false;
                    }
                    break;
                case YELLOW:
                    if (timer >= YELLOW_LIGHT_TIMER_MAX) {
                        timer = 0;
                        currentLight = nextLight;
                        logger.info("Light is " + currentLight);
                    }
                    break;
            }
        }
    }
}
