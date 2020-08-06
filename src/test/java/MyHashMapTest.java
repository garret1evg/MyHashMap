import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyHashMapTest {
    MyHashMap map = new MyHashMap();
    @Before
    public void setUp() {
        map = new MyHashMap();
        map.put(32, 1L);
        map.put(22, 2L);
        map.put(15, 3L);
        map.put(20, 5L);

    }
    @Test
    public void putTest() {
        assertTrue(map.put(27, 6L));
    }

    @Test
    public void putDefaultEmptyKeyFirstTimeTest() {
        int prevSize = map.size();

        map.put(0, 8L);
        assertEquals(++prevSize, map.size());
        assertEquals(8L, map.get(0));
    }

    @Test
    public void putDefaultEmptyKeySecondTimeTest() {
        int prevSize = map.size();
        map.put(0, 8L);
        map.put(0, 1L);
        assertEquals(++prevSize, map.size());
        assertEquals(1L, map.get(0));
    }

    @Test
    public void getTest() {
        long value = map.get(15);
        assertEquals(3L, value);

    }

    @Test
    public void sizeTest() {
        int size = map.size();
        assertEquals(4, size);
    }

    @Test(expected = NoSuchElementException.class)
    public void getWrongKeyTest() {
        map.get(100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest() {
        MyHashMap map1 = new MyHashMap(-1);
    }
    @Test
    public void resizeTest() {
        MyHashMap map1 = new MyHashMap(8,0.5f,2f,0);
        map1.put(32, 1L);
        map1.put(22, 2L);
        map1.put(15, 3L);
        map1.put(20, 5L);
        map1.put(27, 6L);
        assertEquals(5, map1.size());
        assertEquals(1L, map1.get(32));
        assertEquals(2L, map1.get(22));
        assertEquals(3L, map1.get(15));
        assertEquals(5L, map1.get(20));
        assertEquals(6L, map1.get(27));
    }
    @Test
    public void putByKeyInUseWithSameValueTest() {
        int prevSize = map.size();
        map.put(20, 5L);
        assertEquals(prevSize, map.size());
        assertEquals(5L, map.get(20));
    }

    @Test()
    public void putByKeyInUseWithAnotherValueTest() {
        int prevSize = map.size();
        map.put(20, 7L);
        assertEquals(prevSize, map.size());
        assertEquals(7L, map.get(20));
    }

    @Test
    public void randomStressTest(){
        int numberEntries = 3000000;
        Random rand = new Random();
        int key;
        long value;
        int[] keys = new int[numberEntries];
        long[] values = new long[numberEntries];
        int currSize = map.size();
        for (int i=0;i<numberEntries;i++){
            key = rand.nextInt();
            value = rand.nextLong();
            if(!map.containsKey(key)){
                map.put(key,value);
                keys[i] = key;
                values[i] = value;
                assertEquals(++currSize, map.size());
                assertEquals(value, map.get(key));
            }

        }
        for (int i=0;i<numberEntries;i++){
            if(keys[i]!=0){
                assertEquals(values[i], map.get(keys[i]));
            }
        }
    }

}
