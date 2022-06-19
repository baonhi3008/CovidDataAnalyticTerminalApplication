package View;
import Model.DataPointModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static Model.DataPointModel.*;
public class DataListView {
    /**
     * Method to print the content of data point model object
     * @param dataPointModel a data point model object to be displayed
     */
    public void printDataPoint(DataPointModel dataPointModel){
     System.out.printf("Location: %s,Date:%s, Cases: %d, Deaths: %d, Vacinated: %d \n",
             dataPointModel.getLocation(),dataPointModel.getDate(),dataPointModel.getCases(),dataPointModel.getDeaths(),dataPointModel.getVaccinated()); }

    /**
     * Method to display all data point models inside a list from user input with location and within wanted time range.
     * @param dataPointModels an array list of data point models
     */
    public void printDataPointList(ArrayList<DataPointModel> dataPointModels){
//        System.out.printf("Location: %s,Date:%s, Cases: %d, Deaths: %d, Vacinated: %d \n",dataPointModel.getLocation(),dataPointModel.getDate(),dataPointModel.getCases(),dataPointModel.getDeaths(),dataPointModel.getVaccinated());
        DataPointModel firstDPM = dataPointModels.get(0);
        DataPointModel lastDPM = dataPointModels.get(dataPointModels.size()-1);
        String startDate = firstDPM.getDate();
        String endDate = lastDPM.getDate();
        String location = firstDPM.getLocation();
        System.out.println(" List of Data Point to be processed");
        System.out.printf(" Location: %s \n Time Range: %s to %s \n",location,startDate,endDate);
        System.out.println("--------------------------------------------------------------------------");
        for (DataPointModel dpm: dataPointModels
        ) {
           printDataPoint(dpm);
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("Number of data points: %d \n", dataPointModels.size());
    }

}
