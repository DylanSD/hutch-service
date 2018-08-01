package unified.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import unified.model.State;
import unified.service.DaylightService;
import unified.service.RelayService;

import java.time.LocalTime;
import java.util.Optional;

@RestController(value = "/relays")
public class RelayController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private DaylightService daylightService = new DaylightService();

    @Autowired
    private RelayService relayService;

    @PutMapping(value = "/relays/{relayNum}/{state}")
    public ResponseEntity<State> updateRelay(@PathVariable int relayNum, @PathVariable State state) {
        if (!relayService.setRelayState(relayNum, state)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Updated existing relay {} to {}", relayNum, state);
        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @GetMapping(value = "/relays/{relayNum}")
    public ResponseEntity<State> getRelay(@PathVariable int relayNum) {
        logger.info("Request for relay  {} received.", relayNum);
        return Optional.ofNullable(relayService.findOne(relayNum))
                .map(relay -> ResponseEntity.ok().body(relay))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/turnAllOn")
    public ResponseEntity turnAllOn() throws InterruptedException {
        logger.info("Request for turning on ALL relays received.");
        for (int i = 0; i < 8 ; i++) {
            relayService.setRelayState(i, State.ON);
            Thread.sleep(500L);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/turnAllOff")
    public ResponseEntity turnAllOff() throws InterruptedException {
        logger.info("Request for turning off relays received.");
        for (int i = 0; i < 8 ; i++) {
            relayService.setRelayState(i, State.OFF);
            Thread.sleep(500L);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/shouldLightBeOn")
    public ResponseEntity<Boolean> shouldLightBeOn() throws InterruptedException {
        logger.info("Request for checking if the light should be on?");
        return Optional.ofNullable(daylightService.shouldLightBeOnNow(LocalTime.now()))
                .map(relay -> ResponseEntity.ok().body(relay))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/info")
    public String getInfo() {
        StringBuilder sb = new StringBuilder("Relay and Light Service: Turn on or off relays or electric devices assigned to the relay. \n");
        sb.append("Get all relays: GET http://localhost:8080/relays \n");
        sb.append("Get all relays: POST http://localhost:8080/relays, {\"relayNum\":\"1\",\"state\":\"HIGH\"} \n");
        sb.append("Update relay: PUT http://localhost:8080/relays/1 \n");
        return sb.toString();
    }
}
