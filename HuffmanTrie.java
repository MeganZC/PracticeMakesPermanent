/**
 * Created by Megan on 11/21/16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A binary trie implementation of Huffman codes
 *
 * @author <a href="mailto:knapp@american.edu">Adam Knapp</a>
 * @version 1.0
 */
public class HuffmanTrie {

    static HashMap<Character, Integer> hashMap = new HashMap<>();
    static ArrayList<Character> dataStorage = new ArrayList<>();

    private HuffmanNode root;

    /**
     * private default constructor so that this class cannot be instantiated except via the
     * <code>buildTrie</code> method or the <code>readBinaryRepresentation</code>
     */
    private HuffmanTrie() {
    }

    /**
     * Compresses or decompresses standard in to standard out according to command line argument
     *
     * @param args specifies compression (c) or decompression (d)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Provide a single command line argument to say if");
            System.out.println("I should be compressing (c) or decompressing (d).");
            return;
        }
        switch (args[0]) {
            case "c":
                compressStdIn();
                break;
            case "d":
                decompressStdIn();
                break;
            default:
                System.out.println("Invalid argument");
        }
    }

    /**
     * Decompress standard input to standard output
     */
    private static void decompressStdIn() {


        HuffmanTrie trie = readBinaryRepresentation();
        char c = trie.readChar();
        do {
            System.out.print(c);
            c = trie.readChar();
        } while (c != '\0');
    }

    /**
     * Compress standard input to standard output
     */
    private static void compressStdIn() {

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            dataStorage.add(c);
            Integer val = hashMap.get(c);

            if (val != null) {
                hashMap.put(c, val + 1);
            } else {
                hashMap.put(c, 1);
            }
        }

        dataStorage.add('\0');
        Integer val = hashMap.get('\0');

        if (val != null) {
            hashMap.put('\0', val + 1);
        } else {
            hashMap.put('\0', 1);
        }

        char[] characters = new char[hashMap.size()];
        int[] charCounts = new int[hashMap.size()];

        int index = 0;
        for (char c : hashMap.keySet()) {
            characters[index] = c;
            charCounts[index] = hashMap.get(c);
            index++;
        }

        HuffmanTrie trie = HuffmanTrie.buildTrie(characters, charCounts);

        Map<Character, String> map = trie.getCode();

        trie.writeBinaryRepresentation();

        for (char c: dataStorage) {
            String string = map.get(c);
            for (int i = 0; i < string.length() ; i++) {
                if(string.charAt(i) == '0') BinaryStdOut.write(false);
                else BinaryStdOut.write(true);
            }
        } BinaryStdOut.flush();
    }

    /**
     * Constructs a HuffmanTrie based on the characters and counts given
     *
     * @param characters an array of characters found in the given data file, each character should appear at most once
     * @param charCounts an array of counts of characters such that <code>charCounts[i]</code> is the number of
     *                   occurrences of the character <code>characters[i]</code>.
     * @return a new instance of <code>HuffmanTrie</code> with tree structure given by Huffman's algorithm.
     */

    public static HuffmanTrie buildTrie(char[] characters, int[] charCounts) {
        assert characters != null && charCounts != null && characters.length == charCounts.length;

        HuffmanTrie trie = new HuffmanTrie();
        trie.buildTrieFromCounts(characters, charCounts);

        return trie;

    }

    private void buildTrieFromCounts(char[] characters, int[] charCounts) {

        PriorityQueue<HuffmanNode> q = new PriorityQueue<>();

        for (char i = 0; i < hashMap.size(); i++)
            if (charCounts[i] > 0) {
                q.add(new HuffmanNode(characters[i], charCounts[i]));
            }

        while (q.size() > 1) {
            q.add(new HuffmanNode(q.remove(), q.remove()));
        }
        root = q.remove();
    }


    /**
     * Creates an new instance of <code>HuffmanTrie</code> by reading standard input using <code>BinaryStdIn</code>
     *
     * @return a new instance of <code>HuffmanTrie</code>
     */
    public static HuffmanTrie readBinaryRepresentation() {

        HuffmanTrie trie = new HuffmanTrie();

        trie.root = trie.getNode();
        return trie;
    }

    private HuffmanNode getNode() {

        if (BinaryStdIn.readBoolean()) return new HuffmanNode(BinaryStdIn.readChar());
        else return new HuffmanNode(getNode(), getNode());
        }
    /**
     * Returns a <code>Map</code> giving Huffman code determined by this trie. Code words are represented by Strings of
     * 0's and 1's.
     *
     * @return <code>Map</code> giving Huffman code determined by this trie
     */
    public Map<Character, String> getCode() {

        Map<Character, String> code = new HashMap<>();
        root.fillInCode(code, "");
        return code;
    }

    /**
     * Reads an encoded character from standard input using <code>BinaryStdIn</code>
     *
     * @return decoded character. We let '\0' denote a special end-of-file character. The file _should_ end after this
     * character is returned and <code>readChar()</code> should not be called again.
     */
    public char readChar() {

        return root.readChar();
    }

    /**
     * Writes the binary representation of <code>c</code> to standard output using <code>BinaryStdOut</code>
     *
     * @param c character to write
     */
    public void writeChar(char c) {

    //don't really need this, already in other method
    }

    /**
     * @return String representation of the trie via its pre-order traversal
     */

    // don't really need this, already returns a proper string


    /**
     * Write the binary representation of this trie to standard output using <code>BinaryStdOut</code>
     */
    public void writeBinaryRepresentation() {

        root.writeRep();
    }

    /**
     * Inner class for nodes of the binary trie
     */
    private class HuffmanNode implements Comparable<HuffmanNode> {
        char data;
        int count;
        HuffmanNode left, right;

        public HuffmanNode(HuffmanNode l, HuffmanNode r) {
            left = l;
            right = r;
            count = l.count + r.count;
        }

        public HuffmanNode(char c) {
            data = c;
        }

        public HuffmanNode(char character, int charCount) {
            this.data = character;
            this.count = charCount;
        }

        /**
         * @return <code>true</code> is <code>this</code> is a leaf (i.e. no children) Otherwise, <code>false</code>
         */

        boolean isLeaf() {

            if (this.left == null && this.right == null) {
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(HuffmanNode o) {

            return o.count - this.count; // + if this is less, - if more
        }

        /**
         * populates <code>Map</code> of codewords recursively
         *
         * @param codeWords <code>Map</code> to populate
         * @param codeSoFar <code>String</code> containing prefix for all codewords under this node.
         */
        void fillInCode(Map<Character, String> codeWords, String codeSoFar) {

            if (this.isLeaf()) codeWords.put(data, codeSoFar);
            else {
                assert left != null && right != null;
                left.fillInCode(codeWords, codeSoFar + '0');
                right.fillInCode(codeWords, codeSoFar + '1');
            }
        }

        public void writeRep() {

            if (this.isLeaf()) {
                BinaryStdOut.write(true);
                BinaryStdOut.write(data);
            } else {
                BinaryStdOut.write(false);
                left.writeRep();
                right.writeRep();
            }
        }

        public char readChar() {

            if (this.isLeaf()) return this.data;
            else {
                if (BinaryStdIn.readBoolean()) return right.readChar();
                else return left.readChar();
                }
            }
        }

    }
