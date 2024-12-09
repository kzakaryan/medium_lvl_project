# Search Engine
Designed for loading some data from a file and then running queries across this data.

### Prerequisites

- **Java** (JDK 8 or later)
- **Maven** (4.0.0)
- **people.txt** (Containing names for search)

### Setup Instructions

#### 1. Clone Repository

```bash
git clone git@github.com:kzakaryan/medium_lvl_project.git
cd medium_lvl_project
```

#### 2. Prepare ```people.txt ``` file
Data for the search engine should be provided in a text file.
```text
FirstName LastName Email
...
```

#### 3. Run the Application

##### - Install the necessary dependencies:
```bash
mvn install
```
##### - Run the application using Maven with data file path as a command-line argument
```bash
mvn exec:java -Dexec.args="--data path/to/people.txt"
```

#### Example of Running the Application
```
=== Menu ===
1. Find a person
2. Print all people
0. Exit
> 1

Select a matching strategy: ALL, ANY, NONE
> ANY

Enter a name or email to search all suitable people.
> Erick

3 persons found:
Erick Harrington harrington@gmail.com
Erick Burgess
John Doe johndoe@example.com
```