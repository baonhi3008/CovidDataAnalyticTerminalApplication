package View;

import Model.DataPointModel;
import Model.GroupModel;

import java.util.ArrayList;

public class SummaryListView {
    DataListView dataListView = new DataListView();

    public void displayGroup(String range,String total){

        System.out.printf(" %s |  %s ",range,total);

    }

    /**
     * Method to display view for all of group models inside an array list.
     * @param groupList an array list of group model
     */


}
