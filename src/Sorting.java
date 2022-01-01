import javafx.scene.chart.XYChart;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


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

    public static void generate_test(int n) {
        int[] arr = new int[n];
        Random rand = new Random();
        int x;
        for (int i = 0; i < arr.length; i++) {
            x = rand.nextInt(n) + 1;
            arr[i] = x;
        }
        writeToFile(arr, "test_data.txt");

    }

    public static void writeToFile(int[] arr, String filename) {
        OutputStream out;
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop/" + filename;
        try {
            out = new FileOutputStream(desktopPath);
            PrintStream outFile = new PrintStream(out);
            for (int i = 0; i < arr.length; i++) {
                outFile.println(arr[i]);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readFromFile() throws FileNotFoundException {
        String fileName = System.getProperty("user.home") + File.separator + "Desktop/test_data.txt";
        File file = new File(fileName);
        int[] input = new int[10000];
        int size = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextInt()) {
            input[size] = scanner.nextInt();
            size++;
        }
        return Arrays.copyOf(input, size);
    }

    public static void sort_test(String algo) throws FileNotFoundException {
        int[] data = readFromFile();
        switch (algo) {
            case "Insertion" -> Sorting.insertion_sort(data, data.length);
            case "Merge" -> Sorting.mergeSort(data, 0, data.length - 1);
            case "Bubble" -> Sorting.bubbleSort(data, data.length);
            case "Quick" -> Sorting.quicksort(data, 0, data.length - 1);
            case "Heap" -> Sorting.heapsort(data, data.length);
        }
        writeToFile(data, "test_data_sorted.txt");

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
                    swap(arr, j, j + 1);
                    steps++;
                }
                steps++;
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
        steps++;
        if (r < s && arr[r] > arr[largest]) {
            largest = r;
            steps++;
        }
        steps++;
        if (largest != i) {
            swap(arr, i, largest);
            steps++;
            steps += max_heapify(arr, largest, s);
        }
        return steps;
    }

    private static int build_max_heap(int[] arr, int s) {
        int steps = 0;
        for (int i = s / 2; i >= 0; i--) {
            steps += max_heapify(arr, i, s);
        }
        return steps;
    }

    public static int heapsort(int[] arr, int s) {
        int steps = 0;
        steps += build_max_heap(arr, s);
        for (int i = s - 1; i >= 1; i--) {
            swap(arr, 0, i);
            s--;
            steps += 2;
            steps += max_heapify(arr, 0, s);
        }
        return steps;
    }

    public static XYChart.Series<String,Number> generate_series(String type, int max_size, int step) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (type) {
            case "Insertion" -> series.setName("Insertion Sort");
            case "Merge" -> series.setName("Merge Sort");
            case "Bubble" -> series.setName("Bubble Sort");
            case "Quick" -> series.setName("Quick Sort");
            case "Heap" -> series.setName("Heap Sort");
            case "Expo" -> series.setName("N^2");
            case "n log" -> series.setName("n log(n)");
        }

        int[] arr;
        double n = 0;
        int i = 0;
        while (true) {
            arr = Sorting.generate(i);

            switch (type) {
                case "Insertion" -> n = Sorting.insertion_sort(arr, i);
                case "Merge" -> n = Sorting.mergeSort(arr, 0, i - 1);
                case "Bubble" -> n = Sorting.bubbleSort(arr, i);
                case "Quick" -> n = Sorting.quicksort(arr, 0, i - 1);
                case "Heap" -> n = Sorting.heapsort(arr, i);
                case "Expo" -> n = i * i;
                case "n log" -> {
                    if (i == 0) {
                        n = 0;
                        break;
                    }
                    n = i * (Math.log(i) / Math.log(2));
                }
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(i), n));
            if (i < max_size) {
                i += step;
                if (i > max_size) i = max_size;
            } else break;
        }
        return series;
    }

}
