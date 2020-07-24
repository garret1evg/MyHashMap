import java.util.NoSuchElementException;
import java.util.Objects;

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

    // кладет элемент с ключом и значением
    public boolean put(int key, long value) {
        if (size + 1 >= capacity * loadFactor) {
            resize();
        }
        int index = getIndex(key);
        for (int i = index; ; i++) {
            if(i == capacity) i = 0;
            if(table[i] == null) {
                table[i] = new Entry(key,value);
                size++;
                return true;
            }
            if (table[i].getKey() == key){
                if(table[i].getValue() == value)
                    return true;
                throw new RuntimeException("Key is already is use!!!");
            }

        }
    }

    // возвращает значение по ключу
    public long get(int key) {
        for (int i = getIndex(key); ; i++) {
            if(i == capacity) i = 0;
            if(table[i] == null) throw new NoSuchElementException("No such key! -> [" + key + "]");
            if (table[i].getKey() == key) return table[i].getValue();
        }
    }

    // возвращает количество элементов в карте
    public int size() {
        return size;
    }

    // увеличение размера карты
    private void resize(){
        capacity*=multiplier;
        Entry[] oldTable = table;
        table = new Entry[capacity];
        size = 0;

        for(Entry e : oldTable)
            if(e != null)
                put(e.getKey(),e.getValue());
    }

    // возвращает номер позиции по значению хэш-функции
    private int getIndex(int key){
        return hash(key) % capacity;
    }

    // хэш-функция
    private int hash(int hash){
        if (hash == 0) return 0;
        return Objects.hashCode(hash);
    }

    public boolean containsKey(int key){
        for (int i = getIndex(key); ; i++) {
            if(i == capacity) i = 0;
            if(table[i] == null) return false;
            if (table[i].getKey() == key) return true;
        }
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
