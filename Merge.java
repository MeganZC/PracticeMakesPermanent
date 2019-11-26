/**
 * Created by Megan on 12/13/16.
 */

import java.util.Scanner;
import java.util.Arrays;

public class Merge {

    static String[] auxS;
    static int[] auxC;

    public static void sort(int[] countArray, String[] stringArray) {
        auxC = new int[countArray.length];
        auxS = new String[stringArray.length];
        sort(countArray, stringArray, 0, countArray.length - 1);
    }

    private static void sort(int[] countArray, String[] stringArray, int low, int high) {
        if (high <= low) return;
        int mid = low + (high - low) / 2; // numerically same as
        // (high+low)/2, but works
        // better for int overflow

        sort(countArray, stringArray, low, mid);
        sort(countArray, stringArray, mid + 1, high);
        merge(countArray, stringArray, low, mid, high);
    }

    private static void merge(int[] countArray, String[] stringArray, int low, int mid, int high) {
        int i = low, j = mid + 1;

        for (int k = low; k <= high; k++) {
            auxC[k] = countArray[k];
            auxS[k] = stringArray[k];
        }

        for (int k = low; k <= high; k++) {

            if (i > mid) {
                countArray[k] = auxC[j];
                stringArray[k] = auxS[j];
                j++;
            } else if (j > high) {
                countArray[k] = auxC[i];
                stringArray[k] = auxS[i];
                i++;
            } else if (auxC[j] < auxC[i]) {
                countArray[k] = auxC[j];
                stringArray[k] = auxS[j];
                j++;
            } else {
                countArray[k] = auxC[i];
                stringArray[k] = auxS[i];
                i++;
            }
        }
    }

}