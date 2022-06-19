package Model;

import java.util.ArrayList;

public class GroupModel {
    /**
     * This class is a model that represents an Group object.
     * ------------------------------------------------------
     * There is 4 necessary attributes for the the group model
     * content is an array list of data point models extracted from step 1 with wanted location, time range.
     * totalValue is a value of total values of specific metric in the group after calculating process
     */
    private final ArrayList<DataPointModel> content = new ArrayList<>();

    private int totalValue = 0;

    public GroupModel() {
    }

    /**
     * Method to get content of a group
     * @return an array list content all necessary data point models
     */
    public ArrayList<DataPointModel> getContent() {
        return content;
    }

    /**
     * Method to set new total value for the gorup model
     * @param totalValue set new total value of specific metric after calculating process
     */
    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    /**
     * Method to add content for the content list
     * @param dataPoint a data point model to be added
     */
    public void add_content(DataPointModel dataPoint) {
        content.add(dataPoint);
    }

    /**
     * Method to get range for the model
     * @return a String that shows the range of group model
     */
    public String getRange() {
        if (content.size() == 1) {
            return content.get(0).getDate();
        } else {
            return content.get(0).getDate() + " - " + content.get(content.size()-1).getDate();
        }
    }

    /**
     * Method to get total value after calculating process
     * @return an integer of total value
     */
    public int getTotalValue() {
        return totalValue;
    }

    /**
     * Method to display each group content. After the calculation is processed to get summary.
     */
    public void displayGroup(){
        System.out.println("Group content");
        for (DataPointModel dp: content) {
            dp.printDataPoint();
        }
        System.out.println("Total value: " + totalValue);
        System.out.println("-------------------------------------------------------------------------");
    }





}
