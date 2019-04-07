package wordLadder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class WordLadder {

    private Map<String, Byte> wordMap = new HashMap<>();
    private Queue<Stack<String>> ladderQ = new LinkedList<>();
    private Stack<String> ladder = new Stack<>();

    public WordLadder(String sourcePath) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(sourcePath));
            String s;
            while((s = br.readLine())!=null){
                line = System.lineSeparator() + s;
                this.wordMap.put(line.substring(2), (byte)0);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init_map() {
        for (String key : wordMap.keySet()) {
            wordMap.replace(key, (byte)0);
        }
    }

    private void access_map(String key) {
        this.wordMap.replace(key, (byte)1);
    }

    private boolean is_accessed(String key) {
        byte value = this.wordMap.get(key);
        return (value==1);
    }

    private Stack<String> deep_clone_stack(Stack<String> source) {
        Stack<String> dest = new Stack<>();

        for (String s:source) {
            dest.push(s);
        }
        return dest;
    }

    private Vector<String> find_next(String word) {
        Vector<String> res = new Vector<>();
        char[] a2z;
        a2z = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        int len = word.length();
        for (int i = 0; i < len; i++) {
            char currentChar = word.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (a2z[j] == currentChar) continue;
                StringBuilder wordCopy = new StringBuilder(word);
                wordCopy.setCharAt(i, a2z[j]);
                if (!this.wordMap.containsKey(wordCopy.toString()))
                    continue;
                if (is_accessed(wordCopy.toString()))
                    continue;
                res.add(wordCopy.toString());
                access_map(wordCopy.toString());
            }
        }

        return res;
    }

    public void find_ladder(String start, String end) {
        init_map();
        if (!this.ladderQ.isEmpty())
            this.ladderQ = new  LinkedList<>();
        if (!this.ladder.isEmpty())
            this.ladder = new Stack<>();
        Stack<String> startStk = new Stack<>();

        startStk.push(start);
        this.ladderQ.add(startStk);
        access_map(start);

        while (!this.ladderQ.isEmpty()) {
            Stack<String> currentStk = this.ladderQ.poll();
            String currentStr = currentStk.peek();
            if (currentStr.equals(end)) {
                this.ladder = currentStk;
                break;
            }
            Vector<String> nextWords = new Vector<>(find_next(currentStr));
            for (String nextWord:nextWords) {
                Stack<String> newSkt;
                newSkt = deep_clone_stack(currentStk);
                newSkt.push(nextWord);
                this.ladderQ.add(newSkt);
            }
        }
    }

    public Stack<String> get_ladder() {
        return this.ladder;
    }

    public Map<String, Byte> listWordMap() {
        return this.wordMap;
    }

    public Queue<Stack<String>> listLadderQ () {
        return this.ladderQ;
    }
}
