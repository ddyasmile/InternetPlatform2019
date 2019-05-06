package wordLadder;


import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RestController
public class WordLadderController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    private final String dictionaryPath1 = "src/dictionary.txt";
    private final String dictionaryPath2 = "src/smalldict1.txt";

    @RequestMapping("/wordLadder")
    public Stack<String> passLadder(@RequestParam(value="user", defaultValue="") String user,
                                    @RequestParam(value="password", defaultValue="") String password,
                                    @RequestParam(value="start", defaultValue="") String start,
                                    @RequestParam(value="end", defaultValue="") String end)
    {
        ServiceInstance serviceInstance = loadBalancer.choose("authorization");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        try {
            String callServiceResult;
            callServiceResult = new TestRestTemplate()
                    .withBasicAuth(user, password)
                    .getForObject(serviceInstance.getUri().toString() + "/", String.class);

            if (callServiceResult.equals("This is login")) {
                WordLadder aLadder = new WordLadder(dictionaryPath1);
                aLadder.find_ladder(start, end);
                Stack<String> word_ladder = aLadder.get_ladder();
                word_ladder.push(callServiceResult);
                word_ladder.push("and you have login as " + user);
                return word_ladder;
            }
        }
        catch (Exception e) {
        }

        Stack<String> response = new Stack<>();
        response.push("username or password error");
        return response;
    }
}
