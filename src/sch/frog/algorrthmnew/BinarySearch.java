package sch.frog.algorrthmnew;

public class BinarySearch {

    public static void main(String[] args){
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 10};
        System.out.println(binarySearch(arr, 5));
    }


    private static int binarySearch(int[] arr, int aim){
        int start = 0;
        int end = arr.length - 1;
        while(start <= end){
            int mid = start + ((end - start) >> 1);
            int a = arr[mid];
            if(a == aim){
                return mid;
            }else if(a < aim){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
        }
        return -1;
    }

}
