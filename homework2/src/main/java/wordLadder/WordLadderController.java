package wordLadder;

import java.util.Stack;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordLadderController {
    private final String dictionaryPath1 = "src/dictionary.txt";
    private final String dictionaryPath2 = "src/smalldict1.txt";

    @RequestMapping("/wordLadder")
    public Stack<String> passLadder(@RequestParam(value="start", defaultValue="world") String start,
                                    @RequestParam(value="end", defaultValue="world") String end)
    {
        WordLadder aLadder = new WordLadder(dictionaryPath1);
        aLadder.find_ladder(start, end);
        return aLadder.get_ladder();
    }
}
