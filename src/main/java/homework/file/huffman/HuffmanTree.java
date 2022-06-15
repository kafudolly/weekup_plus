package homework.file.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HuffmanTree{
    private final int BUFFER_SIZE = 1<<10;
    private Node root;   //根节点
    private int byteNum;
    private final HashMap<Byte, String> codeMap = new HashMap<>();   //编码表
    private HashMap<Byte, Integer> timesMap = new HashMap<>();   //出现次数表

    private void getTimes(byte[] bytes,int num){
        for(int i = 0; i < num; i++){
            byte bt = bytes[i];
            byteNum ++;
            if(timesMap.containsKey(bt)){
                int value = timesMap.get(bt);
                timesMap.put(bt, ++value);
            }
            else{
                timesMap.put(bt, 1);
            }
        }
    }

    private Node buildHuffmanTree(){
        Node leftChild;
        Node rightChild;
        Set<Byte> keySet = timesMap.keySet();
        List<Node> list = new ArrayList<>();

        for(byte key : keySet){
            int value = timesMap.get(key);
            list.add(new Node(key, value));
        }

        while(list.size() != 1){
            Collections.sort(list);
            leftChild = list.remove(0);
            leftChild.setCode("0");
            rightChild = list.remove(0);
            rightChild.setCode("1");
            Node node = new Node(leftChild.getValue() + rightChild.getValue(), leftChild, rightChild);
            list.add(node);
        }
        return list.get(0);
    }
    
    private void buildHuffmanCode(Node root) {
        if (root.getLeftChild() != null) {
            //root.getLeftChild().getCode()是现在正在遍历节点的编码，root.getCode()是之前的编码，两者要相加
            root.getLeftChild().setCode(root.getCode() + root.getLeftChild().getCode());
            buildHuffmanCode(root.getLeftChild());
        }
        if (root.getRightChild() != null) {
            root.getRightChild().setCode(root.getCode() + root.getRightChild().getCode());
            buildHuffmanCode(root.getRightChild());
        }
        if(root.getLeftChild() == null && root.getRightChild() == null){
            codeMap.put(root.getByte(), root.getCode());
        }
    }

    private void HuffmanEncoding(File file){
        try{
            int num;
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[BUFFER_SIZE];
            while((num = fileInputStream.read(bytes)) != -1){
                getTimes(bytes, num);
            }
            fileInputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root = buildHuffmanTree();
        buildHuffmanCode(root);
    }

    public void HuffmanCompress(File inputFile, String newPath) throws Exception{
        HuffmanEncoding(inputFile);
        File outputFile = new File(newPath);
        byte[] buffer = new byte[BUFFER_SIZE];
        byte[] data = new byte[byteNum];
        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);
        // FileOutputStream obj = new FileOutputStream("test.txt");
        // fos.write(timesMap.toString().getBytes());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        // objectOutputStream.writeObject(timesMap);
        int size = 0;
        int binaryStr = 0;
        int num = 0;
        int dataNum = 0;
        while((size = fis.read(buffer)) != -1){
            for(int i = 0; i < size; i ++){
                String str = codeMap.get(buffer[i]);
                char[] strbyte = str.toCharArray();
                for(char c : strbyte){
                    if(c == '0'){
                        binaryStr = (binaryStr << 1);
                    }
                    else if(c == '1'){
                        binaryStr = (char) (binaryStr << 1);
                        binaryStr ++ ;
                    }
                    num++;
                    if(num == 8){
                        
                        // fos.write(binaryStr & 0xFF);
                        data[dataNum] = (byte) (binaryStr & 0xFF);
                        dataNum ++;
                        num = 0;
                        binaryStr = 0;
                    }
                }
            }            
        }
        if(num != 8){
            binaryStr = binaryStr << (8 - num);
            // fos.write(binaryStr & 0xFF);
            data[dataNum] = (byte) (binaryStr & 0xFF);
            dataNum ++;
        }
        CompressionFile newFile = new CompressionFile(dataNum, byteNum);
        newFile.timesMap = timesMap;
        System.arraycopy(data, 0, newFile.data, 0, dataNum);
        objectOutputStream.writeObject(newFile);
        fis.close();
        fos.close();
    }

    public void HuffmanDecompress(File inputFile, File outputFile){
        try {
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            CompressionFile zipFile = (CompressionFile) objectInputStream.readObject();
            timesMap = zipFile.timesMap;
            root = buildHuffmanTree();
            int binaryCode = 0;
            Node testNode = root;
            int testNum = 0;
            for(int i = 0; i < zipFile.data.length; i ++){
                byte bt = zipFile.data[i];
                for(int j = 7; (j >= 0) && testNum < zipFile.byteNum; j --){
                    binaryCode = bt << (Integer.SIZE - j - 1) >>> (Integer.SIZE - 1);
                    if(binaryCode == 0){
                        testNode = testNode.getLeftChild();
                    }
                    else{
                        testNode = testNode.getRightChild();
                    }
                    if(testNode.getLeftChild() == null &&testNode.getRightChild() == null){
                        // System.out.println((char)testNode.getCh());
                        fos.write(testNode.getByte());
                        testNode = root;
                        testNum ++;
                    }
                }
                
            }

            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws IOException, Exception{
//        HuffmanTree huffmanTree = new HuffmanTree();
//        huffmanTree.huffmanCompress("D:/Study/DataStruct/weekup_plus/demo/test.txt","D:/Study/DataStruct/weekup_plus/demo/after.txt");
//        huffmanTree.huffmanDecompress("D:/Study/DataStruct/weekup_plus/demo/after.txt","D:/Study/DataStruct/weekup_plus/demo/unzip.txt");
//    }
}
