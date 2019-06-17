package main.com.tec.MILIB_DB.domain;

// Libraries
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.*;
import org.jdom2.output.*;

import java.io.*;

/**
 * Tree Node
 * @author Juan Pablo Alvarado
 */
class Node
{
    char ch;
    int freq;
    Node left = null, right = null;

    Node(char ch, int freq)
    {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(char ch, int freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
};

/**
 * Implements an manual Comparator to avoid the use of lambda functions
 * @author Esteban Alvarado
 */
class NodeComparator implements Comparator<Node>{
    @Override
    public int compare(Node nodeA, Node nodeB){
        return (nodeA.freq - nodeB.freq);
    }
}

/**
 * Encodes and decodes based on Huffman algorithm
 * @author Juan Pablo Alvarado
 * @author Esteban Alvarado (co-author)
 */
public class Huffman {


    private static Node GlobalRoot;
    private static String file_path="/home/juan/Documentos/Proyecto3/Servidor MetaData/XML_Metadata/input.xml";
    private static String path = file_path.substring(0, file_path.length() - 9);
    private static String GlobalDecoded="";

    public static Node getGlobalRoot() {
        return GlobalRoot;
    }

    // traverse the Huffman Tree and store Huffman Codes
    // in a map.

    public static void encode(Node root, String str,
                              Map<Character, String> huffmanCode)
    {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.ch, str);
        }


        encode(root.left, str + "0", huffmanCode);
        encode(root.right, str + "1", huffmanCode);
    }

    // traverse the Huffman Tree and decode the encoded string
    public static int decode(Node root, int index, StringBuilder sb)
    {
        if (root == null)
            return index;

        // found a leaf node
        if (root.left == null && root.right == null)
        {
            //System.out.print(root.ch);
            GlobalDecoded+=root.ch;
            return index;
        }

        index++;

        if (sb.charAt(index) == '0')
            index = decode(root.left, index, sb);
        else
            index = decode(root.right, index, sb);

        return index;
    }


    // Builds Huffman Tree and huffmanCode and decode given input text
    public static void buildHuffmanTree (String text)
    {
        // count frequency of appearance of each character
        // and store it in a map
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }

        // Create a priority queue to store live nodes of Huffman tree
        // Notice that highest priority item has lowest frequency
        //PriorityQueue<Node> pq = new PriorityQueue<>((l, r) -> l.freq - r.freq);
        int initCap = freq.size();
        PriorityQueue<Node> pq = new PriorityQueue<>(initCap,new NodeComparator());

        // Create a leaf node for each character and add it
        // to the priority queue.
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // do till there is more than one node in the queue
        while (pq.size() != 1)
        {
            // Remove the two nodes of highest priority
            // (lowest frequency) from the queue
            Node left = pq.poll();
            Node right = pq.poll();

            // Create a new internal node with these two nodes as children
            // and with frequency equal to the sum of the two nodes
            // frequencies. Add the new node to the priority queue.
            int sum = left.freq + right.freq;
            pq.add(new Node('\0', sum, left, right));
        }

        // root stores pointer to root of Huffman Tree
        Node root = pq.peek();

        // traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // print the Huffman codes
        System.out.println("Huffman Codes are :\n");
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nOriginal string was :\n" + text);

        // print encoded string
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < text.length(); i++) {
            sb.append(huffmanCode.get(text.charAt(i)));
        }

        System.out.println("\nEncoded string is :\n" + sb);

        // traverse the Huffman Tree again and this time
        // decode the encoded string
        int index = -1;
        System.out.println("\nDecoded string is: \n");
        while (index < sb.length() - 2) {
            index = decode(root, index, sb);
        }
    }


    static Document SaveTreeXML(Node root){
        Document doc=new Document();
        Element el = new Element("root");
        doc.setRootElement(el);

        SaveTreeXMLRecursive(root,el);

        return doc;
    }

    static void SaveTreeXMLRecursive(Node root, Element el){
        el.setAttribute("freq",root.freq+"");

        if (root.left == null && root.right == null) {
            el.setAttribute("char",root.ch+"");
        }

        if (root.left!=null){
            Element tmp= new Element("leftChild");
            el.addContent(tmp);
            SaveTreeXMLRecursive(root.left,tmp);
        }

        if (root.right!=null){
            Element tmp= new Element("rightChild");
            el.addContent(tmp);
            SaveTreeXMLRecursive(root.right,tmp);
        }

    }

    static String EncodeString(String text){
        // count frequency of appearance of each character
        // and store it in a map
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }

        // Create a priority queue to store live nodes of Huffman tree
        // Notice that highest priority item has lowest frequency
        int initCap = freq.size();
        PriorityQueue<Node> pq = new PriorityQueue<>(initCap,new NodeComparator());

        // Create a leaf node for each character and add it
        // to the priority queue.
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // do till there is more than one node in the queue
        while (pq.size() != 1)
        {
            // Remove the two nodes of highest priority
            // (lowest frequency) from the queue
            Node left = pq.poll();
            Node right = pq.poll();

            // Create a new internal node with these two nodes as children
            // and with frequency equal to the sum of the two nodes
            // frequencies. Add the new node to the priority queue.
            int sum = left.freq + right.freq;
            pq.add(new Node('\0', sum, left, right));
        }

        // root stores pointer to root of Huffman Tree
        Node root = pq.peek();

        // traverse the Huffman tree and store the Huffman codes in a map
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // print encoded string
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < text.length(); i++) {
            sb.append(huffmanCode.get(text.charAt(i)));
        }

        GlobalRoot=root;
        return sb.toString();
    }

    public static void EncodeFile(){
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(file_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text=new String(encoded, Charset.defaultCharset());
        String sb=EncodeString(text);

        try (PrintWriter out = new PrintWriter(path+"Huffman.txt")) {
            out.println(sb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Document doc= SaveTreeXML(GlobalRoot);

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlOutput.output(doc, new FileWriter(path +"HuffmanTree.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(file_path)) {
            out.println("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    static void GetTreeXML(Node root) {
        File inputFile = new File(path+"HuffmanTree.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        Element el  = document.getRootElement();
        GetTreeXMLRecursive(root,el);
    }

    static void GetTreeXMLRecursive(Node root, Element el){
        root.freq=Integer.parseInt(el.getAttributeValue("freq"));

        if (el.getChild("leftChild") == null && el.getChild("rightChild") == null) {
            root.ch=el.getAttributeValue("char").charAt(0);
        }

        if (el.getChild("leftChild")!=null){
            Node tmp= new Node(' ',0);
            root.left=tmp;
            GetTreeXMLRecursive(tmp,el.getChild("leftChild"));
        }

        if (el.getChild("rightChild")!=null){
            Node tmp= new Node(' ',0);
            root.right=tmp;
            GetTreeXMLRecursive(tmp,el.getChild("rightChild"));
        }
    }

    public static void DecodeFile(){
        Node root= new Node(' ',0);
        GetTreeXML(root);

        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path+"Huffman.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sb=new String(encoded, Charset.defaultCharset());

        GlobalDecoded="";
        int index = -1;
        //System.out.println("\nDecoded string is: \n");
        while (index < sb.length() - 2) {
            index = decode(root, index, new StringBuilder().append(sb));
        }
        //System.out.println(GlobalDecoded);

        try (PrintWriter out = new PrintWriter(file_path)) {
            out.println(GlobalDecoded);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //EncodeFile();

        DecodeFile( );
    }

}