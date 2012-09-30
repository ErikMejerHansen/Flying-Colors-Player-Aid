Feature: Firepower calculations
  In order to speed up gameplay
  As a player
  I want to have the Firepower calculated for me


Scenario: Firing at range 5 from a ship with rate 1 in 1780
Given that Im playing "test scenario1"
And that Im firing from the "Ville de Paris"
And my range is "5"
Then my firepower is "8"

Scenario: Firing at range 5 from a ship with rate 2 in 1780
Given that Im playing "test scenario1"
And that Im firing from the "Barfleur"
And my range is "5"
Then my firepower is "6"

Scenario: Firing at range 5 from a ship with rate 3 in 1780
Given that Im playing "test scenario2"
And that Im firing from the "Bedford"
And my range is "5"
Then my firepower is "5"

Scenario: Firing at range 5 from a ship with rate 4 in 1780
Given that Im playing "test scenario2"
And that Im firing from the "Bellerophon"
And my range is "4"
Then my firepower is "4"

Scenario: Firing at range 5 from a ship with rate 5 in 1780
Given that Im playing "test scenario3"
And that Im firing from the "Blenheim"
And my range is "5"
Then my firepower is "2"

Scenario: Firing at range 5 from a rate 1 ship with a white circle rate symbol in 1780
Given that Im playing "test scenario3"
And that Im firing from the "Conquérant"
And my range is "5"
Then my firepower is "9"


Scenario: Firing at range 5 from a rate 3 ship with a black circle rate symbol in 1780
Given that Im playing "test scenario4"
And that Im firing from the "Firme"
And my range is "5"
Then my firepower is "5"

Scenario: Firing at range 5 from a rate 1 ship with a yellow circle rate symbol in 1780
Given that Im playing "test scenario4"
And that Im firing from the "Montagne"
And my range is "5"
Then my firepower is "10"


Scenario: Firing at range 5 from a rate 2 ship with a red hex rate symbol in 1780
Given that Im playing "test scenario5"
And that Im firing from the "Glatton"
And my range is "5"
Then my firepower is "6"


Scenario: Firing at range 5 from a rate 2 ship with a white hex rate symbol in 1780
Given that Im playing "test scenario5"
And that Im firing from the "Tonnant"
And my range is "5"
Then my firepower is "6"


Scenario: Firing from a rate 2 ship at range 1 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "1"
Then my firepower is "17"

Scenario: Firing from a rate 2 ship at range 2 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "2"
Then my firepower is "12


Scenario: Firing from a rate 2 ship at range 3 in 1780
Given that Im playing "test scenario6$"
And that Im firing from the "Windsor Castle"
And my range is "3"
Then my firepower is "10"

Scenario: Firing from a rate 2 ship at range 4 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "4"
Then my firepower is "7"

Scenario: Firing from a rate 2 ship at range 5 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "5"
Then my firepower is "6"

Scenario: Firing from a rate 2 ship at range 6 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "6"
Then my firepower is "4"

Scenario: Firing from a rate 2 ship at range 7 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "7"
Then my firepower is "2"

Scenario: Firing from a rate 2 ship at range 8 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "8"
Then my firepower is "2"

Scenario: Firing from a rate 2 ship at range 9 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "9"
Then my firepower is "1"

Scenario: Firing from a rate 2 ship at range 10 in 1780
Given that Im playing "test scenario6"
And that Im firing from the "Windsor Castle"
And my range is "10"
Then my firepower is "0"


Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is on fire in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is on fire
Then my firepower is "5"


Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is rotating at anchor in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is rotating at anchor
Then my firepower is "5"

Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is firering partial broadside in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is firing a partial broadside
Then my firepower is "5"

Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is tacking in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is tacking
Then my firepower is "5"

Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is at full sails in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is at full sails
Then my firepower is "5"

Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is dismasted in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is dismasted
Then my firepower is "5"


Scenario: Firing at range 5 from a rate 2 ship at range 5, ship is dismasted, on fire, firing a partial broadside while tacking and on fire in 1780
Given that Im playing "test scenario1"
And that Im firing from the "[ship with rate 2]"
And my range is "5"
And my ship is dismasted
And my ship is on fire
And my ship is firing a partial broadside
And my ship is tacking
Then my firepower is "1"








#Still missing: Audcity, caronade (/w different eras), shaded table, damage




