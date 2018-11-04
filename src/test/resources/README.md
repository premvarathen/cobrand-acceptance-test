# Acceptance Test User Guide

To learn about how Serenity is structured and how it works. Go here http://www.thucydides.info/docs/serenity

When you clone this repo, run the below command to download all of the dependencies.
If it not successful, you might need to connect to a wifi network outside of the AA firewall.

    mvn install

## Build Process
### Local Build
* If connected with enterprise, then use:  `mvn clean verify -Pbdd -Dhost="https://aa-ct-fly-merchandising-bff.mybluemix.net" -DreqProxy="true"`
* If connected with Guest then no need to pass -DreqProxy, Use: `mvn clean verify -Pbdd -Dhost="https://aa-ct-fly-merchandising-bff.mybluemix.net"`
* Default value for host if not passed as argument is whatever is set as HOST_DEFAULT in BDDConstants.java
* Default value for reqProxy argument is false, i.e. do not add proxy info.
* Once Test gets Executed, The result in local can be seen under `\aa-ct-loyalty-cobrand-citi-acceptance-test\target\site\serenity\index.html`.

### Run Specific scenarios which have been tagged
If you want to run just selected scenario among our many scenarios in feature files, tag those scenario(s) in feature file and then give additional options in mvn verify command in order to filter and run only tagged scenario. This helps a lot for early feedback if you are in the development phase of a particular feature and runs the  report for that one only.

Example: I want to run only one scenario from eligibility feature file.
1: Tag Scenario as follows like @color=red. Format is @tagName where tagName is in form of color=yourPick

```
@color=red
Scenario: Checking Baggage Eligibility within check in window
  Given Passenger with the following reservation wants to check bags online
    | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime |
    | John      | Smith    | DFW    | LAX         | 1                          | AA      | 2a         |
  When I check bag eligibility for above passenger
  Then I should be able to see customer's eligible to buy bags as following
    | SliceId | EligibleProduct |
    | 1       | YES             |
```

2: Now Run Following Maven Command selecting particular tag as:
  
  ```mvn clean verify -Dcucumber.options="--tags @color=red"```

Notice that you are passing tag name in mvn command. This is how in future we can define our selected suite of test among many tests that we like to run such as tag with @color=regression etc.

### Feature Template
```
Feature: [Title of the feature]**  
  In order to [achieve accomplish goal of feature],  
  As an [Actor/Consumer/Customer/User],  
  I want to be able to [Action done by feature], **so that** [End Result] 
```  
**Example**:  
```
Feature: Standalone Checking Eligibility  
  In order to buy bags online,  
  As an airline customer,  
  I want to be able to check bags eligibility so that I can purchase bags and check-in online
```
### Scenario Template
```
Scenario: [Scenario Description]  
    Given [Describe the Input Data Given Below]  
      | data-header1 | data-header2 |  
      | data1        | data2        |  
    When [The Action is performed on above given data]  
    Then [I should be able to see expected result]  
```
**Example**:  
```
Scenario: Checking Baggage Eligibility within check in window  
    Given Passenger with the following reservation wants to check bags online
      | firstName | lastName | origin | destination | departureDate | journeyType |  
      | John      | Smith    | DFW    | HOU         | NOW + 4 Hours | OW          |  
    When I check bag eligibility for above passenger  
    Then I should be able to see customer is eligible to buy bag for given slice  
```

To build the tests:

    mvn test

To run the tests:

    mvn clean verify

To see the test results report look here:

    serenity-acceptance-tests\target\site\serenity\index.html


The feature files are located in:

    serenity-acceptance-tests\src\test\resources\features

The page object classes are located in:

    serenity-acceptance-tests\src\test\java\common\pageObjects

The step definitions are located in:

    serenity-acceptance-tests\src\test\java\features
