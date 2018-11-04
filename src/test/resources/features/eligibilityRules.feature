@FunctionalTest
Feature:  Eligibility rules to display the citi ad on the prepaid bags.
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18, guest and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | 
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89 |
   And for the locale  "en_US" and deviceType as "Web"
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 01FEB89 |
   And  the Passenger is Eligible For Bag Product
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number, country of residence as US  and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            | US                 |
    And for the locale  "en_US" and deviceType as "Web"      
    When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 02FEB75 |
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number, countryOfResidence is NA  and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  | AdvantageNumber | OneWorldCarrier  |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75     |  6L546E6        |         AA       |
   And for the locale  "en_US" and deviceType as "Web"  
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 02FEB75 | 
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, guest,  country of residence as US and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    US              |
   And for the locale  "es_US" and deviceType as "Web"    
   When the pnr is created
  Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 01FEB89 | 
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number , country of residence as US and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
   And for the locale  "es_US" and deviceType as "Web"    
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 02FEB75 | 
   
  @color=SINGLEPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number , country of residence as NA and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |   
   And for the locale  "es_US" and deviceType as "Web"    
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Samuel  | 
   | lastName    | Junior |
   | dateOfBirth | 02FEB75 | 
 
   
   #Negative scenarios  
  @color=SINGLEPAX_NEGATIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number, countryOfResidence is Mexico  and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier |  countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75     |  6L546E6       |    AA           |      MX             |
   And for the locale  "en_US" and deviceType as "Web"  
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
    
   @color=SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18 with a NA advantage number, countryOfResidence is CANADA  and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  |  countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75     |      CA             |
   And for the locale  "en_US" and deviceType as "Web"  
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
   @color=SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18 with a other airline BA frequent flyer number, countryOfResidence is NA  and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  10018333      |    BA           |
   When the pnr is created
    And for the locale  "en_US" and deviceType as "Web" 
    Then the eligibility rules fail and the response would not have any passengers.
   
   @color=SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18 with a other QF airline frequent flyer number, countryOfResidence is NA  and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier |  
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  0002850        |    QF           |
    And for the locale  "es_US" and deviceType as "Web" 
    When the pnr is created
    Then the eligibility rules fail and the response would not have any passengers.
   
    @color=SINGLEPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for a passenger whose age is > 18 with a valid advantage number , country of residence as US and locale is en_CA
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
    And for the locale  "en_CA" and deviceType as "Web"    
    When the pnr is created
    Then the eligibility rules fail and the response would not have any passengers.
   
    @color=SINGLEPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for a passenger whose age is < 18 with a valid advantage number , country of residence as US and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |  6L546E6        |    AA            |     US             |
    And for the locale  "es_US" and deviceType as "Web"    
     When the pnr is created
     Then the eligibility rules fail and the response would not have any passengers.
   
    @color=SINGLEPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for a passenger whose age is < 18 without advantage number , country of residence as US and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |     US             |
    And for the locale  "es_US" and deviceType as "Web"    
    When the pnr is created
     Then the eligibility rules fail and the response would not have any passengers.
   
    @color=SINGLEPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for a passenger whose age is < 18 with a valid advantage number , country of residence as CA and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |  6L546E6        |    AA            |     CA             |
    And for the locale  "es_US" and deviceType as "Web"    
    When the pnr is created
     Then the eligibility rules fail and the response would not have any passengers.
     
     @color=SINGLEPAX_NEGATIVE_SCENARIO
     Scenario: Eligibility rules for a passenger whose age is < 18 without advantage number , country of residence as US and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |     US             |
     And for the locale  "en_US" and deviceType as "Web"    
     When the pnr is created
     Then the eligibility rules fail and the response would not have any passengers.
   
   @color=SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is < 18 with a valid advantage number , country of residence as CA and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |  6L546E6        |    AA            |     CA             |
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
     Then the eligibility rules fail and the response would not have any passengers.
     
   
   @color=SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is < 18 with a valid advantage number , country of residence as US and locale is en_CA 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |  6L546E6        |    AA            |     US             |
   And for the locale  "en_CA" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   @color=SINGLEPAX_NEGATIVE_SCENARIO
  Scenario: Eligibility rules for a passenger whose age is < 18 with NA  advantage number , country of residence as NA and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    | 
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   #Multiple Paasenger's scenario's
   @color=MULTIPAX_POSITIVE_SCENARIO
   Scenario: Eligibility rules for mutliple pasenger whose age is > 18 with a valid advantage number , country of residence as US and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB80    |  6L559E4        |    AA            |     US             |
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then check the eligigbility rules for the mutilple Passenger's and expect a response 
   
  @color=MULTIPAX_POSITIVE_SCENARIO
  Scenario: Eligibility rules for mutliple pasenger whose age is > 18 with a valid advantage number , country of residence NA and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | 
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |    
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB80    |  6L559E4        |    AA            | 
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then check the eligigbility rules for the mutilple Passenger's and expect a response 
   
   
   #NEGATIVE SCENARIOS
   
    @color=MULTIPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for mutliple pasenger whose age is > 18 with a valid advantage number , country of residence as CA and locale is es_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB10    |  6L559E4        |    AA            |     CA             |
   And for the locale  "es_US" and deviceType as "Web"    
   When the pnr is created
    Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Michael  | 
   | lastName    | Chang |
   | dateOfBirth | 02FEB75 |  
   
    @color=MULTIPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for mutliple pasenger, one of the passenger's  age is < 18 with a valid advantage number , country of residence as US and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Michael  | 
   | lastName    | Chang |
   | dateOfBirth | 02FEB75 |
   
    @color=MULTIPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for mutliple pasenger, both  the passenger's  age is < 18 with a valid advantage number , country of residence as US and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L546E6        |    AA            |     US             |
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
    @color=MULTIPAX_NEGATIVE_SCENARIO
    Scenario: Eligibility rules for mutliple pasenger, one of the passenger's  age is > 18 with a valid advantage number , country of residence as US and locale is en_US 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | AdvantageNumber | OneWorldCarrier  | countryOfResidence |
       | Michael    | Chang    | PHX    | SBP         | 0                          | AA      | now+10     | 02FEB75    |  6L546E6        |    AA            |     US             |
       | Michael    | Stich    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
       | Li         |   Na    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
       | Wang        | Qiang    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
       | Zhang    | Shuai    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
       | Zheng    | Saisai    | PHX    | SBP         | 0                          | AA      | now+10     | 02JAN10    |  6L559E4        |    AA            |     US             |
   And for the locale  "en_US" and deviceType as "Web"    
   When the pnr is created
   Then  check the eligibility rules for the Passenger and expect a response 
   | firstName | Michael  | 
   | lastName    | Chang |
   | dateOfBirth | 02FEB75 |
   
   
   
   #locale testing
    @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, guest,  country of residence as US and locale is china 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence | locale |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    US              |  zh_CN  |
   And for the locale  "zh_CN" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
    @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, has a OA FF #,  country of residence as US and locale is UK 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence | locale | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    US              |  en_GB |       601083819  |       AY        |
   And for the locale  "en_GB" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
     @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, has a AA FF #,  country of residence as US and locale is dominician republic 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence | locale | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    US              |  es_DO |       6L546E6  |       AA        |
   And for the locale  "es_DO" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
      @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, guest and locale is Colombia
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  | locale |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89     |  es_CO  |
   And for the locale  "es_CO" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
     @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, valid OA FF# and locale is Brazil
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  | locale | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89     |  pt_BR  |    0010345     |       QF        |
   And for the locale  "pt_BR" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
   @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, has a AA FF #and locale is Mexico
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  | locale | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89     |  es_MX |       6L546E6  |       AA        |
   And for the locale  "es_MX" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
    @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, has a AA FF #,  country of residence as MX and locale is China
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence | locale |
       | Naomi    | Osaka    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    US              |  zh_CN |     
   And for the locale  "zh_CN" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
   @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, valid OA FF# , country of residence as CA and locale is UK
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth  | locale | AdvantageNumber | OneWorldCarrier | countryOfResidence |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89     |  pt_BR  |    0010345     |       QF        |       CA           |
   And for the locale  "en_GB" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
   @color=LOCALE_SINGLEPAX_NEGATIVE_SCENARIO
   Scenario: Eligibility rules for a passenger whose age is > 18, has a AA FF #,  country of residence as US and locale is dominician republic 
    Given passenger booking a flight ticket
       | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth |  countryOfResidence | locale | AdvantageNumber | OneWorldCarrier |
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89    |    UK             |  es_DO |       6L546E6  |       AA           |
   And for the locale  "es_DO" and deviceType as "Web"    
   When the pnr is created
   Then the eligibility rules fail and the response would not have any passengers.
   
   
