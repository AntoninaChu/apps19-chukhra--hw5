package ua.edu.ucu;

import ua.edu.ucu.stream.IntStream;
import ua.edu.ucu.stream.AsIntStream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author andrii
 */
public class StreamAppTest {
    
    private IntStream intStream;
    private IntStream emptyStream;

    @Before
    public void init() {
        int[] intArr = {0, -1, 1, 2, 3};
        intStream = AsIntStream.of(intArr);
        emptyStream = AsIntStream.of();
    }
    
    @Test
    public void testStreamOperations() {
        System.out.println("streamOperations");
        int expResult = 42;
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testStreamToArray() {
        System.out.println("streamToArray");
        int[] expResult = {0, -1, 1, 2, 3};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);        
    }

    @Test
    public void testStreamForEach() {
        System.out.println("streamForEach");
        String expResult = "0-1123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testStreamTerminal() {
        System.out.println("streamTerminal");
        String expResult = "1.0 3 -1 5 5";
        String result = StreamApp.streamTerminal(intStream);
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsExceptionAverage() {
        emptyStream.average();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsExceptionMin() {
        emptyStream.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsExceptionMax() {
        emptyStream.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsExceptionSum() {
        emptyStream.sum();
    }

}
