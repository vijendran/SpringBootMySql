# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.3/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Tech Stack
* Spring Boot
* JSTL tags to populate in front end 

# To run the project
* import the project to intellij
* gradle 6 and above will work fine
* To install gradle 
  * [https://sdkman.io/sdks#gradle](To install sdk man and gralde)
  * Sdk man is a package manager helps to jungle between versions on demand and
* Google Format Java is used to format the code 
  * [https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml](Formatter xml file)
* Sonar lint is used to maintain some code standards 
  * [https://plugins.jetbrains.com/plugin/7973-sonarlint](Sonar Lint)

### Project Explanation
* "/" is where you can upload the file(Excel)
* /view is the page to render the persisted data
* Sample Excel file is added to the resources folder with null values too
* Mock Data is generated from the below tool 
  * [https://www.mockaroo.com/](Mock data generator)
* Install mysql 
  * [https://dev.mysql.com/doc/refman/8.0/en/macos-installation.html](For mac)
* Running the project can be done seemlessly using intellij UI with out any hustle
* Database URl and credentials need to be updated only in DatabaseConnection.java