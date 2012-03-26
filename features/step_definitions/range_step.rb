Given /^that the range is (\d+)$/ do |range|
  performAction('enter_text_into_named_field', range, 'Enter Range')
end