package ru.netology.firstspringbootapplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FirstSpringBootApplicationTests {

    private static final GenericContainer<?> myDevApp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);
    private static final GenericContainer<?> myProdApp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        myDevApp.start();
        myProdApp.start();
    }

    @Test
    void devAppTest() {
        Integer firstAppPort = myDevApp.getMappedPort(8080);

        ResponseEntity<String> firstEntity = restTemplate.getForEntity("http://localhost:" + firstAppPort + "/profile", String.class);

        Assertions.assertEquals(firstEntity.getBody(), "Current profile is dev");
    }

    @Test
    void prodAppTest() {
        Integer secondAppPort = myProdApp.getMappedPort(8081);

        ResponseEntity<String> firstEntity = restTemplate.getForEntity("http://localhost:" + secondAppPort + "/profile", String.class);

        Assertions.assertEquals(firstEntity.getBody(), "Current profile is production");
    }
}
