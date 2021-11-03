package com.company;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WordOccurrencesTest {

    @Mock
    FileSystemDataService s;

    @Spy
    HashMap<String, Integer> t = new HashMap<>();

    @InjectMocks
    WordOccurrences sol = new WordOccurrences();

    @Before
    public void setUp() throws IOException {
        this.sol = new WordOccurrences();
        sol.setService(s);
        Mockito.when(s.getData("first_path")).thenReturn("hello world");
        Mockito.when(s.getData("second_path")).thenReturn("hello worlds hey");
        Mockito.when(s.getData("incorrect_path")).thenThrow(new FileNotFoundException());
    }

    @Test
    public void testFirstPath() throws IOException {
        t.put("hello", 1);
        t.put("world", 1);

        Assert.assertEquals(sol.process("first_path"), t);
    }

    @Test
    public void testSecondPath() throws IOException {
        t.put("hello", 1);
        t.put("worlds", 1);
        t.put("hey", 1);

        Assert.assertEquals(sol.process("second_path"), t);
    }

    @Test(expected = FileNotFoundException.class)
    public void testIncorrectPath() throws IOException {
        var t = new HashSet<String>();
        t.add("test");
        Assert.assertEquals(sol.process("incorrect_path"), t);
    }
    @Test
    public void testCalls() throws IOException {
        sol.process("first_path");
        verify(s, times(1)).getData("first_path");
    }
}