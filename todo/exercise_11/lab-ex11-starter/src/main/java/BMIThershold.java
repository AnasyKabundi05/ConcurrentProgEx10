import java.util.List;

public class BMIThershold implements Runnable{

    private List<HealthRecord> data;
    private int threshold;

    public BMIThershold(List<HealthRecord> data, int threshold) {
        this.data = data;
        this.threshold = threshold;
    }

    @Override
    public void run(){

        List<HealthRecord> result = data.stream().filter(r -> r.getBmi() >= threshold).toList();

        System.out.println("Total number of individuals with BMI >= " + threshold + " is " + result.size());

        result.forEach(r -> System.out.println("ID: " + r.getId() + "\tBMI: " + r.getBmi()));
    }
}
