Given /^that Im firing from the "([^"]*)"$/ do |ship|
  performAction('press', ship)
end