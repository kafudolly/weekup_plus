package homework.file.huffman;

import java.util.HashMap;

import javax.sql.rowset.serial.SerialException;

public class CompressionFile extends SerialException{
    public HashMap<Byte, Integer> timesMap = new HashMap<>();
    public byte[] data;
    public long byteNum;

    public CompressionFile(int dataNum, int byteNum){
        this.data = new byte[dataNum];
        this.byteNum = byteNum;
    }
}