package View;

import Model.GroupModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Formatter;
import static Utility.PrintScreen.*;



public class ChartDisplay {
    private static final String[][] Chart = new String[25][81]; //Increase the chart size for place to print unit and group name
    private static int[] minMax = new int[2];

    /**
     * Method to display a chart ( which is drawn by drawChart()) base on group models with wanted groups and metrics from user.
     * @param dataTable an array list of group models.
     */
    public static void displayChart(ArrayList<GroupModel> dataTable) {

        minMax = minMaxCalculator(dataTable);

        //Initializing the Chart
        initializeChart();

        //Initializing and fill in Y Axis
        double valueMin = minMax[0];
        double valueMax = minMax[1];
        println("Value Min: " + (int) valueMin);
        println("Value Max: " + (int) valueMax);
//        int yAxisRuler = (minMax[1] + minMax[0])/10;

        int yAxisRuler = (int) Math.round((valueMax - valueMin) / 10);

        int xAxisRuler, Coor;

        /*
                                Drawing the chart
        If condition to check if the group size exceeds the chart space, if so then
        it wills decrease the size of the bar in order to fit more data.
        */
        if (enoughSpaceCheck(dataTable.size())) {

            xAxisRuler = 4;
            Coor = 4;

            drawChart(2, dataTable, Coor, yAxisRuler, xAxisRuler, valueMin, valueMax);

        } else {

            xAxisRuler = 2;
            Coor = 3;

            drawChart(1, dataTable, Coor, yAxisRuler, xAxisRuler, valueMin, valueMax);

        }

        //Finding the max height of all the bars
        println("Step size: " + yAxisRuler);
    }

    //                    THE THREE MAIN HANDLER OF DISPLAY CHART

    /**
     * Method to draw a chart.
     * @param barSize which bar size the chart will display
     * @param dataTable an array list of group model
     * @param Coor coordinate of specific metric value
     * @param yAxisRuler rule of y axis value
     * @param xAxisRuler rules of name for group in x axis
     * @param valueMin min value of chart
     * @param valueMax max value of chart
     */
    private static void drawChart(int barSize,ArrayList<GroupModel> dataTable,int Coor,int yAxisRuler,int xAxisRuler, double valueMin, double valueMax) {
        int maxCoordinate = 0;
        for (GroupModel groupModel : dataTable) {
            int Y = 22;
            int valueCheck = (int) valueMin; //Check to see bar about to exceed the value

            while (valueCheck <= groupModel.getTotalValue()) {

                //Print the group number
                String s = String.valueOf(dataTable.indexOf(groupModel) + 1);
                if (Integer.parseInt(s) < 10) {s+=" ";}
                Chart[24][Coor] = s;

                //Draw the bar
                drawBar(barSize,Y,Coor);

                Y--;
                valueCheck += yAxisRuler;
            }

            if (groupModel.getTotalValue() == valueMax) {
                maxCoordinate = Y + 1;
            }

            Coor += xAxisRuler;
        }
        fillYAxis(yAxisRuler, valueMin, valueMax, maxCoordinate);
        printChart(maxCoordinate, dataTable.size(), xAxisRuler);
    }

    /**
     * Method to draw a bar
     * @param barSize a bar size to be draw small or big
     * @param Y what is the value of y
     * @param Coor what is the coordinate
     */
    private static void drawBar(int barSize, int Y, int Coor) {
        switch (barSize) {
            case 1 -> Chart[Y][Coor] = "* ";
            case 2 -> {
                Chart[Y][Coor] = "* ";
                Chart[Y][Coor + 1] = "* ";
            }
        }
    }

    /**
     * Method to print chart with max coordinate and group size, and x axis rule
     * @param maxCoordinate a max value in the chart
     * @param groupSize how many groups there are in an array list
     * @param xAxisRuler the rule of x axis
     */
    private static void printChart(int maxCoordinate,int groupSize,int xAxisRuler) {
        for (int y = maxCoordinate ; y < 25; y++) {
            for (int x = 0; x < Math.min( (groupSize * xAxisRuler) + 4 , 81); x++) {
                print(Chart[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * Method to check if there is enough space for the chart
     * @param groupSize number of group in an array list
     * @return whether there is enough space to draw all group
     */

    private static boolean enoughSpaceCheck(int groupSize) {
        return (groupSize * 4) < 80;
    }

    /**
     * Method to initialize the chart
     */
    private static void initializeChart() {
        //Initialize entire chart with blank space
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 81; x++) {
                Chart[y][x] = "  ";
            }
        }
        //Initialize the xAxis
        for (int X = 1; X < 81; X++) {
            Chart[23][X] = "_ ";
        }
        //Initialize the yAxis
        for (int Y = 0; Y < 24; Y++) {
            Chart[Y][1] = "| ";
        }
    }

    /**
     * Method to fill the value in the left size of y axis
     * @param yAxisRuler the ruler value of y axis
     * @param valueMin min value in list
     * @param valueMax max value in list
     * @param maxCoor max coordinate of the chart
     */
    private static void fillYAxis(int yAxisRuler,double valueMin, double valueMax,int maxCoor) {
        int unit = (int) valueMin;
        String stringMin, stringMax;
        stringMin = "--" + (int) valueMin + "--";
        stringMax = "--" + (int) valueMax + "--";

        int maxLength = stringMax.length();

        Chart[24][0] = fillBlankSpace(1,maxLength);
        Chart[23][0] = fillBlankSpace(1,maxLength);
        Chart[22][0] = fillBlankSpace(stringMin.length() + 1,maxLength) + stringMin;
        Chart[maxCoor][0] = stringMax;

        for (int Y = 21; Y > maxCoor; Y--) {
            unit += yAxisRuler ;
            Chart[Y][0] =  fillBlankSpace(String.valueOf(unit).length() + 2, maxLength) + unit + '-';
        }
    }

    /**
     * Method to calculate min and max values in chart
     * @param dataTable an array list of group models
     * @return an array of 2 integers of min value and max value
     */
    private static int[] minMaxCalculator(ArrayList<GroupModel> dataTable) {

        minMax[0] = dataTable.get(0).getTotalValue();
        minMax[1] = dataTable.get(0).getTotalValue();

        //get min and max from the table
        for (GroupModel group: dataTable) {
            if (group.getTotalValue() > minMax[1]) {
                minMax[1] = group.getTotalValue();
            }
            if (group.getTotalValue() < minMax[0]) {
                minMax[0] = group.getTotalValue();
            }
        }
        return minMax;
    }

    /**
     * Method to fill in the blank space
     * @param varLength the variable length
     * @param maxLength the max length
     * @return a string of blank space
     */
    private static String fillBlankSpace(int varLength, int maxLength) {
        String blankSpace = "";
        while (blankSpace.length() + varLength <= maxLength) {
            blankSpace += (" ");
        }
        return blankSpace;
    }
}



