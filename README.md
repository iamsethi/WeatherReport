# Weather Report
UI-API Automation TestNG Framework for execution on Chrome 

## Tools
* LANGUAGE : JAVA 
* AUTOMATION : SELENIUM,REST ASSURED
* REPORTER : EXTENT REPORTER

## Architecture
* ./src/test/java : Contains all test class
* ./src/test/resources : Environment Data and TestNG xml
* ./src/main/java : 
  * Factory : Contains all the driver factory classes
  * Pages : Contains project related Page Objects
  * api  :  api action methods to make rest api calls
  * pojo :  POJO classes for weather response
  * utilities : RestUtilities,Comparator and YamlReader
  

## Reports
/target/surefire-reports/ExtentReportTestNG.html

## Deployment instructions for local execution
* CLI execution - mvn clean test
