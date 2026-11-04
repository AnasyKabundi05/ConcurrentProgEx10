import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class HealthStatistics implements Runnable{

    private List<HealthRecord> data;

    public HealthStatistics(List<HealthRecord> data) {
        this.data = data;
    }

    @Override
    public void run(){

        DecimalFormat df = new DecimalFormat("0.00");

       double age = data.stream().mapToDouble(r -> r.getAge())
               .average()
               .orElse(0);

        System.out.println("Mean Age: " + df.format(age));


        double averageHoursSlept = data.stream().mapToDouble(r -> r.getAvgSleepHours())
                .average()
                .orElse(0);

        System.out.println("Mean Sleep Hours: " + df.format(averageHoursSlept));


        double exerciseMinutes = data.stream().mapToDouble(r -> r.getExerciseMinsPerWeek())
                .average()
                .orElse(0);

        System.out.println("Mean Exercise Minutes: " + df.format(exerciseMinutes));


        double dietQuality = data.stream().mapToDouble(r -> r.getDietQuality())
                .average()
                .orElse(0);

        System.out.println("Mean Diet Quality: " + df.format(dietQuality));

        double smokerPercentage = 0;

        double smokers = data.stream().filter(r -> r.getSmoker().equals("Yes"))
                .count();

        smokerPercentage = (smokers / data.size()) * 100;

        System.out.println("Smoker Percentage: " + df.format(smokerPercentage));
    }
}
