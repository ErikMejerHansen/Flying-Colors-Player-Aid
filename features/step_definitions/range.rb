Given /^my range is "([^"]*)"$/ do |range|
    performAction('set_named_seekbar', range)
end