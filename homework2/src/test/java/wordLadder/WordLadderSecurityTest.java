package wordLadder;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Stack;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordLadderSecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void accessWordLadderUnauthorized() {
        String url = "http://localhost:" + this.port + "wordLadder";

        ResponseEntity<Map> entity = this.testRestTemplate
                .getForEntity(url, Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void invalidUserAccessWordLadder() {
        String url = "http://localhost:" + this.port + "wordLadder";

        ResponseEntity<Map> entity = this.testRestTemplate
                .withBasicAuth("invalidUser", "invalidPassword")
                .getForEntity(url, Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void validUserAccessWordLadder() {
        String url = "http://localhost:" + this.port + "wordLadder";

        ResponseEntity<Stack> entity = this.testRestTemplate
                .withBasicAuth("user0", "password")
                .getForEntity(url, Stack.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
