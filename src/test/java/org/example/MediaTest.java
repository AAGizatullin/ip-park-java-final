package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.hamcrest.core.StringEndsWith;

import java.nio.file.Files;
import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class MediaTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile());

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldUploadSingleMultipart() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("file", "media.jpg", MediaType.IMAGE_JPEG_VALUE, Files.newInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "media.jpg").toPath()));

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/media/multipart")
                                .file(file)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.name").value(StringEndsWith.endsWith(".jpg"))
                );

    }
}
