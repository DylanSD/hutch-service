package unified.service;

import unified.model.State;

public interface RelayService {

    State findOne(int id);

    boolean setRelayState(int relayNum, State state);

    boolean exists(int relayNum);


}
