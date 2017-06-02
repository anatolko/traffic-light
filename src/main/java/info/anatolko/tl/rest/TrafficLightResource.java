package info.anatolko.tl.rest;

import info.anatolko.tl.service.TrafficLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for working with traffic light.
 */
@RestController
@RequestMapping("/api/tl")
public class TrafficLightResource {

    private final TrafficLightService trafficLightService;

    /**
     * @param trafficLightService service for working with traffic light
     */
    @Autowired
    public TrafficLightResource(TrafficLightService trafficLightService) {
        this.trafficLightService = trafficLightService;
    }

    /**
     * GET /api/tl/light : get current color of light.
     *
     * @return 200 (OK) and current color of light
     */
    @GetMapping("/light")
    public ResponseEntity<?> getCurrentLightColor() {
        return ResponseEntity.ok(trafficLightService.getCurrentLight());
    }

    /**
     * POST /api/tl/button : push button for color change.
     *
     * @return 200 (OK) if button was pushed
     */
    @PostMapping("/button")
    public ResponseEntity<?> pushTheButton() {
        trafficLightService.pushTheButton();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * TurnOn/TurnOff "On Service" Mode.
     *
     * @return 200(OK) and status of "Service Mode"
     */
    @PostMapping("/on-service")
    public ResponseEntity<?> switchServoceMode() {
        return ResponseEntity.ok(trafficLightService.switchServiceMode());
    }
}
