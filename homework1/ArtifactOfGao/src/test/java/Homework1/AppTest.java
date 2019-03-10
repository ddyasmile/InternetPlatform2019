package Homework1;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     *
     * @throws IOException
     */
    @Test
    public void testApp1() throws IOException {
        assertTrue(App.MakeDic().get("aa") == 0);
    }

    @Test
    public void testApp2() throws IOException {
        assertTrue(App.MakeDic().get("a") == null);
    }

    @Test
    public void testApp3() throws IOException {
        assertTrue(App.MakeDic().size() == 221922);
    }
}