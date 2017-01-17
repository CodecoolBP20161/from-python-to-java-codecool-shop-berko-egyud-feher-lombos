# :star2: :star2: :star2: :star2: :star2: Codecool Online Shop :star2: :star2: :star2: :star2: :star2:

The goal was to build an Online Shop, an online eCommerce web-application with Java.
Where users can browse products, add them into a Shopping Cart, checkout items and make payments.

# Preview 

!["preview"](src/main/resources/public/img/preview_home.png)

!["preview"](src/main/resources/public/img/login.png)

!["preview"](src/main/resources/public/img/signup.png)

!["preview"](src/main/resources/public/img/preview_cart.png)

!["preview"](src/main/resources/public/img/checkout.png)

!["preview"](src/main/resources/public/img/shippinginformation.png)

!["preview"](src/main/resources/public/img/pay.png)


# :lollipop: :lollipop: :lollipop: User Stories :lollipop: :lollipop: :lollipop:

1. Development / Create an environment :white_check_mark:
2. Products / List :white_check_mark:
3. Products / by Product Category :white_check_mark:
4. Products / by Supplier :white_check_mark:
5. Shopping Cart / add to Cart :white_check_mark:
6. Shopping Cart / Review :white_check_mark:
7. Shopping Cart / Edit :white_check_mark:
8. Shopping Cart / Checkout :white_check_mark:
9. Shopping Cart / Payment :white_check_mark:
10. Shopping Cart / Confirmation :white_check_mark:
11. Admin log
12. Products / DB  :white_check_mark:
13. Products / memory DAO testing  :white_check_mark:
14. Product / DB / Testing  :white_check_mark:
15. Database / Config :white_check_mark:
16. Products / List / Unit Test :white_check_mark:
17. Products / by Product Category / Unit test :white_check_mark:
18. Products / by Supplier / Unit test :white_check_mark:
19. Shopping Cart / add to Cart / Unit test :white_check_mark:
20. Shopping Cart / Review / Unit test
21. Shopping Cart / Edit / Unit test
22. Shopping Cart / Checkout / Unt test
23. Shopping Cart / Payment / Unit test
24. Shopping Cart / Confirmation / Unit test
25. Shopping Cart / Safe Checkout
26. User / Registration :white_check_mark:
27. User / Login - Logout :white_check_mark:
28. User / Order history
29. User / Save shopping cart
30. User / Save billing and shipping info
31. Shopping Cart / Safe Checkout / Test
32. User / Registration / Test
33. User / Login - Logout / Test
34. User / Order history / Test
35. User / Save shopping cart / Test
36. User / Save billing and shipping info / Test

We undertook the marked pipe stories and we could accomplish all of them at this week /2016.11.7 - 2016.11.24/.


# Technical requirements

We had to take consideration the following requirements:
- Work with GIT flow.
- Use JDBC for the database access.
- Use the database through the DAO pattern (Data Access Object).
- Suggested (though not required) to cover the new code with tests.
- Use advanced OOP concepts:
- Use inheritance
- Write at least one Abstract class
- Implement at least one Interface
- It's not required to integrate real payment services 

# Install & Usage the App

To operating the code, you need to clone two other repo. 
    1. :envelope::envelope::envelope: [Postal Fee Calculator](https://github.com/CodecoolBP20161/from-python-to-java-microservices-los_patrones)
    2. :hourglass::hourglass::hourglass: [Delivery Time Calculator](https://github.com/CodecoolBP20161/from-python-to-java-microservices-team2)

1. Import this project to IntelliJ as a Maven project. (IntelliJ can auto-install the dependencies from the pom.xml)
2. Fill `resources/connection/connection.properties` config file with your database & email's properties
3. run `src\main\java\Main.java`
4. run  `from-python-to-java-microservices-los_patrones\src\main\java\Main.java` (postal_fee_calculator microservice)
4. In your browser, type `localhost:8888`

#  Usage the Test

In test/java/com/codecool/shop there are two different folders. 
You can just simply `run JunitTestRunner` file with CodeCoverage.







