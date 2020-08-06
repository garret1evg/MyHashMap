import java.util.Arrays;
import java.util.NoSuchElementException;

/*******************************************************************************
 * Copyright (c) 2020 Chmutov Eugene <garret.evg@gmail.com>.
 * All rights reserved.
 ******************************************************************************/

public class MyHashMap {

    static private final int DEFAULT_INITIAL_CAPACITY = 16;//размер карты по умолчанию
    static private final float LOAD_FACTOR = 0.5f;//максимальная загруженость карты по умолчанию
    static private final float MULTIPLIER = 2.0f; //во столько раз увеличится размер карты при пересборке по умолчанию
    static private final int DEFAULT_EMPTY_KEY = 0;//пустой ключ по умолчанию
    static private final int DEFAULT_INTEGER = 0;//пустой int по умолчанию

    private int capacity;
    private final int emptyKey;//пустой ключ
    private boolean isEmptyKeyInUse;//используется пустой ключ
    private int indexEmptyKey;//индекс пустого ключа
    private int size; //количество елементов в карте
    private final float loadFactor;
    private int maxSize;
    private final float multiplier;
    private int[] keys;
    private long[] values;


    //конструкторы
    public MyHashMap(int capacity, float loadFactor, float multiplier,int emptyKey) {
        if (capacity <= 0){
            throw new IllegalArgumentException("Initial capacity is negative: " + capacity);
        } else if (loadFactor <= 0){
            throw new IllegalArgumentException("Initial loadFactor is negative: " + loadFactor);
        } else if (loadFactor >= 1){
            throw new IllegalArgumentException("Initial loadFactor is greater than 1: " + loadFactor);
        } else if (multiplier <= 1){
            throw new IllegalArgumentException("Initial multiplier is less than 1: " + multiplier);
        } else{
            this.capacity = capacity;
            this.loadFactor = loadFactor;
            this.multiplier = multiplier;
            this.size = 0;
            this.emptyKey = emptyKey;
            isEmptyKeyInUse = false;
            indexEmptyKey = getIndex(this.emptyKey);
            maxSize = (int) (capacity*loadFactor);
            keys = new int[capacity];
            values = new long[capacity];
            if (this.emptyKey != DEFAULT_INTEGER)
                Arrays.fill(keys,this.emptyKey);
        }
    }

    public MyHashMap(int capacity) {
        this(capacity,LOAD_FACTOR,MULTIPLIER,DEFAULT_EMPTY_KEY);
    }

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY,LOAD_FACTOR,MULTIPLIER,DEFAULT_EMPTY_KEY);
    }

    // кладет элемент с ключом и значением
    public boolean put(int key, long value) {
        if (size >= maxSize) {
            resize();
        }
        if (key == emptyKey){
            values[indexEmptyKey] = value;
            if (!isEmptyKeyInUse)
                {
                    size++;
                    isEmptyKeyInUse = true;

                }
            return true;
        }
        int index = getIndex(key);
        for (int i = index; ; i++) {
            if (i ==  indexEmptyKey) i++;
            if(i == capacity) i = 0;
            if(keys[i] == 0) {
                keys[i] = key;
                values[i] = value;
                size++;
                return true;
            }
            if (keys[i] == key){
                values[i] = value;
                return true;
            }

        }
    }

    // возвращает значение по ключу
    public long get(int key) {
        if ((key == emptyKey)&&(isEmptyKeyInUse))
            return values[indexEmptyKey];
        for (int i = getIndex(key); ; i++) {
            if (i == indexEmptyKey) i++;
            if(i == capacity) i = 0;
            if(keys[i] == 0) throw new NoSuchElementException("No such key! -> [" + key + "]");
            if (keys[i] == key) return values[i];
        }
    }

    // возвращает количество элементов в карте
    public int size() {
        return size;
    }

    // увеличение размера карты
    private void resize(){
        int oldCapacity = capacity;
        capacity *= multiplier;
        maxSize = (int) (capacity*loadFactor);
        int[] oldKeys = keys;
        long[] oldValues = values;
        keys = new int[capacity];
        values = new long[capacity];
        size = 0;
        int oldIndexEmptyKey = indexEmptyKey;
        indexEmptyKey = getIndex(emptyKey);
        if (isEmptyKeyInUse) {
            isEmptyKeyInUse = false;
            put(emptyKey, oldKeys[oldIndexEmptyKey]);
        }

        for (int i = 0;i < (oldCapacity) ; i++) {
            if (oldKeys[i] != 0)
                put(oldKeys[i],oldValues[i]);
        }

    }

    // возвращает номер позиции по значению хэш-функции
    private int getIndex(int key){
        int hash = Math.abs(hash(key));
        return  hash % capacity;
    }

    // хэш-функция
    private int hash(int hash){

        return (hash >> 15) ^ hash;
    }

    //проверяет наличие ключа в карте
    public boolean containsKey(int key){
        if ((key == emptyKey)&&(isEmptyKeyInUse))
            return true;
        for (int i = getIndex(key); ; i++) {
            if (i == indexEmptyKey) i++;
            if(i == capacity) i = 0;
            if(keys[i] == 0) return false;
            if (keys[i] == key) return true;
        }
    }

}
