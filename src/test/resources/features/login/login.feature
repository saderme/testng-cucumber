@login 
Feature: BBOS Login Tests 

Background: 
	Given I am on the BBOS login screen 
 
@smoke @Login 
Scenario Outline: User login to BBOS through ID&V 
	When I login using "<username>" and "<password>" 
	And I reach the BBOS verify account key screen
	And I submit requested "<accountkey>" details
	Then I reach the BBOS summary screen
	
	Examples: 
		| username | password   | accountkey  |
		|66599020  |P@ssw0rd1234|password1234 |

@Loginpojo2 
Scenario Outline: User can login to BBOS through ID&V 
	When I login using "<username>" credentials
	Then I reach the BBOS summary screen
	
	Examples: 
		| username | 
		|66599020  |   