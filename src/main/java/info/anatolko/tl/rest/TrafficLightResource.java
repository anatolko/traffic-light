package info.anatolko.tl.rest;

import info.anatolko.tl.service.TrafficLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
     * GET /api/tl/light/bydate?date= : get light color from log by date
     * date should be transferred in format "2017-06-03 22:22:22"
     *
     * @param date of light
     * @return 200 (OK) and info from log about light color
     *         400 (Bad Request) if date was transferred in wrong format
     */
    @GetMapping("/light/bydate")
    public ResponseEntity<?> getTraffilLightStateByDate(@RequestParam String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return ResponseEntity.ok(trafficLightService.getLightByDateTime(dateTime));
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
