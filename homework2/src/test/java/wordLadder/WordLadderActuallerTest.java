package wordLadder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class WordLadderActuallerTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map<?, ?> info = (Map<?, ?>)(entity.getBody().get("app"));
        then(info.get("name")).isEqualTo("spring-boot-wordLadder");
        then(info.get("version")).isEqualTo("v1.0.0");
    }

    @Test
    public void checkHealthStatus() throws Exception {
        Map<?, ?> response = this.testRestTemplate.getForObject(
                "http://localhost:" + this.mgt + "actuator/health", Map.class);

        then(response.get("status")).isEqualTo("UP");
    }

    @Test
    public void checkHttptrace() throws Exception {

        String url = "http://localhost:" + this.port + "wordLadder";
        this.testRestTemplate.getForObject(url, Stack.class);

        Map<?, ?> trace = this.testRestTemplate.getForObject(
                "http://localhost:" + this.mgt + "actuator/httptrace", Map.class);
        Map<?, ?> request = (Map<?, ?>) ((Map<?, ArrayList<Map<?, ?>>>)trace).get("traces").get(0).get("request");
        Map<?, ?> response = (Map<?, ?>) ((Map<?, ArrayList<Map<?, ?>>>)trace).get("traces").get(0).get("response");

        then(((Map<?, ArrayList<?>>)trace).size()).isEqualTo(1);
        then(request.get("method")).isEqualTo("GET");
        then(response.get("status")).isEqualTo(200);
    }
}
