package org.example;

public class demo {
    public static void main(String[] args) {
        int[] array = {-2, -1, -1, -5, -1, -3, -2};
//        int[] array = {2, 1, 1, 5, 1, 3, 2};

        int windowSize = 3;
        int maxSum = Integer.MIN_VALUE;
        int windowSum = 0;

        for (int right = 0; right < windowSize; right++) {
            windowSum += array[right];
        }

        maxSum = windowSum;

        for (int right = windowSize; right < array.length; right++) {
            windowSum = windowSum + array[right] - array[right - windowSize];
            maxSum = Math.max(maxSum, windowSum);
        }

        System.out.println(maxSum);

//        int maxSum = 0;
//
//        int left = 0;
//        int right = 2;
//
//        for (int i = 0; i <= right; i++) {
//            maxSum += array[i];
//        }
//        int prevSum = maxSum;
//        while (right < array.length - 1) {
//            int currSum = prevSum + array[right + 1] - array[left];
//            prevSum = currSum;
//            right++;
//            left++;
//            maxSum = Math.max(maxSum, currSum);
//        }

//        System.out.println("Maximum Sum: " + maxSum);


//        int windowSize = 3;
//        int windowSum = 0;
//        int maxSum = 0;
//
//        // First window
//        for (int right = 0; right < windowSize; right++) {
//            windowSum += array[right];
//        }
//
//        maxSum = windowSum;
//
//        // Slide the window
//        for (int right = windowSize; right < array.length; right++) {
//
//            windowSum = windowSum + array[right] - array[right - windowSize];
//
//            maxSum = Math.max(maxSum, windowSum);
//        }
//        System.out.println("Maximum Sum: " + maxSum);
    }
}
