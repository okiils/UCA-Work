package Phase2Files;
class DataPoint {
    private double[] coordinates;
    private DataPoint cluster; // Reference to the cluster it belongs to

    public DataPoint(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public DataPoint getCluster() {
        return cluster;
    }

    public void setCluster(DataPoint cluster) {
        this.cluster = cluster;
    }

    public double distance(DataPoint other) {
        // Implement distance calculation (e.g., Euclidean distance)
        double distance = 0;
        for (int i = 0; i < coordinates.length; i++) {
            double diff = coordinates[i] - other.coordinates[i];
            distance += diff * diff;
        }
        return Math.sqrt(distance); // Use Math.sqrt only for final distance calculation

    }
}
