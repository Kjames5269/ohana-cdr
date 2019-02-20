/*
 * Copyright 2002-2019 the original author or authors.
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
package sample;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.model.Product;

@RunWith(SpringRunner.class)

//  The WebMvcTest annotation is a way for Spring to mock the server and only start up
//  our controller. If we want multiple controllers this can be left empty.
@WebMvcTest(ForaServerController.class)
public class ForaServerControllerTest {

  //  Base of all Spring boot testing. Seems like this is everywhere
  @Autowired private MockMvc mockMvc;

  @Test
  public void shouldPing() throws Exception {
    this.mockMvc
        .perform(get("/fora/ping"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Ping!")));
  }

  @Test
  public void shouldPong() throws Exception {
    this.mockMvc
        .perform(get("/fora/pong"))
        .andExpect(status().isOk())
        .andExpect(content().string(is("Pong!")));
  }

  // Creates a post request to send to the mocked controller and receives a JSON response
  @Test
  public void testAddingProduct() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/fora/product/")
                .content(asJsonString(new Product("1", "ProductName")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
  }

  public static String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
