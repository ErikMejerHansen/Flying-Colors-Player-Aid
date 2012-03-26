Given /^that Im firing from a "([^"]*)" rate "([^"]*)" ship in "([^"]*)"$/ do |rate, nationality, year|
  	performAction('select_item_from_named_spinner', 'Select rate', rate)
	performAction('select_item_from_named_spinner', 'Select Nationality', nationality)
	performAction('enter_text_into_named_field', year, 'Enter year')
end