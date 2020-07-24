/*******************************************************************************
 * Copyright (c) 2020 Chmutov Eugene <garret.evg@gmail.com>.
 * All rights reserved.
 ******************************************************************************/

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyHashMapTest {
    MyHashMap map = new MyHashMap();
    @Before
    public void setUp() throws Exception {
        map = new MyHashMap();
        map.put(32, 1L);
        map.put(22, 2L);
        map.put(15, 3L);
        map.put(20, 5L);

    }
    @Test
    public void putTest() throws Exception {
        assertTrue(map.put(27, 6L));
    }

    @Test
    public void getTest() throws Exception {
        long value = map.get(15);
        assertEquals(3L, value);

    }

    @Test
    public void sizeTest() throws Exception {
        int size = map.size();
        assertEquals(4, size);
    }

    @Test(expected = NoSuchElementException.class)
    public void getWrongKeyTest() throws Exception {
        map.get(100);
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorTest() throws Exception {
        MyHashMap map1 = new MyHashMap(-1);
    }
    @Test
    public void resizeTest() throws Exception {
        MyHashMap map1 = new MyHashMap(8,0.5f,2f);
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
    public void putByKeyInUseWithSameValueTest() throws Exception {
        int prevSize = map.size();
        map.put(20, 5L);
        assertEquals(prevSize, map.size());
        assertEquals(5L, map.get(20));
    }

    @Test(expected = RuntimeException.class)
    public void putByKeyInUseWithAnotherValueTest() throws Exception {
        map.put(20, 7L);
    }

    @Test
    public void randomStressTest(){
        int numberEntries = 30000000;
        Random rand = new Random();
        int key;
        long value;
        int[] keys = new int[numberEntries];
        long[] values = new long[numberEntries];
        int currSize = map.size();
        for (int i=0;i<numberEntries;i++){
            key = rand.nextInt(100000);
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
