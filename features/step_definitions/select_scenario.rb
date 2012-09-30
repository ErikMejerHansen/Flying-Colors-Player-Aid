Given /^that Im playing "([^"]*)"$/ do |scenario|
	performAction('wait_for_text', 'New game')
	performAction('press','New game')
	performAction('wait_for_text', 'Select scenario')
	performAction('select_item_from_named_spinner', 'scenarios', scenario)
	performAction('press','Start')
	performAction('wait_for_text', 'Select agressor')
end