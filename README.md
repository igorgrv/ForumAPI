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
5. [POST using REST](#post)
	* [TopicForm/CourseRepository](#topicform)
	* [Controller](#controllertopic)
6. [POSTMAN- Testing the PostMapping](#postman)
7. [BeanValidation](#postman)
	8. [ControllerAdvice - ValidationHandler](#advice)
	9.  [Testing BeanValidation](#testingbean)
8. [Detailing  the topics](#detail)
9. [Models](#models)
10. [Models](#models)

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
## <a name="post"></a>Post using REST

To perform a POST, we will use the `@PostMapping` annotation that will implement the same URI that we use with GET, therefore, as both use the same URI, we will implement `@RequestMapping ("/topic")` in the controller. <br> This way Spring will understand that all methods will default to **/topic/something**.

```java
@RestController
@RequestMapping("/topic")
public class TopicController {
	
	@GetMapping
	public List<TopicDTO> list(String courseName){
	}
	
	@PostMapping
	public void save() {
	}
}
```

### <a name="topicform"></a>TopicForm/CourseRepository 
When using "GET", we assign  -> "DTO";<br> For "POST", we assign  -> "FORM";

#### What will this "FORM" have??  
The **TopicForm** class will receive data that is not filled in by default in the Topic class (the creation date, for example, is already filled in), that is, it will have:
* Title;  
* Post;  
* CourseName; (que ira retornar um Course)

#### What is the purpose of Form classes?
Like the DTO class, the Form class must return a complete object based on the data it receives.
1. Create the method, **toTopic**, which should return a Topic.
2. To return a Course, we will need a **CourseRepository**, where through the **courseName**, a course is returned.

```JAVA
public class TopicForm {

	private String title, post, courseName;
	
	public Topic toTopic(CourseRepository courseRepository) {
		Course course = courseRepository.findByName(this.courseName);
		Topic topic = new Topic(title, post, course);
		return topic;
	}
	//Getters and Setters
} 

public interface CourseRepository extends JpaRepository<Course, Long>{

	Course findByName(String courseName);
}

```

### <a name="controllertopic"></a> Controller 
The TopicForm class will come from the data sent via body, for that we will use `@ RequestBody` and for carrying out a transaction, we will add the annotation` @ Transactional`.

Example:
```java
@PostMapping
@Transactional
public void save(@RequestBody TopicForm form) {
	Topic topic = form.toTopic(courseRepository);
	topicRepository.save(topic);
}
```
A method with void return, returns the code 200 in case of success, which means that it was successfully sent to the server, but what interests us is to know if the request was created, for this we wait for the code 201.
1. We will change the save method, changing the void return to `ResponseEntity <TopicDTO>`. This return is responsible for returning code 201 with the DTO class information;
2. To return a **ResponseEntity**, it is necessary to use the **created** method that receives a URI, like: `return ResponseEntity.created(uri)`;  
3. To get the URI, we will use the **URI class** and we will ask Spring to inject the **UriComponentsBuilder class** in the method call, which has the **path** method responsible for indicating which path will be returned. In this case **("/topic/{id}")**, because we want to return the object, not the entire list.
4. To fill the {id} we will use the **buildAndExtend** method passing **topic.getId ()**. Finally, it will be necessary to convert to uri, with the **toUri ()** method.
5. With the URI completed, we will add to the return, the **body** method that will pass a **new topicDTO(topic)**.

Controller completed:
```java
@PostMapping
@Transactional
public ResponseEntity<TopicDTO> save(@RequestBody TopicForm form, UriComponentsBuilder uriBuilder) {
	Topic topic = form.toTopic(courseRepository);
	topicRepository.save(topic);
	
	URI uri = uriBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();
	
	return ResponseEntity.created(uri).body(new TopicDTO(topic));
}
```

## <a name="postman"></a> Postman - Testing the PostMapping

Because the request is of the type @Post, it's not possible to test directly through the URL, for this there is the Postman software - [Download Postman](https://www.postman.com/downloads/);

Look below, how to use the Postman:
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/postman.png?raw=true" width=850 height=400>

## <a name="postman"></a>BeanValidation!
So far, the POST method does not have any type of validation, that is, it is possible to forward the blank "title", for example. <br> In order for this not to happen, we need to make validations before saving a topic.
* With "BeanValidation" all validations are done through annotations, such as: @NotBlank | @Notnull | @Size

**Simple validation (without message)**
```java
public class TopicForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title, courseName;
	
	@NotNull @NotEmpty @Length(min = 10)
	private String post;
}

//TopicController
public ResponseEntity<TopicDTO> save(@RequestBody @Valid TopicForm form, UriComponentsBuilder uriBuilder) {
	//code omitted
}
```

### <a name="advice"></a>ControllerAdvice - ValidationErrorHandler
#### But how do we know what the error is, without any message?
When submitting an "unapproved" request, we want the **reason for the error** to be demonstrated. 
* Spring has a solution for this type of scenario, where every time that an exception occurs, **Spring will automatically call an Interceptor**, called "ControllerAdvice".

Explanation/Creation - ControllerAdvice:
1. Create the package **_forum.config.validation_**;
2. Create the class **_ValidationErrorHandler_** with the annotation `@RestControllerAdvice`;
3. To warn Spring about which exception it will run the method, we use the `@ExceptionHandler` annotation, passing the `MethodArgumentNotValidException` class, which is responsible for handling @Valid;
4. With the use of this method, **Spring understands that we are dealing with the validations** ourselves and then in case of error, it will "return" a 200 code (success) and not a 400 code (Bad request). To correct this we use the annotation `@ResponseStatus (code = BAD_REQUEST)`;
5. In return, we want a list that contains the type of exception and the field that had an error (title, post or courseName), so we add the return as `List<ErrorDTO>`;
6. We will implement a `List <FieldError>` which is responsible for catching field exceptions. As we want to display a list of exceptions by fields, we will create an empty `List <ErrorDTO>`. To insert each exception to our list, we will use forEach.
7. To return the message, we will use the class `MessageSource`, which should be injected by Spring through `@Autowired`. This class has the method `getMessage`, which will receive the exception and a `LocaleContextHolder`, responsible for the language.


```java
public class ErrorDTO {

	private String field;
	private String exception;

	public ErrorDTO(String field, String exception) {
		this.field = field;
		this.exception = exception;
	}
	//Getters and Setters
}
//-----------------------------------------------------------
@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDTO> handle(MethodArgumentNotValidException exception) {
		List<ErrorDTO> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			ErrorDTO error = new ErrorDTO(e.getField(), messageSource.getMessage(e, LocaleContextHolder.getLocale()));
			dto.add(error);
		});
		return dto;
	}
}
```
* Spring will automatically understand that every exception must pass through the class!


### <a name="testingbean"></a>Testing BeanValidation
Send the .json bellow, using the Postman and add into the Header > Key (Accept-Language) > Value (en-US)
```json
{
	"title":"",
	"post":"",
	"courseName":"Spring Boot"
}
```
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/bean.png?raw=true" width=450 height=400>

## <a name="detail"></a>Detailing Topics
#### How?
We will use `@GetMapping("/{id}")` and as it comes as part of the URL, we will use the `@PathVariable` annotation. <br>
_By default, if the parameter name is the same as the URL (both are "id"), there is no need to put anything in @PathVariable("something")._

As we want to detail the topic, we cannot use the **TopicDTO**, because **we want more information**. For this, we will create the **TopicDetailDTO** class!

```java
public class TopicDetailsDTO {

	private Long id;
	private String title, post;
	private LocalDateTime creationDate;
	private String userName;
	private TopicStatus status;
	private List<AnswerDTO> answers;
	
	public TopicDetailsDTO(Topic topic) {
		this.id = topic.getId();
		this.title = topic.getTitle();
		this.post = topic.getPost();
		this.creationDate = topic.getCreationDate();
		this.userName = topic.getUser().getName();
		this.status = topic.getStatus();
		
		this.answers = new ArrayList<>();
		this.answers.addAll(topic.getRespostas().stream().map(AnswerDTO::new).collect(Collectors.toList()));
	}
}
//--------------------------------------------------------
public class AnswerDTO {

	private Long id;
	private String post;
	private LocalDateTime creationDate;
	private String userName;
	
	public AnswerDTO(Answer answer) {
		this.id = answer.getId();
		this.post = answer.getPost();
		this.creationDate = answer.getCreationDate();
		this.userName = answer.getUser().getName();
	}
}
//--------------------------------------------------------
//TopicController
@GetMapping("/{id}")
public TopicDetailsDTO detail(@PathVariable Long id) {
	Topic topic = topicRepository.getOne(id);
	TopicDetailsDTO details = new TopicDetailsDTO(topic); 
	return details;
}
```