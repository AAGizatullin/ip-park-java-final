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
class CRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformFilmCRUD() throws Exception {
        // getAll
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/films/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "films": [
                                              {
                                                "id": 1,
                                                "name": "Titanic",
                                                "price": 300,
                                                "genre": [
                                                  "melodrama",
                                                  "history"
                                                ],
                                                "rating": 8.4,
                                                "image": "titanic.jpg"
                                              },
                                              {
                                                "id": 2,
                                                "name": "We are the Millers",
                                                "price": 150,
                                                "genre": [
                                                  "comedy"
                                                ],
                                                "rating": 7.3,
                                                "image": "millers.jpg"
                                              },
                                              {
                                                "id": 3,
                                                "name": "The Silence of the Lambs",
                                                "price": 200,
                                                "genre": [
                                                  "detective",
                                                  "horror"
                                                ],
                                                "rating": 8.3,
                                                "image": "lambs.jpg"
                                              },
                                              {
                                                "id": 4,
                                                "name": "Coco",
                                                "price": 250,
                                                "genre": [
                                                  "cartoon",
                                                  "fantasy"
                                                ],
                                                "rating": 8.6,
                                                "image": "coco.jpg"
                                              },
                                              {
                                                "id": 5,
                                                "name": "The Holiday",
                                                "price": 150,
                                                "genre": [
                                                  "comedy",
                                                  "melodrama"
                                                ],
                                                "rating": 7.7,
                                                "image": "holiday.jpg"
                                              },
                                              {
                                                "id": 6,
                                                "name": "The Terminator",
                                                "price": 200,
                                                "genre": [
                                                  "fantasy",
                                                  "thriller"
                                                ],
                                                "rating": 8.0,
                                                "image": "terminator.jpg"
                                              },
                                              {
                                                "id": 7,
                                                "name": "Avatar",
                                                "price": 300,
                                                "genre": [
                                                  "fantasy",
                                                  "thriller",
                                                  "melodrama"
                                                ],
                                                "rating": 7.9,
                                                "image": "avatar.jpg"
                                              },
                                              {
                                                "id": 8,
                                                "name": "Sherlock Holmes",
                                                "price": 150,
                                                "genre": [
                                                  "detective",
                                                  "thriller",
                                                  "comedy"
                                                ],
                                                "rating": 8.1,
                                                "image": "sherlock.jpg"
                                              }
                                            ]
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
                                // language=JSON
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
                                              "views": 300,
                                              "image": "lambs.jpg"
                                            }
                                        }
                                        """
                        )
                );


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/films/getById")
                                .queryParam("id", String.valueOf(999))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                  "id": 0,
                                                    "name": "Ирония судьбы, или С легким паром! ",
                                                    "price": 150,
                                                    "productionYear": 1975,
                                                    "country": [
                                                      "USSR"
                                                    ],
                                                    "genre": [
                                                      "comedy",
                                                      "melodrama"
                                                    ],
                                                    "rating": 8.2,
                                                    "views": 450,
                                                    "image":"bath.jpg"
                                                }
                                                """
                                )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "film": {
                                              "id": 9,
                                              "name": "Ирония судьбы, или С легким паром! ",
                                              "price": 150,
                                              "productionYear": 1975,
                                              "country": [
                                                "USSR"
                                              ],
                                              "genre": [
                                                "comedy",
                                                "melodrama"
                                              ],
                                              "rating": 8.2,
                                              "views": 450,
                                              "image": "bath.jpg"
                                            }
                                        }
                                        """
                        )
                );
    }
}
