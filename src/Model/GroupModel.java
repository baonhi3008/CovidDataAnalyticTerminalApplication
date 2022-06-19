package Model;

import java.util.ArrayList;

public class GroupModel {
    private final ArrayList<DataPointModel> Content = new ArrayList<>();
    private String total_string;
    private int new_total;
    private int Total_value = 0;

    public GroupModel() {
    }

    public ArrayList<DataPointModel> getContent() {
        return Content;
    }

    public void setTotal_value(int total_value) {
        this.Total_value = total_value;
    }

    public void add_content(DataPointModel dataPoint) {
        Content.add(dataPoint);
    }


    public String getRange() {
        if (Content.size() == 1) {
            return Content.get(0).getDate();
        } else {
            return Content.get(0).getDate() + " - " + Content.get(Content.size()-1).getDate();
        }
    }
    public int getTotal_value() {
        return Total_value;
    }
    public void displayGroup(){
        System.out.println("Group content");
        for (DataPointModel dp:Content) {
            dp.printDataPoint();
        }
        System.out.println("Total value: " + Total_value);
        System.out.println("-------------------------------------------------------------------------");
    }





}
