package wordLadder;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class WordLadderTest {
    @Test
    public void testWordLadderConstructor() {
        assertNotNull("should not be null", new WordLadder("src/smalldict1.txt"));
    }

    @Test
    public void testWordMapLength() {
        WordLadder testWordLadder = new WordLadder("src/dictionary.txt");
        assertThat(testWordLadder.listWordMap().size(), equalTo(221922));
    }

    @Test
    public void testWordMapContent() {
        WordLadder testWordLadder = new WordLadder("src/dictionary.txt");
        assertThat(testWordLadder.listWordMap().keySet(), hasItems("aardwolf","outride","wastable"));
    }

    @Test
    public void testWordLadderCorrectness() {
        Integer[] result = {5,5,1,9,0,0,0,0,0,0};

        String[][] test = {
                {"code","data"},
                {"data","code"},
                {"gap","gap"},
                {"start","dream"},
                {"zz","vv"},
                {"destination","aaaaaaaaaaa"},
                {"aaaaaaaa","bbbbbbbb"},
                {"iquaman","ironman"},
                {"aaaaaa","bbbbbb"},
                {"bbbbbb","aaaaaa"}
        };

        WordLadder testWordLadder = new WordLadder("src/dictionary.txt");
        for (Integer i = 0; i < 10; i++) {
            testWordLadder.find_ladder(test[i][0], test[i][1]);
            assertThat(testWordLadder.get_ladder().size(), equalTo(result[i]));
        }
    }
}
