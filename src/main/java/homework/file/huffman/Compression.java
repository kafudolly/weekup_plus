package homework.file.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Compression {
    private final int BUFFER_SIZE = 1<<10;    //缓冲区大小
    private Node root;   //根节点
    private int byteNum; //字节数
    private final HashMap<Byte, String> codeMap = new HashMap<>();   //编码表
    private final HashMap<Byte, Integer> timesMap = new HashMap<>();   //出现次数表


    //创建哈夫曼树
    private void buildHuffmanTree(){
        Node leftChild;
        Node rightChild;
        Set<Byte> keySet = timesMap.keySet();
        List<Node> list = new ArrayList<>();
        //遍历字节频数，为每个字节创建叶子节点
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
        root =  list.get(0);
    }

    //生成哈夫曼编码，递归实现
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

    //获取字节频数
    private void getTimes(File inputFile){
        try{
            int num;    
            FileInputStream fis = new FileInputStream(inputFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            while((num = fis.read(buffer)) != -1){
                for(int i = 0; i < num; i++){
                    byte bt = buffer[i];
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
            fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //压缩文件
    public int huffmanCompress(String filePath, String newPath) throws IOException{
        File inputFile = new File(filePath);
        File outputFile = new File(newPath);
        getTimes(inputFile);
        buildHuffmanTree();
        buildHuffmanCode(root);
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
        
        return 0;    
    }
}
