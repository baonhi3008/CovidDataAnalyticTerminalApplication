import Controller.DataListController;
import Controller.SummaryListController;
import Model.DataPointModel;
import Model.GroupModel;
import Utility.ShowOptionMenu;
import View.DataDisplay;


import java.util.ArrayList;
import java.util.Scanner;

public class MainMVC {
    public static MainMVC app1 = new MainMVC();
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
    public void run() throws Exception {

        ArrayList<DataPointModel> dataObjectModels;
        DataListController dataListController = new DataListController();
        dataObjectModels = dataListController.prompNewList();
        dataListController.updateView();
        ArrayList<GroupModel> data_table;
        SummaryListController summaryListController = new SummaryListController(dataObjectModels);
        System.out.println();
        ShowOptionMenu.groupingOption();
        data_table = summaryListController.group_n_total_process();
        summaryListController.updateView();
        DataDisplay.Display_table(data_table);

    }

    public void quit() {
        System.out.println("Application shutdown.");
        System.exit(0);
    }

}
