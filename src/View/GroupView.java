package View;

import Model.DataPointModel;
import Model.GroupModel;

public class GroupView {
    DataPointView dataPointView;
    public void displayGroup(GroupModel groupModel){
        System.out.println("Group content");
        System.out.println("In range: " + groupModel.getRange());
        for (DataPointModel dp:groupModel.getContent()) {
            dataPointView.printDataPoint(dp);
        }
        System.out.println("Total Value: "+groupModel.getTotal_value());
    }
}
