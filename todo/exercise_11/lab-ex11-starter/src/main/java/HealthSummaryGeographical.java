import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class HealthSummaryGeographical implements Callable<Map<String, Double>> {

    private List<HealthRecord> data;
    private String region;

    public HealthSummaryGeographical(List<HealthRecord> data, String region) {
        this.data = data;
        this.region = region;
    }

    @Override
    public Map<String, Double> call(){

        double sampleSize = data.stream().filter(r -> r.getRegion().equalsIgnoreCase(region))
                .count();

        double alcoholConsumption = data.stream().filter(r -> r.getAlcoholUnitsPerWeek() > 14)
                .filter(r -> r.getRegion().equalsIgnoreCase(region))
                .count();

        double alcoholConsumptionRateAns = (alcoholConsumption / sampleSize) * 100;

        double metabolicSyndrome = data.stream().filter(r -> r.getRegion().equalsIgnoreCase(region))
                .filter(r -> (r.getBmi() >= 30 ? 1 : 0)
                        + (r.getSystolicBp() >= 130 || r.getDiastolicBp() >= 85 ? 1 : 0)
                        + (r.getCholesterolMgDl() >= 200 ? 1 : 0)
                        + (r.getGlucoseMgDl() >= 100 ? 1 : 0)
                        + (r.getExerciseMinsPerWeek() < 90 ? 1 : 0) >= 3)
                .count();

        double metabolicSyndromeRateAns = (metabolicSyndrome / sampleSize) * 100;

        Map<String, Double> results = new LinkedHashMap<>();
        results.put("Sample Size", sampleSize);
        results.put("Alcohol Above Limit (%)", alcoholConsumptionRateAns);
        results.put("Metabolic Syndrome Markers (%)", metabolicSyndromeRateAns);

        return results;
    }
}