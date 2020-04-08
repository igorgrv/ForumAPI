# Forum -  "API REST" from scratch
## About the Project

The **purpose** of this project is to create a **Forum**, where we can post, remove, list  our doubts!

### Technologies/Database used
-   Spring Boot;
-   API Rest;
-   BeanValidation;
-   JPA;
-  H2 Database;
-  Spring Initializer/DevTools

## Summary
1. [Starting the project](#starting)
2. [Implementing JPA and H2 Database](#jpa)
3. [MVC](#mvc)
	* [Models](#models)
	* [Repository](#repository)
4. [Creating the first API Endpoint](#first)
	* [Simple example:](#simple)
	* [Simplifying the example:](#simplifying)
	* [Adding a filter:](#filter)
5. [Models](#models)
6. [Models](#models)
7. [Models](#models)

## <a name="starting"></a>Starting the project
1. Create the artifact: **forum**;
2. GroupId: **com.forum**;
3. Add the dependencies: **Web, JPA, MySQL Connector and DevTools**;
4. Unzip the file and import as "maven project";

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
* It works in memory, that is, it means that when you restart it, the data is lost, however, if **there was** a **data.sql** file into the **src/main/resources**, Spring is able to read and always add the data within the tables;
 
Download the [data.sql](https://github.com/igorgrv/ForumAPI/blob/master/Forum/src/main/resources/data.sql)
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
* Open the console by: `http://localhost:8080/h2-console`

<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/m2.PNG?raw=true" width=370 height=300>

<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/m22.PNG?raw=true" width=550 height=300>

## <a name="mvc"></a>MVC
### <a name="models"></a>Models

* [Download the Models](https://github.com/igorgrv/ForumAPI/blob/master/Model.rar)  and import into the project!

PayAttention! Create the package model inside the package.forum (where the main class is)

**Understanding the models with UML:**
<img src="https://github.com/igorgrv/ForumAPI/blob/master/UML/simplyUml.PNG?raw=true" width=600 height=500>

### <a name="repository"></a>Repository
* Let's create the Repositories, as: TopicRepository, CourseRepository
```java
public interface TopicRepository extends JpaRepository<Topic, Long>{
}

public interface CourseRepository extends JpaRepository<Course, Long>{
}
```

## <a name="first"></a>Creating the first API Endpoint
By default, Spring uses the Jackson library, which transforms a Java List into a .json format. <br>
In short, Spring takes the List -> passes it to Jackson -> Jackson transforms it to .json -> Spring returns it to the browser.

### <a name="simple"></a>Simple example:
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
	* To format the .json as below, use a  [jsonFormatter.com](jsonformatter.curiousconcept.com/)
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
### <a name="simplifying"></a>Simplifying the example:
**_@ResponseBody_**
So that we don't have to keep using `@ResponseBody` every time, Spring has an annotation, called `@RestController`, which will tell Spring that all of the controller's methods will not return a view/page!

**_DTOClass (DataTransferObject)_**
DTOClass, is a class that will **only has the necessary attributes**, that is, there is greater flexibility in choosing what will be in the API. <br> _In the last example, we passed all the attributes of the Topic class, which will have all the attributes of more classes, which is bad!_

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

		@RequestMapping("/topic")
	public List<TopicDTO> list(){
//		Topic topic = new Topic("Doubt", "API Doubt", new Course("Java", "API"));
//		return TopicDTO.toTopic(Arrays.asList(topic, topic, topic));
		return TopicDTO.toTopic(topicRepository.findAll());
	}
	
}
```
Spring will return the .json below:
```json
[
   {
      "id":1,
      "title":"Doubt",
      "post":"Error when create the project",
      "creationDate":"2019-05-05T18:00:00"
   },
   {
      "id":2,
      "title":"Doubt 2",
      "post":"Project do not compile",
      "creationDate":"2019-05-05T19:00:00"
   },
   {
      "id":3,
      "title":"Doubt 3",
      "post":"HTML tag",
      "creationDate":"2019-05-05T20:00:00"
   }
]
```
### <a name="filter"></a>Adding a filter to the example:

And if we wanted to find a specfic topic based on the name of the course?<br> Like: `localhost:8080/topic?courseName=Spring+Boot` 

1. We need to change the controller method, requiring a course name.
	* However, **if** no course names are given, we must return all topics!
2.  Create special filter on the TopicRepository, finding the Course by the name. <br> Like: `topicRepository.findByCourseName(courseName)` 
3.  Search by `localhost:8080/topic?courseName=Spring+Boot` 
```java
@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;

	@RequestMapping("/topic")
	public List<TopicDTO> list(String courseName){
		System.out.println(courseName);
		if (courseName == null) {
			return TopicDTO.toTopic(topicRepository.findAll());
		} else {
			return TopicDTO.toTopic(topicRepository.findByCourseName(courseName));
		}
	}
}

//-------------------------------------------------------------------
public interface TopicRepository extends JpaRepository<Topic, Long>{

	List<Topic> findByCourseName(String courseName);
	//Or
		// List<Topic> findByCourse_Name(String courseName);
	//Or
		//@Query("SELECT t FROM Topic t WHERE t.course.name = :courseName")
		// List<Topic> getByTheCourseName(@Param("courseName") String courseName);

}
```