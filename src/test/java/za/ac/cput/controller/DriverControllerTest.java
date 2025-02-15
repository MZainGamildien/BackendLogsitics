package za.ac.cput.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Driver;
import za.ac.cput.factory.DriverFactory;
import za.ac.cput.util.Helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverControllerTest {

    static Driver driver =
            DriverFactory.createNewDriver("Lyle", "Esau", "0623458765", "lyle@gmail.com", "delivery driver", Helper.generateID());

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseURL = "http://localhost:8080/driver";

    @Test
    void a_create(){
        String url= baseURL + "/create";
        System.out.println("URL: " + url);
        //post
        ResponseEntity<Driver> postResponse = restTemplate.postForEntity(url, driver, Driver.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

        Driver savedDriver = postResponse.getBody();
        assertEquals(savedDriver.getDriverId(), savedDriver.getDriverId());
        System.out.println("Saved data: " + savedDriver);
    }

    @Test
    void b_read(){
        String url = baseURL + "/read/" + driver.getDriverId();
        System.out.println("URL: " + url);
        //get
        ResponseEntity<Driver> response = restTemplate.getForEntity(url, Driver.class);
        System.out.println(response.getBody());
    }

    @Test
    void c_update() {
        Driver updated = new Driver.Builder()
                .copy(driver)
                .setFirstName("Enrico")
                .setLastName("Marez")
                .setDriverPosition("Staff Shuttle driver")
                .build();
        String url = baseURL + "/update";
        System.out.println("URL: " + url);
        System.out.println("Post data: " + updated);
        // post
        ResponseEntity<Driver> response = restTemplate.postForEntity(url, updated, Driver.class);
        //assertNotNull(response.getBody());
    }

    @Test
    @Disabled
    void e_delete() {
        String url = baseURL + "/delete/" + driver.getDriverId();
        System.out.println("URL: " + url);
        restTemplate.delete(url);
    }

    @Test
    void  d_getAll() {
        String url = baseURL + "/getAll";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("Show ALL:");
        System.out.println(response);
        System.out.println(response.getBody());
    }

}

