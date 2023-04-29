Feature: Open SVT Play

Scenario: SVT Play Title is visible
  When Visit SVT Play
  Then Verify the Page Title
  And close the driver

Scenario: SVT Play Logo is visible
  When Visit SVT Play
  Then Verify the Page Logo
  And close the driver

Scenario: Links in the Main Menu
  When Visit SVT Play
  Then Verify the Menu Links
  And close the driver

Scenario: Link for Tillgänglighet i SVT Play
  When Visit SVT Play
  Then Verify the Tillgänglighet i SVT Play
  And close the driver

Scenario: Follow Link for Tillgänglighet i SVT Play
  Given Visit SVT Play
  When Click the TillganglighetLink
  Then Verify the Click On TillganglighetLink
  And close the driver

Scenario: Click the Program Tab
  Given Visit SVT Play
  When Click the program tab
  Then Verify the categories are displayed
  And close the driver

Scenario: Validate the Search Option
  Given Visit SVT Play
  When Click on the Search tab
  And Provide input as musik
  When Click the enter
  Then Verify the musik categories are displayed
  And close the driver

Scenario:  Verify Click on Humor Category
  Given Visit SVT Play
  When Click on Humor Link
  Then Validates the title of the Humor category page
  And close the driver

Scenario:  Verify Click on SVT Logo on Other Page
  Given Visit SVT Play
  When Click on Humor Link
  Then Validates the title of the Humor category page
  When Click on SVT logo on other Page
  Then Verify the Page Title
  And close the driver

Scenario:  Verify Click on Next Arrow Button
  Given Visit SVT Play
  When Click on Next Arrow Button
  Then Back Arrow Button should be visible
  And close the driver

Scenario:  Verify Footer on Page
  Given Visit SVT Play
  When Scroll to Footer
  Then Contact us should be visible in footer
  When Click on contact us
  Then Verify title on contact us
  And close the driver
