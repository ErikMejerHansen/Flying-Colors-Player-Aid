Then /^my firepower is "([^"]*)"$/ do |firepower|
    performAction('assert_text_in_textview', firepower)
end

Then /^my firepower is "(\d+)$/ do |firepower|
	performAction('assert_text_in_textview', firepower)
end