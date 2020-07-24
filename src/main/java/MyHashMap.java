/*******************************************************************************
 * Copyright (c) 2020 Chmutov Eugene <garret.evg@gmail.com>.
 * All rights reserved.
 ******************************************************************************/

public class MyHashMap {

    private final int DEFAULT_INITIAL_CAPACITY = 37;//размер карты по умолчанию
    private final float LOAD_FACTOR = 0.5f;//максимальная загруженость карты по умолчанию
    private final float MULTIPLIER = 2.0f; //во столько раз увеличится размер карты при пересборке по умолчанию

    private int capacity;
    private int size; //количество елементов в карте
    private float loadFactor;
    private float multiplier;
    private Entry[] table;

    //конструкторы
    public MyHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = LOAD_FACTOR;
        this.size = 0;
        this.multiplier = MULTIPLIER;
        table = new Entry[capacity];
    }

    public MyHashMap(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException("Initial capacity is negative: " + capacity);
        } else {
            this.capacity = capacity;
            this.loadFactor = LOAD_FACTOR;
            this.size = 0;
            this.multiplier = MULTIPLIER;
            table = new Entry[capacity];
        }

    }

    public MyHashMap(int capacity, float loadFactor, float multiplier) {
        if (capacity <= 0){
            throw new IllegalArgumentException("Initial capacity is negative: " + capacity);
        } else if (loadFactor <= 0){
            throw new IllegalArgumentException("Initial loadFactor is negative: " + loadFactor);
        } else if (multiplier <= 0){
            throw new IllegalArgumentException("Initial multiplier is negative: " + multiplier);
        } else{
            this.capacity = capacity;
            this.loadFactor = loadFactor;
            this.multiplier = multiplier;
            this.size = 0;
            table = new Entry[capacity];
        }
    }

    public boolean put(int key, long value) {

    }


    public long get(int key) {

    }


    public int size() {

    }

    public static void main(String[] args){


    }

    //Пара <ключ, значение>
    class Entry {
        private int key;
        private long value;

        public Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }

    }
}
