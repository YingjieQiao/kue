package com.example.android1;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_URLGeneration() {
        String url = Utils.generateURL("username1", "fe3e4980-6bec-4390-85ae-16f0a9f4612f");
        assertEquals("http://3.82.106.27/username1/fe3e4980-6bec-4390-85ae-16f0a9f4612f", url);
    }
}