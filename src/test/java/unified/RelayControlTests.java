/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package unified;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import unified.model.State;
import unified.service.RelayService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RelayControlTests {

    @MockBean
    private RelayService relayService;

    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    @Ignore
    @Test
    public void turnOffRelayTest() throws Exception {
        State onRelay = State.OFF;
        State offRelayRequest = State.ON;
        given(relayService.findOne(1))
                .willReturn(onRelay);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/relays/1").
                contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(offRelayRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getRelayStatusTest() throws Exception {
        given(relayService.findOne(1))
                .willReturn(State.ON);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/relays/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(relayService).findOne(1);
    }

}
