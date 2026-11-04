import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MentalHealthScore implements Callable<Map<String, Double>> {

    private List<HealthRecord> data;

    public MentalHealthScore(List<HealthRecord> data) {
        this.data = data;
    }

    @Override
    public Map<String, Double> call(){

        Map<String, Double> averageMentalHealthScore = data.stream().collect(Collectors.groupingBy(
                r -> r.getRegion(), Collectors.averagingDouble(r -> r.getMentalHealthScore())));

        return averageMentalHealthScore;
    }
}
