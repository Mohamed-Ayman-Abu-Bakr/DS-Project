import javafx.scene.chart.XYChart;

import java.io.*;
import java.util.Random;

public class Sorting {

    private static int steps = 0;

    public static int[] generate(int n) {
        int[] arr = new int[n];
        Random rand = new Random();
        int x;

        for (int i = 0; i < n; i++) {
            x = rand.nextInt(n) + 1;
            arr[i] = x;
        }
        return arr;
    }

    public static void generate_test(int n){
        OutputStream out = null;
        Random rand = new Random();
        int x;
        try {
            out = new FileOutputStream("test_data.txt");
            PrintStream outFile = new PrintStream(out);
            for (int i = 0; i < n; i++) {
                x = rand.nextInt(n) + 1;
                outFile.println(x);
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    public static int insertion_sort(int[] arr, int length) {
        int key, j;
        int cnt = 0;
        for (int i = 1; i < length; i++) {
            key = arr[i];
            j = i - 1;
            cnt += 2;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                cnt += 2;
            }
            arr[j + 1] = key;
            cnt++;
        }
        return cnt;
    }

    private static int Merge(int[] arr, int l, int m, int r) {
        int steps = 0;
        final int n1 = m - l + 1;
        final int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        steps += 3;

        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i];
            steps++;
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
            steps++;
        }

        int i = 0, j = 0, k = l;
        steps++;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            steps += 3;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            steps += 3;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            steps += 3;
        }
        return steps;
    }

    public static int mergeSort(int[] arr, int l, int r) {
        int steps = 0;
        if (l >= r) {
            return steps;
        }
        int m = (l + r) / 2;
        steps += 2;
        steps += mergeSort(arr, l, m);
        steps += mergeSort(arr, m + 1, r);
        steps += Merge(arr, l, m, r);
        return steps;
    }

    public static int bubbleSort(int[] arr, int length) {
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr,j,j+1);
                    steps ++;
                }
            }
        }
        return steps;
    }

    private static int Partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;
        steps += 2;
        for (int j = p; j < r; j++) {
            if (arr[j] <= x) {
                i++;
                swap(arr, i, j);
                steps += 2;
            }
            steps++;
        }
        swap(arr, i + 1, r);
        steps++;
        return i + 1;
    }

    public static int quicksort(int[] arr, int p, int r) {
        steps = 0;
        int q;
        if (p < r) {
            q = Partition(arr, p, r);
            steps += quicksort(arr, p, q - 1);
            steps += quicksort(arr, q + 1, r);
        }
        return steps;
    }

    private static int left(int i) {
        return 2 * i + 1;
    }

    private static int right(int i) {
        return 2 * i + 2;
    }

    private static int max_heapify(int[] arr, int i, int s) {
        int l = left(i);
        int r = right(i);
        int largest;
        int steps = 3;
        if (l < s && arr[l] > arr[i]) {
            largest = l;
        } else {
            largest = i;
        }
        steps += 2;
        if (r < s && arr[r] > arr[largest]) {
            largest = r;
            steps++;
        }
        steps++;
        if (largest != i) {
            swap(arr, i, largest);
            max_heapify(arr, largest, s);
            steps += 2;
        }
        steps++;
        return steps;
    }

    private static int build_max_heap(int[] arr, int s) {
        int steps = 0;
        for (int i = s / 2; i >= 0; i--) {
            steps += max_heapify(arr, i, s);
            steps++;
        }
        return steps;
    }

    public static int heapsort(int[] arr, int s) {
        int steps = 0;
        steps += build_max_heap(arr, s);
        for (int i = s - 1; i >= 1; i--) {
            swap(arr, 0, i);
            s--;
            steps += max_heapify(arr, 0, s);
            steps += 3;
        }
        return steps;
    }

    public static XYChart.Series generate_insertion_series(int max_size, int step) {
        XYChart.Series<String, Number> series_insertion = new XYChart.Series<>();
        series_insertion.setName("Insertion Sort");

        int[] arr;
        int n;
        for (int i = 1; i < max_size; ) {
            arr = Sorting.generate(i);
            n = Sorting.insertion_sort(arr, i);
            series_insertion.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }

        return series_insertion;
    }

    public static XYChart.Series generate_merge_series(int max_size, int step) {
        XYChart.Series<String, Number> series_merge = new XYChart.Series<>();
        series_merge.setName("Merge Sort");

        int[] arr;
        int n;
        for (int i = 1; i < max_size; ) {
            arr = Sorting.generate(i);
            n = Sorting.mergeSort(arr, 0, i - 1);
            series_merge.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_merge;
    }

    public static XYChart.Series generate_bubble_series(int max_size, int step) {
        XYChart.Series<String, Number> series_bubble = new XYChart.Series<>();
        series_bubble.setName("Bubble Sort");

        int[] arr;
        int n;
        for (int i = 1; i < max_size; ) {
            arr = Sorting.generate(i);
            n = Sorting.bubbleSort(arr, i);
            series_bubble.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_bubble;
    }

    public static XYChart.Series generate_quick_series(int max_size, int step) {
        XYChart.Series<String, Number> series_quick = new XYChart.Series<>();
        series_quick.setName("Quick Sort");

        int[] arr;
        int n;
        for (int i = 1; i < max_size; ) {
            arr = Sorting.generate(i);
            n = Sorting.quicksort(arr, 0, i - 1);
            series_quick.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_quick;
    }

    public static XYChart.Series generate_heap_series(int max_size, int step) {
        XYChart.Series<String, Number> series_heap = new XYChart.Series<>();
        series_heap.setName("Heap Sort");

        int[] arr;
        int n;
        for (int i = 1; i < max_size; ) {
            arr = Sorting.generate(i);
            n = Sorting.heapsort(arr, i);
            series_heap.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_heap;
    }

    public static XYChart.Series generate_expo_series(int max_size, int step) {
        XYChart.Series<String, Number> series_expo = new XYChart.Series<>();
        series_expo.setName("N^2");

        int n;
        for (int i = 1; i < max_size; ) {
            n = i * i;
            series_expo.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_expo;
    }

    public static XYChart.Series generate_n_log_series(int max_size, int step) {
        XYChart.Series<String, Number> series_expo = new XYChart.Series<>();
        series_expo.setName("n log(n)");
        double n;
        for (int i = 1; i < max_size; ) {
            n = i * (Math.log(i) / Math.log(2));
            series_expo.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_expo;
    }


    //TODO
    public static XYChart.Series generate_series(String type, int max_size, int step) {
        XYChart.Series<String, Number> series_expo = new XYChart.Series<>();
        series_expo.setName("n log(n)");

        int[] arr;
        double n;
        for (int i = 1; i < max_size; ) {
            n = i * (Math.log(i) / Math.log(2));
            switch(type){
                case "Insertion":
                    arr = Sorting.generate(i);

                    break;
                case "Merge":
                    arr = Sorting.generate(i);

                    break;
                case "Bubble":
                    arr = Sorting.generate(i);

                    break;
                case "Quick":
                    arr = Sorting.generate(i);

                    break;
                case "Heap":

                    break;
                case "Expo":

                    break;
                case "n log":

                    break;
            }
            series_expo.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i + step <= max_size) i += step;
            else i = max_size;
        }
        return series_expo;
    }

}
