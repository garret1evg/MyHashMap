import java.util.Arrays;
import java.util.NoSuchElementException;

/*******************************************************************************
 * Copyright (c) 2020 Chmutov Eugene <garret.evg@gmail.com>.
 * All rights reserved.
 ******************************************************************************/

public class MyHashMap {

    private final int DEFAULT_INITIAL_CAPACITY = 16;//размер карты по умолчанию
    private final float LOAD_FACTOR = 0.5f;//максимальная загруженость карты по умолчанию
    private final float MULTIPLIER = 2.0f; //во столько раз увеличится размер карты при пересборке по умолчанию

    private int capacity;
    private int size; //количество елементов в карте
    private float loadFactor;
    private float multiplier;
    private int[] keys;
    private long[] values;
    private boolean[] isInUse; //массив для определения статуса ячейки в карте(свободна/занята)

    //конструкторы
    public MyHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = LOAD_FACTOR;
        this.size = 0;
        this.multiplier = MULTIPLIER;
        keys = new int[capacity];
        values = new long[capacity];
        isInUse = new boolean[capacity];
        Arrays.fill(isInUse, Boolean.FALSE);
    }

    public MyHashMap(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException("Initial capacity is negative: " + capacity);
        } else {
            this.capacity = capacity;
            this.loadFactor = LOAD_FACTOR;
            this.size = 0;
            this.multiplier = MULTIPLIER;
            keys = new int[capacity];
            values = new long[capacity];
            isInUse = new boolean[capacity];
            Arrays.fill(isInUse, Boolean.FALSE);
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
            keys = new int[capacity];
            values = new long[capacity];
            isInUse = new boolean[capacity];
            Arrays.fill(isInUse, Boolean.FALSE);
        }
    }

    // кладет элемент с ключом и значением
    public boolean put(int key, long value) {
        if (size + 1 >= capacity * loadFactor) {
            resize();
        }
        int index = getIndex(key);
        for (int i = index; ; i++) {
            if(i == capacity) i = 0;
            if(!isInUse[i]) {
                keys[i] = key;
                values[i] = value;
                isInUse[i] = Boolean.TRUE;
                size++;
                return true;
            }
            if (keys[i] == key){
                if(values[i] == value)
                    return true;
                throw new RuntimeException("Key is already is use!!!");
            }

        }
    }

    // возвращает значение по ключу
    public long get(int key) {
        for (int i = getIndex(key); ; i++) {
            if(i == capacity) i = 0;
            if(!isInUse[i]) throw new NoSuchElementException("No such key! -> [" + key + "]");
            if (keys[i] == key) return values[i];
        }
    }

    // возвращает количество элементов в карте
    public int size() {
        return size;
    }

    // увеличение размера карты
    private void resize(){
        capacity *= multiplier;
        int[] oldKeys = keys;
        long[] oldValues = values;
        boolean[] oldIsInUse = isInUse;
        keys = new int[capacity];
        values = new long[capacity];
        isInUse = new boolean[capacity];
        size = 0;

        for (int i = 0;i < (capacity/multiplier) ; i++) {
            if (oldIsInUse[i])
                put(oldKeys[i],oldValues[i]);
        }

    }

    // возвращает номер позиции по значению хэш-функции
    private int getIndex(int key){
        return hash(key) % capacity;
    }

    // хэш-функция
    private int hash(int hash){

        return (hash >> 15) ^ hash;
    }

    //проверяет наличие ключа в карте
    public boolean containsKey(int key){
        for (int i = getIndex(key); ; i++) {
            if(i == capacity) i = 0;
            if(!isInUse[i]) return false;
            if (keys[i] == key) return true;
        }
    }

}
