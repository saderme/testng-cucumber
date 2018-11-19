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

@Login 
Scenario Outline: User cannot login to BBOS through ID&V 
	When I login using "<username>" and "<password>" 
	And I reach the BBOS verify account key screen
	And I submit requested "<accountkey>" details
	Then I see an error on screen
	
	Examples: 
		| username | password   | accountkey  |
		|99999999  |P@ssw0rd9999|password9999 |		
	
   