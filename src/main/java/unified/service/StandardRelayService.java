package unified.service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.stereotype.Service;
import unified.model.State;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StandardRelayService implements RelayService {

    private Map<Integer, State> relays = new ConcurrentHashMap<>();

    private List<Pin> relayToPinNum = Arrays.asList(
            RaspiPin.GPIO_08,
            RaspiPin.GPIO_09,
            RaspiPin.GPIO_07,
            RaspiPin.GPIO_00,
            RaspiPin.GPIO_02,
            RaspiPin.GPIO_03,
            RaspiPin.GPIO_12,
            RaspiPin.GPIO_13);

    private GpioController gpioController;
    private Map<Integer, GpioPinDigitalOutput> relayToGpioOutputs = new ConcurrentHashMap<>();

    public StandardRelayService() {
        relays.put(0, State.OFF);
        relays.put(1, State.OFF);
        relays.put(2, State.OFF);
        relays.put(3, State.OFF);
        relays.put(4, State.OFF);
        relays.put(5, State.OFF);
        relays.put(6, State.OFF);
        relays.put(7, State.OFF);
    }

    public void init() {
        if (gpioController == null) {
            gpioController = GpioFactory.getInstance();
            for (Map.Entry<Integer, State> entry : relays.entrySet()) {
                relayToGpioOutputs.computeIfAbsent(entry.getKey(),
                        k -> gpioController.provisionDigitalOutputPin(
                                relayToPinNum.get(entry.getKey()),
                                PinState.HIGH));
            }
        }
    }

    @Override
    public State findOne(int id) {
        return relays.get(id);
    }

    @Override
    public boolean setRelayState(int relayNum, State state) {
        try {
            physicallySetRelay(relayNum, state);
        } catch (Throwable ep) {
            ep.printStackTrace();
            return false;
        }
        relays.put(relayNum, state);
        return true;
    }

    @Override
    public boolean exists(int relayNum) {
        return relays.containsKey(relayNum);
    }

    private void physicallySetRelay(int relayNum, State state) {
        init();
        if (relayNum < 0 || relayNum > 7) {
            throw new IllegalArgumentException("Relay number is out of range [0,7]");
        }
        if (state.equals(State.ON)) {
            relayToGpioOutputs.get(relayNum).setState(PinState.LOW);
        } else {
            relayToGpioOutputs.get(relayNum).setState(PinState.HIGH);
        }
    }
}
