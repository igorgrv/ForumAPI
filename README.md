# Forum -  "API REST" from scratch
## About the Project

The **purpose** of this project is to create a **Forum**, where we can post, remove, list  our doubts!

### Technologies/Database used
-   Spring Boot;
-   API Rest;
-   BeanValidation;
-   JPA;
-  MySQL;
-  Spring Initializer/DevTools

## Summary
1. [Starting the project](#starting)
2. [MVC](#mvc)
	* [Models](#models)
3. [Creating the first API Endpoint](#first)
4. [Implementing JPA and H2 Database](#jpa)
5. [Models](#models)
6. [Models](#models)
7. [Models](#models)

## <a name="starting"></a>Starting the project
1. Create the artifact: **forum**;
2. GroupId: **com.forum**;
3. Add the dependencies: **Web, JPA, MySQL Connector and DevTools**;
4. Unzip the file and import as "maven project";

## <a name="mvc"></a>MVC
### <a name="models"></a>Models

* [Download the Models](https://github.com/igorgrv/ForumAPI/blob/master/Model.rar)  and import into the project!

PayAttention! Create the package model inside the package.forum (where the main class is)

**Understanding the models with UML:**
<img src="https://github.com/igorgrv/ForumAPI/blob/master/UML/simplyUml.PNG?raw=true" width=600 height=500>

## <a name="first"></a>Creating the first API Endpoint
By default, Spring uses the Jackson library, which transforms a Java List into a .json format. <br>
In short, Spring takes the List -> passes it to Jackson -> Jackson transforms it to .json -> Spring returns it to the browser.

### An example:
1. Create a **controller** package, into the _forum package_;
2. Add the code below:
```java
@Controller
public class TopicController {

	@RequestMapping("/firstEndPoint")
	@ResponseBody
	public List<Topic> list(){
		Topic topic = new Topic("Doubt", "API Doubt", new Course("Java", "API"));
		
		return Arrays.asList(topic, topic, topic);
	}
	
}
```
3. Spring will return the .json below:
	* To format the .json as below, use a  [jsonFormatter](jsonformatter.curiousconcept.com/)
```json
[
	{
		"id":null,  
		"title":"Doubt",  
		"post":"API Doubt",  
		"creationDate":"2020-04-07T16:35:22.370081",  
		"status":"NOT_ANSWERED",  
		"user":null,  
		"course":{
			"id":null,  
			"name":"Java",  
			"category":"API"  
		},  
		"answers":[
		]  
	},  
	{
		"id":null,  
		"title":"Doubt",  
		"post":"API Doubt",  
		"creationDate":"2020-04-07T16:35:22.370081",  
		"status":"NOT_ANSWERED",  
		"user":null,  
		"course":{

		"id":null,  
		"name":"Java",  
		"category":"API"  
		},  
		"answers":[
		]  
	},  
	{
		"id":null,  
		"title":"Doubt",  
		"post":"API Doubt",  
		"creationDate":"2020-04-07T16:35:22.370081",  
		"status":"NOT_ANSWERED",  
		"user":null,  
		"course":{

		"id":null,  
		"name":"Java",  
		"category":"API"  
		},  
		"answers":[
		]  
	}  
]
```
### Simplifying the example:
**_@ResponseBody_**
So that we don't have to keep using `@ResponseBody` every time, Spring has an annotation, called `@RestController`, which will tell Spring that all of the controller's methods will not return a view/page!

**_DTOClass (DataTransferObject)_**
DTOClass, is a class that will **only pass the necessary attributes**, that is, there is greater flexibility in choosing what will be in the API. <br> _In the example used, we passed all the attributes of the Topic class, which will have all the attributes of more classes, which is bad!_

TopicDTO:
```java
public class TopicDTO {

	private Long id;
	private String title, post;
	private LocalDateTime creationDate;
	
	//Constructor will get the values of the Topic, because our class isn't a Entity, so will not have datas
	public TopicDTO(Topic topic) {
		this.id = topic.getId();
		this.title = topic.getTitle();
		this.post = topic.getPost();
		this.creationDate = topic.getCreationDate();
	}
	
	//This method is necessary to return a TopicDTO, once that we won't use the Topic anymore
	public static List<TopicDTO> toTopic(List<Topic> topics) {
		return topics.stream().map(TopicDTO::new).collect(Collectors.toList());
	}
	
	//ONLY getters
}
```
Controller:
```java
@RestController
public class TopicController {

	@RequestMapping("/firstEndPoint")
	public List<TopicDTO> list(){
		Topic topic = new Topic("Doubt", "API Doubt", new Course("Java", "API"));
		
		return TopicDTO.toTopic(Arrays.asList(topic, topic, topic));
	}
}
```
Spring will return the .json below:
```json
[
	{
	"id":null,  
	"title":"Doubt",  
	"post":"API Doubt",  
	"creationDate":"2020-04-08T00:02:47.8391678"  
	},  
	{
	"id":null,  
	"title":"Doubt",  
	"post":"API Doubt",  
	"creationDate":"2020-04-08T00:02:47.8391678"  
	},  
	{
	"id":null,  
	"title":"Doubt",  
	"post":"API Doubt",  
	"creationDate":"2020-04-08T00:02:47.8391678"  
	}  
]
```
## <a name="jpa"></a>Implementing JPA and H2 Database

As JPA and M2, have already been inserted into pom.xml, through **Spring Initializer**, we will only need to configure the H2 database, through **application.properties**.

Altough, follow below the pom.xml:
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
</dependency>
```
### Why use the H2 database?
The H2 database:
* Requires no installation;
* It is easy to use;
* It works in memory, that is, it means that when you restart it, the data is lost, however, if there was a .sql file, Spring is able to read and always add the data within the tables;

### Configurating the application.properties

```
#DataSource
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:forum
spring.datasource.username=sa
spring.datasource.password=

#JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true

#H2
# console.enabled allow us to have a h2 console
# console.path is how we're going to access the h2 interface
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```