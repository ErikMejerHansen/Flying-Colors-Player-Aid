Feature: Base Firepower
  In order to speed up gameplay
  As a player
  I want to have the Base Firepower calculated for me

Scenario: British 2nd rate ship in 1779
  Given that Im firing from a "2nd" rate "British" ship in "1779"
  And that the range is 5
  Then my Base Firepower is "6"

Scenario: British 1st rate ship in 1779
  Given that Im firing from a "1st" rate "British" ship in "1779"
  And that the range is 2 
  Then my Base Firepower is "16" 

Scenario: French G rate ship in 1826
  Given that Im firing from a "G" rate "French" ship in "1826"
  And that the range is 6 
  Then my Base Firepower is "-" 

Scenario: 6th rate Spanish ship in 1786
  Given that Im firing from a "6th" rate "Spanish" ship in "1786"
  And that Im firing with full sails
  And that Im firing during a tack
  And Im firing a partial broadside 
  Then my Base Firepower is "-"

Scenario: British 2nd rate ship in 1779
  Given that Im firing from a "2nd" rate "British" ship in "1779"
  And that the range is 5
  And my Audacity is 2
  Then my Base Firepower is "8"

Scenario: British 1st rate ship in 1787
  Given that Im firing from a "1st" rate "British" ship in "1787"
  And that the range is 1
  And my ship has a square rate value
  Then my Base Firepower is "21"