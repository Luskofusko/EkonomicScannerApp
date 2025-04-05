# EkonomicScannerApp

An Android application that allows to take a picture of invoices or receipts with the purpose of managing expenses

### Features

- Taking a picture of a owned invoice
- Manually insert invoice details the user doesn't own
- Prompt a list of receipts already added
- Manage existing receipts (change data or delete)



### Architecture

###### This App is designed in Clean Architecture

![](https://miro.medium.com/v2/resize:fit:700/1*9Ds8N-6LZNDNclnZEyX48w.png)

It's composed by four modules:

- data - Data layer. Where the repositories are implemented. DAOs, Database setup and mappers are also defined here;
- domain - Domain layer. Where the business rules are defined. Use Cases and Repository Interfaces are located here;
- presentation - Presentation Layer. View implementation, View Models, Navigation, and Ui related elements are defined here;
- app - Application Entry Point. Where startup logic is defined. Some initial permissions are implemented here.

##### Dependency Injection and Module Dependencies

This application uses Hilt as a DI.

The domain module has no dependencies. It's the core of business logic and should be independent

The data module implements the interfaces defined in the domain module

The presentation module uses the domain layer through use cases

The app module acts as the orchestrator that wires everything together



##### Run

- Clone this repository;
- Open project in Android Studio
- Run the Application



##### Build

In order to build the project and generate APKs, execute the following command:

```
./gradlew build
```



