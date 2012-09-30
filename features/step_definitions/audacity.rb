Given /^my Audacity is (\d+)$/ do |audacity|
  performAction('enter_text_into_named_field', audacity, 'Enter Audacity')
end