# CovidDataAnalyticTerminalApplication

## Terminal approach for Covid Data Analytics. 
### About 
- This repository is the written in Java as terminal approach application for initial data analytic using csv file form WHO. The application let user choose the way of specifying time range, calculation methods and display methods from interactive terminal command. 

--------------------------------------------------

Problem details
The COVID-19 pandemic is becoming worse over time. Although not having a firm background in medicine and health, this project aims to contribute to the community in fighting this battle. By reading science articles, you know that health decision-makers need to process a lot of data to make informed and insightful decisions. 
The most important weapon you need for this project is data. Fortunately, pandemic data is made available by many organizations. The data file used in this project is provided by WHO. It’s a CSV file. However, it has been deleted some unused columns. The column names are straightforward as well as the data.

----------------------------------------------------
<strong>1. Data: each data object has a geographic area, which can be either a country (e.g., Vietnam) or a continent (e.g., Asia), and a time range. The time range can be specified as one of the following:</strong> 

<li> A pair of start date and end date (inclusive) (e.g., 1/1/2021 and 8/1/2021)</li> 
<li> A number of days or weeks from a particular date (e.g., 2 days from 1/20/2021 means there are 3 days 1/20/2021, 1/21/2021, and 1/22/2021)</li> 
<li> A number of days or weeks to a particular date (e.g., 1 week to 1/8/2021 means there are 8 days from 1/1/2021 to 1/8/2021)</li> 
----------------------------------------------------


<strong> 2. Summary: </strong> this is the data after processed and ready to display. To create summary data, original data are grouped (2.1), a metric is chosen (2.2), and a result is calculated (2.3). The possible ways of specifying groupings are (explanation of 2.1):

<li> No grouping: each day is a separate group. </li>
<li> Number of groups: a number is specified and you must divide your data into that number of groups. You need to divide your data as equally as possible.</li>

<li> Number of days: a number is specified and you divide your data into groups so that each group contains that number of days. For this grouping, if it is not possible to divide groups equally, raise an error telling the caller about that. </li>

<li> After specifying a grouping method, a metric is chosen. There are 3 possible metrics (explanation of 2.2): positive cases, deaths, and people vaccinated.</li>
-----------------------------------------------------

<strong> Calculation methods </strong>

<li> <strong> New Total: </strong> total new cases/new deaths/new vaccinated people in a group. </li>

<li> <strong> Up To: </strong> total cases/deaths/vaccinated from the beginning up to the last date of a group </li>
-----------------------------------------------------

<strong> 3.Display: </strong> summary data is displayed to viewers. There are 2 ways to display data

<li> <strong>Tabular display: </strong> display summary data in a table. There are 2 columns: the first column named “Range” and the second column named “Value”. In the table, display a row for each group. For each group, the “Range” column shows “date1 – date2” where date1 and date2 are the first date and last date of a group respectively. If a group contains just 1 date, shows that date only. The “Value” column of a group shows the calculated value (New Total or Up To) described above. </li>

<li> <strong> Chart display: </strong> display summary data in a textual chart. The chart area consists of 24 rows x 80 cols. The x-coordinate direction is left to right, and the y-coordinate direction is from bottom to top. The x-coordinate represents the groups and the y-coordinate represents the calculated summary results. You should position the groups as equally as possible on the x-coordinate. And you should use the minimum and maximum result values to position a result on the y-coordinate linearly. The left-most column should display all | (pipe) characters while the bottom-most row should display all _ (underscore) characters. (That means you have 23 rows and 79 columns left to display data points). Each summary data point is represented as an asterisk *. </li>


