package homework.file.huffman;

public class Node implements Comparable<Node>{
    private byte bt;//存储字符
    private int value;//权值
    private Node leftChild;//左子节点
    private Node rightChild;//右子节点
    private String code = "";//字符对应哈夫曼编码

    public Node(byte bt, int value){
        this.bt = bt;
        this.value = value;
    }

    public Node(int value, Node leftChild, Node rightChild){
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public byte getByte(){
        return bt;
    }

    public void setByte(byte bt){
        this.bt = bt;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString(){
        return "Node{"+
                "bt="+bt+
                ",value="+value+
                ",leftChild="+leftChild+
                ",rightChild="+rightChild+
                "}";
    }

    
    @Override
    public int compareTo(Node o) {
        int result = this.getValue() - o.getValue();
        return result;
    }
}
