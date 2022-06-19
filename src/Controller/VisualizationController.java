package Controller;

import Model.GroupModel;
import PromptManager.PromptManager;
import Utility.ShowOptionMenu;
import View.ChartDisplay;
import View.TubularDisplay;
import java.util.ArrayList;

public class VisualizationController {
    private static ArrayList<GroupModel> groupModels;
    private static final PromptManager promptManager = new PromptManager();

    /**
     * Constructor fir visualization
     * @param groupModels an array list of group models to be visualized
     */
    public VisualizationController(ArrayList<GroupModel> groupModels){
        VisualizationController.groupModels = groupModels;
        TubularDisplay tubularDisplay = new TubularDisplay();
        ChartDisplay chartDisplay = new ChartDisplay();
    }

    /**
     * Print kind of chart based on user choice with title
     * @throws Exception if there is any error
     */
    public void printTypesOfChart() throws Exception {
        ShowOptionMenu.askTypeOfDisplay();
        int option = promptManager.choiceMenuWithValidOption(2,1);
        switch (option){
            case 1 -> {
                System.out.println("                         TUBULAR TABLE DISPLAY");
                System.out.println("========================================================================");
                System.out.println();
                TubularDisplay.displayTable(groupModels);
                System.out.println("========================================================================");}
            case 2 -> {
                System.out.println("                          CHART DISPLAY");
                System.out.println("========================================================================");
                System.out.println();
                ChartDisplay.displayChart(groupModels);
                System.out.println("========================================================================");}
        }
    }

}
