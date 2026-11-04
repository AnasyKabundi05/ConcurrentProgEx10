import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class LifestyleAnalysis implements Runnable{

    private List<HealthRecord> data;

    public LifestyleAnalysis(List<HealthRecord> data) {
        this.data = data;
    }

    @Override
    public void run(){

        DecimalFormat df = new DecimalFormat("0.00");

        double smokerAverageExercise = data.stream().filter(r -> r.getSmoker().equals("Yes"))
                    .mapToDouble(r -> r.getExerciseMinsPerWeek())
                    .average()
                    .orElse(0);

        double nonSmokerAverageExercise = data.stream().filter(r -> r.getSmoker().equals("No"))
                .mapToDouble(r -> r.getExerciseMinsPerWeek())
                .average()
                .orElse(0);

        double smokerAverageDietQuality = data.stream().filter(r -> r.getSmoker().equals("Yes"))
                .mapToDouble(r -> r.getDietQuality())
                .average()
                .orElse(0);

        double nonSmokerAverageDietQuality = data.stream().filter(r -> r.getSmoker().equals("No"))
                .mapToDouble(r -> r.getDietQuality())
                .average()
                .orElse(0);


        System.out.println("============Lifestyle Summary by Smoking Status=========");

        System.out.println("Smoker Avg Exercise: " + df.format(smokerAverageExercise) + " mins/week, " + "Avg Diet Quality: " + df.format(smokerAverageDietQuality));

        System.out.println("Non-Smoker Avg Exercise: " + df.format(nonSmokerAverageExercise) + " mins/week, " + "Avg Diet Quality: " + df.format(nonSmokerAverageDietQuality));
    }
}
