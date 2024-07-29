package Phase2Files;

import java.util.*;
import java.io.*;

public class Kmeans {

    public static void main(String[] args) throws FileNotFoundException {
        // Parse command-line arguments
        if (args.length != 5) {
            System.err.println("Usage: java KMeans F K I T R");
            System.exit(1);
        }
        String fileName = args[0];
        int k = Integer.parseInt(args[1]);
        int maxIterations = Integer.parseInt(args[2]);
        double threshold = Double.parseDouble(args[3]);
        int runs = Integer.parseInt(args[4]);

        // Check for valid parameters
        if (k <= 1 || maxIterations <= 0 || threshold < 0 || runs <= 0) {
            System.err.println("Invalid parameter values.");
            System.exit(1);
        }

        // Read data points from file
        List<DataPoint> dataPoints = readDataPoints(fileName);

        // Run k-means algorithm multiple times
        for (int r = 0; r < runs; r++) {
            System.out.println("Run " + (r + 1));

            // Initialize random centers
            List<DataPoint> centers = initializeCenters(dataPoints, k);

            // Run k-means algorithm
            runKMeans(dataPoints, centers, maxIterations, threshold);
        }
    }

    private static List<DataPoint> readDataPoints(String fileName) throws FileNotFoundException {
        List<DataPoint> dataPoints = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        // Read number of points and dimensionality
        int n = scanner.nextInt();
        int d = scanner.nextInt();

        // Read data points
        for (int i = 0; i < n; i++) {
            double[] coordinates = new double[d];
            for (int j = 0; j < d; j++) {
                coordinates[j] = scanner.nextDouble();
            }
            dataPoints.add(new DataPoint(coordinates));
        }

        scanner.close();
        return dataPoints;
    }

    private static List<DataPoint> initializeCenters(List<DataPoint> dataPoints, int k) {
        Random random = new Random();
        List<DataPoint> centers = new ArrayList<>();

        // Select k random data points as centers
        for (int i = 0; i < k; i++) {
            int index = random.nextInt(dataPoints.size());
            centers.add(dataPoints.get(index));
        }

        return centers;
    }

    private static void runKMeans(List<DataPoint> dataPoints, List<DataPoint> centers, int maxIterations, double threshold) {
        boolean converged = false;
        int iterations = 0;
        double prevSSE = Double.POSITIVE_INFINITY;

        while (!converged && iterations < maxIterations) {
            // Assign data points to closest centers
            assignPoints(dataPoints, centers);

            // Update centers
            updateCenters(dataPoints, centers);

            // Calculate SSE
            double sse = calculateSSE(dataPoints, centers);

            // Check for convergence
            converged = (sse - prevSSE) / prevSSE < threshold;
            prevSSE = sse;

            // Print SSE
            System.out.println("Iteration " + (iterations + 1) + ": SSE = " + sse);

            iterations++;
        }

        // Print final SSE
        System.out.println("Final SSE: " + prevSSE);
    }

    private static void assignPoints(List<DataPoint> dataPoints, List<DataPoint> centers) {
        for (DataPoint dataPoint : dataPoints) {
            double minDistance = Double.POSITIVE_INFINITY;
            DataPoint closestCenter = null;
            for (DataPoint center : centers) {
                double distance = dataPoint.distance(center);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCenter = center;
                }
            }
            dataPoint.setCluster(closestCenter);
        }
    }

    private static void updateCenters(List<DataPoint> dataPoints, List<DataPoint> centers) {
        // Create temporary lists to store the sum of coordinates and counts for each cluster
        List<double[]> sumCoordinates = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for (int i = 0; i < centers.size(); i++) {
            sumCoordinates.add(new double[centers.get(i).getCoordinates().length]);
            counts.add(0);
        }

        // Iterate through data points and accumulate coordinates and counts for their clusters
        for (DataPoint dataPoint : dataPoints) {
            DataPoint clusterCenter = dataPoint.getCluster();
            int clusterIndex = centers.indexOf(clusterCenter);
            double[] clusterSum = sumCoordinates.get(clusterIndex);
            int count = counts.get(clusterIndex);

            // Add data point's coordinates to cluster sum
            for (int i = 0; i < dataPoint.getCoordinates().length; i++) {
                clusterSum[i] += dataPoint.getCoordinates()[i];
            }

            // Increment count for the cluster
            counts.set(clusterIndex, count + 1);
        }

        // Update centers by calculating the average of coordinates for each cluster
        for (int i = 0; i < centers.size(); i++) {
            double[] clusterSum = sumCoordinates.get(i);
            int count = counts.get(i);
            double[] newCoordinates = new double[clusterSum.length];

            // Calculate average coordinates for the cluster
            for (int j = 0; j < newCoordinates.length; j++) {
                newCoordinates[j] = clusterSum[j] / count;
            }

            // Update the center's coordinates
            centers.get(i).setCoordinates(newCoordinates);
        }
    }
    private static double calculateSSE(List<DataPoint> dataPoints, List<DataPoint> centers) {
        double sse = 0;
        for (DataPoint dataPoint : dataPoints) {
            DataPoint clusterCenter = dataPoint.getCluster();
            double distance = dataPoint.distance(clusterCenter);
            sse += distance * distance; // Square the distance for SSE
        }
        return sse;
    }
}
