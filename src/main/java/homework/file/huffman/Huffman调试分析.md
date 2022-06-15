### 哈夫曼编码调试分析

1. 读取文件时使用缓冲区提高文件读取速度，而FileInputStream.read()方法在文件剩余内容小于缓冲区容量时末端数据出现重复。因此使用该方法返回值进行缓冲区有效数据判定。

   ```java
    byte[] bytes = new byte[16];
               while((num = fileInputStream.read(bytes)) != -1){
                   getTimes(bytes, num);
               }
   ```

2. 字符频数一样时出现编码错误，创建左子树时忘记赋权值

3. 读取文件频数出现困难，遂改为创建CompressionFile类，存储后将类通过ObjectOutputStream输出

4. 压缩文件缓冲区利用出现错误，缓冲区判满应在while循环结束后，错误写在每次while循环内，导致缓冲区读取结束后进行多余写入
    修改前：
    ```java
        while((size = fis.read(buffer)) != -1){
            //压缩操作
            if(num != 8){
            binaryStr = binaryStr << (8 - num);
            data[dataNum] = (byte) (binaryStr & 0xFF);
            dataNum ++;
            }
        }
    ```
    修改后：
    ```java
        while((size = fis.read(buffer)) != -1){
            //压缩操作
        }
        if(num != 8){
            binaryStr = binaryStr << (8 - num);
            data[dataNum] = (byte) (binaryStr & 0xFF);
            dataNum ++;
        }
    ```

