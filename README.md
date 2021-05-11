# Tutorial Project for Testing Web Applications with a Data Driven Testing Framework

### Applied technology:
- Language: Java 1.8
- Build Automation Tool: Maven
- Frameworks: JUnit 5
- Drivers used: Selenium
- Test website: demowebshop.tricentis.com

### Features:
- Separating Test Implementation and Test Specification
- Separating fixtures from tests
- Separating BOM layers
- Launching the browser in normal and non-graphical mode
- Run on Unix and Windows operating systems

## Installation and launch

- Copy the repository
- Add project to IDE
- Run command:
```sh
mvn test
```
To run tests on Unix, you need to run the command for the "/unix/chromedriver" file:
```sh
chmod u+x chromedriver
```
## For reference
- Browser driver included: ChromeDriver 90.0.4430.24

The browser driver is located in the folder: 
1. To Windows: src/test/resources/drivers/windows/chromedriver.exe
2. To Unix: src/test/resources/drivers/unix/chromedriver
