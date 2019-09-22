package sch.frog.algorithm;

import java.util.Arrays;

public class SelectByOrder {

    public static void main(String[] args){
        int[] arr = new int[]{23, 34, 1, 56, 23, 6, 23, 356, 8, 23, 23, 5, 9, 2, 4};
        int aim = 4;
        System.out.println(selectByOrder(arr, 0, arr.length - 1, aim));
        System.out.println(fastSelectByOrder(arr, 0, arr.length - 1, aim));
        insertSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }



    private static int fastSelectByOrder(int[] arr, int start, int end, int order){
        if(start == end){
            return arr[start];
        }else{
            int len = (end - start + 1);
            int last = len % 5;
            int[] groupMid = new int[len / 5 + (last > 0 ? 1 : 0)];
            int j = 0;

            for(int i = start; i < end - last; i += 5){
                insertSort(arr, i, i + 4);
                groupMid[j] = arr[i + 2];
                j++;
            }

            if(last > 0){
                insertSort(arr, end - last, end);
                groupMid[groupMid.length - 1] = arr[end - last + last / 2 + 1];
            }

            int pivot = fastSelectByOrder(groupMid, 0, groupMid.length - 1, groupMid.length / 2);
            int p = start;
            for(int i = start; i <= end; i++){
                if(arr[i] == pivot){
                    p = i;
                    break;
                }
            }

            arr[p] = arr[start];

            int tempStart = start;
            int tempEnd = end;
            while(start < end){
                while(start < end && arr[end] > pivot){
                    end--;
                }
                if(start < end){
                    arr[start] = arr[end];
                    start++;
                }
                while(start < end && arr[start] < pivot){
                    start++;
                }
                if(start < end){
                    arr[end] = arr[start];
                    end--;
                }
            }

            arr[start] = pivot;

            int pos = start - tempStart;
            if(pos == order){
                return pivot;
            }else if(pos > order){
                return fastSelectByOrder(arr, tempStart, start - 1, order);
            }else{
                return fastSelectByOrder(arr, start + 1, tempEnd, order - pos - 1);
            }
        }
    }

    private static void insertSort(int[] arr, int start, int end){
        for(int i = start + 1; i < end; i++){
            int base = arr[i];
            int j = i - 1;
            for(; j >= 0; j--){
                if(arr[j] < base){ break; }
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = base;
        }
    }

    /**
     * 期望运行时间为O(n)
     */
    private static int selectByOrder(int[] arr, int start, int end, int order){
        if(start == end){
            return arr[start];
        }else{
            int tempStart = start;
            int tempEnd = end;
            int pivot = arr[start];
            while(start < end){
                while(start < end && arr[end] > pivot){
                    end--;
                }
                if(start < end){
                    arr[start] = arr[end];
                    start++;
                }
                while(start < end && arr[start] < pivot){
                    start++;
                }
                if(start < end){
                    arr[end] = arr[start];
                    end--;
                }
            }
            arr[start] = pivot;
            int offset = start - tempStart;
            if(offset == order){
                return pivot;
            }else if(offset < order){
                return selectByOrder(arr, start + 1, tempEnd, order - offset - 1);
            }else{
                return selectByOrder(arr, tempStart, start - 1, order);
            }
        }
    }

}
