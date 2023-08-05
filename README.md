Shopme
Shopme

📖 Information
Here is the explanation of the project
Shopme is a Spring Boot App as Monolith architecture. It is an e-commerce example.
It covers both admin part and customer part
All images are stored in Amazon S3 Bucket
Admin part handle with implementing the process of CRUD for category, product and monitor the past orders with respect its role covering Admin, Editor, Saleperson, Shipper and Assistant
Admin part is based i18n for Turkish and English
Customer register the system and login it. Next, s/he makes an order and apply payment and show previous orders
🔨 Run the App
Maven
1 ) Download your project from this link https://github.com/Rapter1990/Shopme

2 ) Go to the project's home directory : cd Shopme

3 ) Create a jar file though this command mvn clean install -DskipTests

4 ) Run the project though this command mvn spring-boot:run

Used Dependencies
Spring Boot Web
Java
JUnit
Thymeleaf
Spring Security
Oauth 2
Lombok
Amazon S3 bucket
Javascript
HTML
CSS
Amazon SDK
Super csv
Apache Poi ooxml
Librepdf
