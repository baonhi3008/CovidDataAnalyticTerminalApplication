import Controller.DataListController;
import Controller.SummaryListController;
import Controller.VisualizationController;
import Model.DataPointModel;
import Model.GroupModel;
import Utility.ShowOptionMenu;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static Main app1 = new Main();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        ShowOptionMenu.welcome_Screen();
        System.out.println();
        boolean continued;
        do{
            app1.run();
            System.out.println("Do you want to continue (y/n)");
            String answer = scanner.nextLine();
            continued = answer.equals("y");
            if(!continued){
                app1.quit();
            }
        } while (continued);

    }
    public void run() {
        try
        {
            ArrayList<DataPointModel> dataObjectModels;
            DataListController dataListController = new DataListController();
            dataObjectModels = dataListController.prompNewList();
            dataListController.updateView();
            ArrayList<GroupModel> dataTable;
            SummaryListController summaryListController = new SummaryListController(dataObjectModels);
            System.out.println();
            dataTable = summaryListController.groupNTotalProcess();
            summaryListController.updateViewList();
            VisualizationController  visualizationController = new VisualizationController(dataTable);
            visualizationController.printTypesOfChart();
        }
        catch (Exception e){
            System.out.println("There is an exception error");
        }

    }

    public void quit() {
        System.out.println("Application shutdown. Thank you and see you again! ");
        System.exit(0);
    }

}
