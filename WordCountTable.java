/**
 * Created by Megan on 12/7/16.
 */


public class WordCountTable {

    int size = 0;
    int capacity = 16;
    String[] keys = new String[capacity];
    int[] values = new int[capacity];


//    public static void main(String[] args) {

//
//        WordCountTable wc = new WordCountTable();
//
//        wc.set("Aa", 1);
//        wc.set("BB", 1);
//
//        String s = "Aa";
//        String t = "BB";
//        for (int i = 2; i < 10; i++) {
//            s += "Aa";
//            t += "BB";
//            wc.set(s, i);
//            wc.set(t, i);
//        }
//
//        System.out.println(wc.get("Aa"));
//    }

    /**
     * Constructs an empty table
     */
    public WordCountTable() {
    }

    /**
     * inserts the key-value pair (key,value) into the table, overwriting
     * any previous pair of the form (key,*) if present
     *
     * @param key   the key
     * @param value the value
     */
    public void set(String key, int value) {


        if (size >= capacity / 2) {

            String[] oldKeys = keys;
            int[] oldValues = values;

            capacity = capacity * 2;
            keys = new String[capacity];
            values = new int[capacity];
            size = 0;

            for (int i = 0; i < oldKeys.length; i++) {

                if (oldKeys[i] != null && oldValues[i] != 0) {
                    set(oldKeys[i], oldValues[i]);
                }
            }
        }

        int index = ((key.hashCode() % capacity) + capacity) % capacity;

        while (keys[index] != null && !keys[index].equals(key)) {

            index = (index + 1) % capacity;
        }

        if (keys[index] == null) size++;

        keys[index] = key;
        values[index] = value;

    }

    /**
     * gets the Value corresponding to the Key, key
     *
     * @param key the key
     * @returns the associated value or zero if no value is associated to key
     */
    public int get(String key) {

        int index = ((key.hashCode() % capacity) + capacity) % capacity;
        int count = 0;

        while (keys[index] != null && !keys[index].equals(key) && count < capacity) {
            index = (index + 1) % capacity;
            count++;
        }

        if (count >= capacity || keys[index] == null) return 0;

        return values[index];

    }

    /**
     * determines if the symbol table contains a key-value pair with Key equal
     * to key
     *
     * @param key the key to test for
     * @returns true if such a key-value pair is present, false otherwise
     */
    public boolean containsKey(String key) {
        return get(key) != 0;
    }

}
