Feature: Purchasing one or more Bags
  In order to buy bags online,
  As an airline user,
  I want to be able to select bag(s) and purchase it online

  @color=GUEST_SINGLEPAX_OW
  Scenario: Guest Single passenger pnr travelling one way with single segment purchasing bags
    Given Reservation
      | FirstName | LastName | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime | dateOfBirth | 
       | Samuel    | Junior    | PHX    | SBP         | 0                          | AA      | now+10     | 01FEB89 |
    When the user has a reservation
    Then the Passenger is Eligible For Bag Product

#  @color=GUEST_MULTIPAX_OW
#  Scenario: Multi passenger traveling One Way Non Stop purchasing multiple bags
#    Given Reservation
#      | NumberOfPassengers | Origin | Destination | DepartingNoOfDaysFromToday | Carrier | DepartTime |
#      | 2                  | DFW    | LAX         | 0                          | AA      | now+15     |
#    When the user has a reservation
#    Then the Passenger is Eligible For Bag Product