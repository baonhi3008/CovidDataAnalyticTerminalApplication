package View;
import Model.DataPointModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Model.DataPointModel.*;
public class DataPointView {
    public String changeToStringDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }
    public void printDataPoint(DataPointModel dataPointModel){
        System.out.printf("Location: %s,Date: %s, Cases: %d, Deaths: %d, Vacinated: %d \n",dataPointModel.getLocation(),dataPointModel.getDate(),dataPointModel.getCases(),dataPointModel.getDeaths(),dataPointModel.getVaccinated());
    }

}
