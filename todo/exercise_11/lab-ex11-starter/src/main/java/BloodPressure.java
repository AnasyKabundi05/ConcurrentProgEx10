import java.util.List;
import java.util.concurrent.Callable;

public class BloodPressure implements Callable<String>{

    private List<HealthRecord> data;
    public static String HighRisk = "High";
    public static String LowRisk = "Low";
    public static String MediumRisk = "Medium";

    public BloodPressure(List<HealthRecord> data) {
        this.data = data;
    }

    @Override
    public String call(){

        String risk = "";
        String result = "";

        for(HealthRecord record : data){

            if(record.getDiastolicBp() < 80 && record.getSystolicBp() < 120){
               risk = LowRisk;

               if(record.getAge() > 60){
                   risk = MediumRisk;
               }

                result += "\nParticipant: " + record.getId() + "\tHypertension Risk: " + risk;

            } else if (record.getDiastolicBp() <= 89 && record.getSystolicBp() < 140) {
                risk = MediumRisk;

                if(record.getAge() > 60){
                    risk = HighRisk;
                }

                result += "\nParticipant: " + record.getId() + "\tHypertension Risk: " + risk;

            }else{
               risk = HighRisk;
                result += "\nParticipant: " + record.getId() + "\tHypertension Risk: " + risk;
            }
        }

        return result;
    }
}
