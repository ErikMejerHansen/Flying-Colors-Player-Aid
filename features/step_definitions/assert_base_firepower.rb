Then /^my Base Firepower is "([^"]*)"$/ do |expected_firepower|
  performAction('assert_text_in_textview', "Base firepower: #{expected_firepower}", 'Caluclated base firepower')
end