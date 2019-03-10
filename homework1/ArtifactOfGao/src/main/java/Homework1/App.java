package Homework1;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        MakeDic();
    }

    public static Map<String, Integer> MakeDic() throws IOException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String pathname = "Homework1\\ArtifactOfGao\\src\\main\\java\\Homework1\\dictionary.txt";
        try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                map.put(line, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

}
