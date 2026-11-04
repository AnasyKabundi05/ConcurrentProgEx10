import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "data/health_and_wellness_dataset.csv";
        List<HealthRecord> data = HealthDataLoader.load(filePath);

        if (data.isEmpty()) {
            System.err.println("No data loaded. Please check the file path or format.");
            return;
        }

        ///////Task 1////////////////////////////////////////////////////////////

        System.out.println("Loaded " + data.size() + " health records.\n");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        //executorService.submit(new BMIThershold(data,35));

        //executorService.shutdown();

        ///Task 2///////////////////////////////////////////////////////////////

        //executorService.submit(new LifestyleAnalysis(data));

        //executorService.shutdown();


        /////Task 3/////////////////////////////////////////////////////////////

        //Future is for the callables
        Future<Map<String, Double>> future = executorService.submit(new MentalHealthScore(data));

        //executorService.shutdown();

        //Map<String, Double> result = future.get();

        //result.forEach((region, avg) -> System.out.printf("%s : %.2fn\n", region, avg));

        //////Task 4///////////////////////////////////////////////////////////////////

        //executorService.submit(new HealthStatistics(data));

        //executorService.shutdown();

        ///Task 5//////////////////////////////////////////////////////////////////////////

        //Future<String> future1 = executorService.submit(new BloodPressure(data));

        //System.out.println(future1.get());

        /////////Task 6////////////////////////////////////////////////////////////////////

        Future<Map<String, Double>> north = executorService.submit(new HealthSummaryGeographical(data, "north"));
        Future<Map<String, Double>> south = executorService.submit(new HealthSummaryGeographical(data, "south"));

        executorService.shutdown();

        System.out.println("Region: North");
        north.get().forEach((label, value) -> System.out.printf("%s: %.2f%n", label, value));

        System.out.println("\nRegion: South");
        south.get().forEach((label, value) -> System.out.printf("%s: %.2f%n", label, value));
    }
}