package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class SaleTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile());

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformSale() throws Exception {
        //sale
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sales/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        //language=JSON
                                        """
                                                 {
                                                   "filmId": 3,
                                                   "price": 260
                                                 }
                                                """
                                )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                //language=JSON
                                """
                                        {
                                           "sale": {
                                             "id": 1,
                                             "filmId": 3,
                                             "name": "The Silence of the Lambs",
                                             "price": 200
                                           }
                                         }
                                        """
                        )

                );
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/films/getById")
                                .queryParam("id", String.valueOf(3))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                //language=JSON
                                """
                                        {
                                           "film": {
                                             "id": 3,
                                             "name": "The Silence of the Lambs",
                                             "price": 200,
                                             "productionYear": 1990,
                                             "country": [
                                               "USA"
                                             ],
                                             "genre": [
                                               "detective",
                                               "horror"
                                             ],
                                             "rating": 8.3,
                                             "views": 301,
                                             "image": "lambs.jpg"
                                           }
                                         }
                                        """
                        )
                );

    }
}
