This is the one of the assignments for the Software Design course and it uses the Spring Framework.
	
I tried my best to respect the SOLID Design Principles and to implement some design patterns such as Decorator, Builder and a pseudo-Factory (as the actual creation of the components was ensured by Spring).

The main functionalities of the application are:
* The administrator can perform CRUD operations on agents and on the categories of the website.

* The agent can perform CRUD operations on his/her offers and will receive an email when his/her offers receive any review. Offers can belong to one or more categories.

* The customer can buy offers and he/she will be sent a confirmation email containing the sale details. The customer can perform CRUD operations on his/her reviews. 
Moreover, he/she can subscribe/unsubscribe of the categories of the website. When offers are added to any of the categories the customer is subscribed to, he/she will receive an email notification. 
