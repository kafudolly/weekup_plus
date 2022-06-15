package Middleware;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringTools {
    public static String FileToString(File file){
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileContent);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(fileContent);
    }

    public static String RandomNumber(){
        String s = new SimpleDateFormat("yyMMdd").format(new Date());
        StringBuilder str = new StringBuilder();
        str.append(s);
        Random random = new Random();
        int min = 10000000;
        int max = 99999999;
        int sum = random.nextInt(max)%(max-min+1) + min;
        str.append(sum);
        return str.toString();
    }

    public static boolean matchString(String target, String mode) {
        //为了和算法保持一致，使index从1开始，增加一前缀
        String newTarget = "x" + target;
        String newMode = 'x' + mode;

        int[] K = calculateK(mode);

        int i = 1;
        int j = 1;
        while (i <= target.length() && j <= mode.length()) {
            if (j == 0 || newTarget.charAt(i) == newMode.charAt(j)) {
                i++;
                j++;
            } else {
                j = K[j];
            }
        }

        return j > mode.length();
    }

    /*
     * 计算K值
     */
    private static int[] calculateK(String mode) {
        //为了和算法保持一致，使index从1开始，增加一前缀
        String newMode = "x" + mode;
        int[] K = new int[newMode.length()];
        int i = 1;
        K[1] = 0;
        int j = 0;

        while (i < mode.length()) {
            if (j == 0 || newMode.charAt(i) == newMode.charAt(j)) {
                i++;
                j++;
                K[i] = j;
            } else {
                j = K[j];
            }
        }

        return K;
    }


}

