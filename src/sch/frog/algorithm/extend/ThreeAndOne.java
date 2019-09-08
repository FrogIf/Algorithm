package sch.frog.algorithm.extend;

public class ThreeAndOne {


    public static void main(String[] args){
        System.out.println(threeAndOne(27));
    }

    /**
     * 3n+1问题<br/>
     * 返回总共结果多少次计算才归一
     * @param x 需要归一的数字
     * @return 经过多少次归一
     */
    public static int threeAndOne(int x){
        int count = 0;  // 总共执行的次数
        while(x != 1){
            if(x % 2 == 1){
                x = 3 * x + 1;
            }else{
                x = x / 2;
            }
            count++;
        }
        return count;
    }

}
