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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.model.Product;

@RestController
public class ForaServerController {

  private Logger LOGGER = LoggerFactory.getLogger(ForaServerController.class);

  @GetMapping("${spring.data.fora.path}/ping")
  public String ping() {
    LOGGER.info("Ping");
    return "Ping!";
  }

  @GetMapping("${spring.data.fora.path}/pong")
  public String pong() {
    LOGGER.info("Pong");
    return "Pong!";
  }

  @PostMapping("${spring.data.fora.path}/product/")
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    String productID = product.getId();
    LOGGER.info("New product added with id: {}", productID);
    return new ResponseEntity<>(product, HttpStatus.CREATED);
  }
}
