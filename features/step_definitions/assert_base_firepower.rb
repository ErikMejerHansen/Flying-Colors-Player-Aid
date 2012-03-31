Then /^my Base Firepower is "([^"]*)"$/ do |expected_firepower|
  performAction('assert_text', 'Base firepower:', true)
  performAction('assert_text_in_textview', expected_firepower, 'Caluclated base firepower')
end