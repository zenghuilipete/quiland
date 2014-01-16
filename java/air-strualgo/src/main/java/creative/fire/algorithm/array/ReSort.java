package creative.fire.algorithm.array;

/**
 * Created by hanl on 12/15/13.
 */
public class ReSort {

    public static Object[] switchArray(Object[] oArr, int n) {
        int size = oArr.length;
        Object[] oArr1 = new Object[size];
        for (int i = 0; i < size; i++) {
            int index = i + n;
            if (index > size - 1) {
                index -= size;
            }
            oArr1[i] = oArr[index];
        }
        return oArr1;
    }

    public static void main(String[] args) {
        Object[] arr = new Object[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = i + "";
        }

        Object[] arr1 = ReSort.switchArray(arr,5);

        for (int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i]);
        }
    }
}
