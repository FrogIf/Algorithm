package sch.frog.algorithm;

import java.io.*;
import java.util.Arrays;

/**
 * 类似 - 赫弗曼压缩
 */
public class HuffmanCompress {

    public static void main(String[] arr) throws IOException {
        compress("F:\\test.xlsx", "F:\\output.frogc");
        decompress("F:\\output.frogc", "F:\\yyyy.frogdc");
    }

    private static void decompress(String inputPath, String outputPath) throws IOException {
        InputStream inputStream = new FileInputStream(inputPath);
        File file = new File(outputPath);
        OutputStream outputStream = new FileOutputStream(file, false);
        decompress(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
    }

    private static void decompress(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] codeTable = new byte[256];
        int len = inputStream.read(codeTable);
        if(len != 256){
            throw new IllegalArgumentException("文件格式不正确");
        }
        int[] table = new int[256];
        for(int i = 0; i < 256; i++){
            int a = codeTable[i];
            if(a < 0){
                table[a + 256] = i;
            }else{
                table[a] = i;
            }
        }

        System.out.println(Arrays.toString(table));
        byte[] buf = new byte[1024];
        int oc = 0;    // 已经存在的1个数, 最大为255
        byte[] cache = new byte[1024];
        int cacheIndex = 0;

        while((len = inputStream.read(buf)) != -1){
            for(int i = 0; i < len; i++){
                int b = buf[i];
                if(b < 0){
                    b = b + 256;
                }
                if(b == 255){ // 11111111
                    oc += 8;
                    continue;
                }

                int zerocase = 128;
                while(zerocase > 0){
                    if((b & zerocase) == 0){
                        cache[cacheIndex++] = (byte) table[oc];
                        if(cacheIndex == 1024){
                            outputStream.write(cache);
                            cacheIndex = 0;
                        }
                        oc = 0;
                    }else{
                        oc++;
                    }
                    zerocase = zerocase >> 1;
                }
            }
        }

        if(cacheIndex > 0){
            outputStream.write(cache, 0, cacheIndex);
        }
    }

    private static void compress(String inputPath, String outputPath) throws IOException{
        InputStream inputStream = new FileInputStream(inputPath);
        int[] freq = frequencyStatistic(inputStream);   // 统计出现频率
        inputStream.close();
        int[][] freqHeap = buildMaxHeap(freq);// 构建最大堆

        int[] codes = generateCodeTable(freqHeap);  // 该codes尺寸一定为256
        inputStream = new FileInputStream(inputPath);
        File output = new File(outputPath);
        OutputStream outputStream = new FileOutputStream(output, false);
        compress(inputStream, outputStream, codes); // 压缩
        outputStream.close();
        inputStream.close();
    }


    private static void compress(InputStream inputStream, OutputStream outputStream, int[] codes) throws IOException {
        byte[] codeTable = new byte[codes.length];
        for(int i = 0; i < codes.length; i++){
            codeTable[i] = (byte) codes[i]; // codes最大值为255
        }
        outputStream.write(codeTable);  // 将字码表写入压缩文件中

        byte[] buf = new byte[1024];
        int len;
        int res = 8;    // 余下多少个空位
        byte[] cache = new byte[1024];
        int cacheIndex = 0;
        byte temp = 0;
        while((len = inputStream.read(buf)) != -1){
            for(int i = 0; i < len; i++){
                int a = buf[i];
                if(a < 0){
                    a = a + 256;
                }
                int n = codes[a];  // 获取有多少个前置1
                if(n > res){
                    n -= res;
                    temp |= (0xff >> (8 - res));
                    cache[cacheIndex++] = temp;
                    temp = 0;
                    if(cacheIndex == 1024){
                        outputStream.write(cache);
                        cacheIndex = 0;
                    }
                    res = 8;
                }

                while(n >= 8){
                    cache[cacheIndex++] = (byte) 0xff;
                    if(cacheIndex == 1024){
                        outputStream.write(cache);
                        cacheIndex = 0;
                    }
                    n -= 8;
                }

                if(n > 0){
                    res -= n;   // 此时, 一定是n < res的
                    temp |= 0xff >> (8 - n) << res;
                    if(res == 0){
                        res = 8;
                        cache[cacheIndex++] = temp;
                        temp = 0;
                        if(cacheIndex == 1024){
                            outputStream.write(cache);
                            cacheIndex = 0;
                        }
                    }
                }

                res--;  // byte中加入一个0
                if(res == 0){
                    res = 8;
                    cache[cacheIndex++] = temp;
                    temp = 0;
                    if(cacheIndex == 1024){
                        outputStream.write(cache);
                        cacheIndex = 0;
                    }
                }
            }
        }

        if(res < 8){
            cache[cacheIndex++] = (byte) ((255 >> 8 - res) | temp);
        }

        if(cacheIndex > 0){
            outputStream.write(cache, 0, cacheIndex);
        }
    }


    private static int[] generateCodeTable(int[][] freq){
        int[] codes = new int[256]; // 记录0前有多少个1

        for(int i = 0; i < codes.length; i++){
            int[] info = popMaximum(freq, codes.length - i);
            codes[info[0]] = i;
        }

        return codes;
    }

    // 统计一个文件中各个字节出现的频率
    private static int[] frequencyStatistic(InputStream inputStream) throws IOException {
        int[] freq = new int[256];  // 记录每一种字节出现的次数, 使用int统计, 可以存储重复数据上限为2 ^ 31 byte, 即2Gb重复字符

        byte[] buf = new byte[1024];
        int len;
        while((len = inputStream.read(buf)) != -1){
            for(int i = 0; i < len; i++){
                int a  = buf[i];
                if(a < 0){
                    a = a + 256;
                }
                freq[a]++;
            }
        }

        return freq;
    }

    // 取出最大堆中的最大元素
    private static int[] popMaximum(int[][] arr, int heapSize){
        int[] min = arr[0];
        arr[0] = arr[--heapSize];
        maxHeapify(arr, 0, heapSize);
        return min;
    }

    // 构建最大堆, 返回一个二维数组, 第二个维度中 : index0 - 字节, index1 - 表示频率
    private static int[][] buildMaxHeap(int[] arr){
        int[][] result = new int[arr.length][];
        for(int i = 0; i < arr.length; i++){
            result[i] = new int[]{i, arr[i]};
        }
        for(int i = result.length >> 1; i >= 0; i--){
            maxHeapify(result, i, result.length);
        }
        return result;
    }

    // 最大堆化
    private static void maxHeapify(int[][] arr, int i, int heapSize){
        int l = (i << 1) + 1;
        int r = l + 1;

        int largest = i;
        if(l < heapSize && arr[largest][1] < arr[l][1]){
            largest = l;
        }

        if(r < heapSize && arr[largest][1] < arr[r][1]){
            largest = r;
        }

        if(largest != i){
            int[] temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            maxHeapify(arr, largest, heapSize);
        }
    }

}
