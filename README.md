# Forum -  "API REST" from scratch

<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/project.png?raw=true" width=650 height=400>

## About the Project

The **purpose** of this project is to create a **Forum**, where we can post, remove, list  our doubts, via REST, using Spring Security and JJWT to authenticate the users;

### Technologies/Database used
-   Spring Boot;
-   API Rest;
- JJWT - Cache;
- Spring Security;
- Swagger + SpringFox;
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
5. [CREATE/POST](#post)
	* [TopicForm/CourseRepository](#topicform)
	* [Controller](#controllertopic)
	* [POSTMAN - Testing the Post](#postman)
6. [BeanValidation](#postman)
	* [ControllerAdvice - ValidationHandler](#advice)
	* [POSTMAN - Testing the BeanValidation](#testingbean)
7. [PUT/DETAIL](#detail)
8. [UPDATE](#update)
	* [POSTMAN - Testing the Update](#updatetest)
9. [DELETE](#remove)
	* [POSTMAN - Testing the Update](#deletetest )
10. [Handling 404 error](#404)
	* [New Controller](#404)
11. [Pagination/PageAble](#pagination)
	* [POSTMAN - Testing the Pagination](#paginationtest)
	* [@PageableDefault](#pageabledefault)
12. [Working with Cache](#cache)
	* [POSTMAN - Testing the Cache memory](#cachetest)
13. [Spring Security - Session](#security)
14. [Java Json Web Token - Stateless](#jjwt)
	* [Returning the token as a response](#returningtoken)
	* [Validating Token via Spring Security](#tokenspring)
	* [Token Complete](#tokencomplete)
15. [Swagger + SpringFox](#swagger)

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
spring.jpa.properties.hibernate.format_sql = true
spring.jpa.properties.hibernate.show_sql = true

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

### <a name="postman"></a> Testing the Post

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


### <a name="testingbean"></a>Testing the BeanValidation
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
Look the answer in Postman:<br>	
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/detail.png?raw=true" width=450 height=400>

## <a name="update"></a>Updating the topic
It is similar to the `save`method, we should use`ResponseEntity`, but instead of using `@PostMapping`, we will use `@PutMapping`.<br>

_Note: **@PutMapping** is used to update various attributes of the class.
**@PetMapping** is used to use one or two attributes of the class.
In general, the "PutMapping" method is used._

* As we want to update a topic, we need to receive `{id}`. _The same way as used in the `save`method._
* It is necessary to validate, _also the same way we validate in save_, but we will not use `TopicForm`, we will have a new Form for the update, called `UpdateTopicForm`.
	* The `UpdateTopicForm` class, will update only the **title and post** attributes;
	*   It will be necessary to create the `toTopic` method that will return a complete topic and where we will set the new information;
	* The `toTopic` method will receive the id and also the `topicRepository` so that through the `topicRepository.getOne (id)` method it is possible to return a complete topic.

```java
//TopicController
@PutMapping("/{id}")
@Transactional
public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicForm form){
	Topic topic = form.toTopic(id, topicRepository);
	return ResponseEntity.ok(new TopicDTO(topic));
}

//-------------------------------------------------------------
public class UpdateTopicForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	
	@NotNull @NotEmpty @Length(min = 10)
	private String post;
	
	public Topic toTopic(Long id, TopicRepository topicRepository) {
		Topic topic = topicRepository.getOne(id);
		
		topic.setTitle(title);
		topic.setPost(post);
		return topic;
	}
	//Getters and Setters
}
```
### <a name="updatetest"></a>Testing the Update

```json
{
	"title":"Doubt Updated",
	"post":"Post Updated"
}
```

<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/update.png?raw=true" width=650 height=400>

## <a name="remove"></a>Removing topics
To remove a topic, it is very similar to the `save` method. In this case, as we will not return any attributes, we will change the return of `ResponseEntity`, leaving it as an option `<?>`.

```java
//TopicController
@DeleteMapping("{id}")
@Transactional
public ResponseEntity<?> delete(@PathVariable long id){
	topicRepository.deleteById(id);
	return ResponseEntity.ok().build();
}
```
### <a name="deletetest"></a>Testing the Delete
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/delete.png?raw=true" width=650 height=400>

## <a name="404"></a>Handling 404 error
 
When performing a query on a non-existent ID, by default a stackTrace is returned, however this is not a good practice. The ideal is that every time we use an ID, it is guaranteed that this ID exists and, otherwise, a 404 code is returned.

### <a name="newcontroller"></a>New Controller
To prevent the "id not found" error from occurring, we will use the `Optional` class, in all controller methods.

``` java
@GetMapping("/{id}")
public ResponseEntity<TopicDetailDTO> detail(@PathVariable Long id) {
	Optional<Topic> topic = topicRepository.findById(id);
	if(topic.isPresent()) {
		TopicDetailDTO details = new TopicDetailDTO(topic.get()); 
		return ResponseEntity.ok(details);
	}
	return ResponseEntity.notFound().build();		
}

@PutMapping("/{id}")
@Transactional
public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicForm form){
	Optional<Topic> optional = topicRepository.findById(id);
	if(optional.isPresent()) {
		Topic topic = form.toTopic(id, topicRepository);
		return ResponseEntity.ok(new TopicDTO(topic));
	}
	return ResponseEntity.notFound().build();
}

@DeleteMapping("{id}")
@Transactional
public ResponseEntity<?> delete(@PathVariable long id){
	Optional<Topic> optional = topicRepository.findById(id);
	if(optional.isPresent()) {
		topicRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	return ResponseEntity.notFound().build();
}
```
## <a name="pagination"></a>Pagination/PageAble
Spring has the Pageable class, which has attributes that allow us to:
* Set the page we want;
* Set the number of elements on a page;
* Sort by element;
* Select the type of sorting (ASC or DESC);

#### How to activate?
To activate Pageable, we need to inform Spring that we are going to use it. For this, we will change the "main" class, adding the annotation `@EnableSpringDataWebSupport`.
This way, Spring will allow us to pass the attributes through the URL.
```java
@SpringBootApplication
@EnableSpringDataWebSupport
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

}
```
#### How to use?
1. In the list method, we will add Pageable to the method;
	* Pay Attetion to the import!
2. When using pagination, the return is no longer a `List <>` but a `Page <>`. For this, we must change the method returns.
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//TopicController
@GetMapping
public Page<TopicDTO> list(@RequestParam(required = false) String courseName, Pageable pageable){
	if (courseName == null) {
		Page<Topic> topics = topicRepository.findAll(pageable);
		return TopicDTO.toTopic(topics);
	} else {
		return TopicDTO.toTopic(topicRepository.findByCourseName(courseName, pageable));
	}
}

//--------------------------------------------------------------
//TopicDTO
//Using the map method, for each topic, a "topicDTO" will be created
public static Page<TopicDTO> toTopic(Page<Topic> topics) {
	return topics.map(TopicDTO::new);
}

//--------------------------------------------------------------
//TopicRepositoru
public interface TopicRepository extends JpaRepository<Topic, Long>{
	Page<Topic> findByCourseName(String courseName, Pageable pageable);
}
```

### <a name="paginationtest"></a>Testing the Pagination
`http://localhost:8080/topic?page=0&size=10&sort=id,desc`<br>
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/pagination.png?raw=true" width=400 height=400>

#### <a name="pageabledefault"></a>PageableDefault
When we don't pass any paging parameters, what will happen? We can set the pagination by default with the annotation `@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10)`;

```java
@GetMapping
	public Page<TopicDTO> list(@RequestParam(required = false) String courseName,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable){
}
```

## <a name="cache"></a>Working with Cache
Cache is very useful when we want to **improve the performance of the application**, however **we have to be careful with the methods that we will put in "cache"**.<br>
**_The recommended methods to use "cache", are the methods that use tables that will suffer few data insertions, as a table of countries and states._**

#### How to use?
Add into the pom.xml the dependency below:
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```
To enable the cache, we must add the @EnableCaching annotation in the main method.
```java
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class ForumApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
}
```
To use cache memory, we need to add the `@Cacheable` annotation, which will warn Spring that the return should be saved in cache memory and we need to inform a "value" within the annotation, so that Spring knows which the cache search!

```java
@GetMapping
@Cacheable(value = "listOfTopics")
public Page<TopicDTO> list (){
}
```

### <a name="cachetest"></a>Testing the Cache memory
To test the cache memory, we will check if Spring will search the database, when calling the GET method a **second time**.

First Time - Console log:<br>
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/first.PNG?raw=true" width=400 height=400>
<br>
Second Time - Console log:<br>
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/second.PNG?raw=true" width=400 height=300>

### <a name="invalidating"></a>Invalidating the cache memory
We must pay attention to the methods: **post, delete and update.**
If we save the list in the cache memory and there is an insertion, the user will not "see" this update, as it was saved in the cache memory. 
* To invalidate / clear the cache memory, we use the `@CacheEvict` annotation, passing the "value" that would be the cache id.

```java
@PostMapping
@Transactional
@CacheEvict(value = "listOfTopics", allEntries = true)
public ResponseEntity<TopicDTO> save(){
}

@PutMapping("/{id}")
@Transactional
@CacheEvict(value = "listOfTopics", allEntries = true)
public ResponseEntity<TopicDTO> update(){
}

@DeleteMapping("{id}")
@Transactional
@CacheEvict(value = "listOfTopics", allEntries = true)
public ResponseEntity<?> delete(){
}
```

## <a name="security"></a>Spring Security - Sessions
#### Why to use?
The API as it stands, has no security, that is, anyone who knows the address of the EndPoints, can perform any operation.<br> To perform an access control, we will use **Spring Security!**
#### How to use?
Add into the pom.xml the dependency below:
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
To enable the Spring Security, we must create a Class Configuration.
1. Create the package inside the config package, called `config.security`;
2. Create the `SecurityConfiguration` class;
	* Inside this class, it will have all the Security Configuration;
3. To inform Spring that this is a security configuration class, we will use the `@EnableWebSecurity` annotation and also the `@Configuration` ;
4. Extend the class `WebSecurityConfigurerAdapter`;

```Java
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
}
```
#### How to allow the users?
Our logic will be to allow the `List` and `ListDetail` methods to be **public**, while the others (delete, update and save) will require authentication.<br><br>

In order to authenticate, it is necessary to use the User class, which must implement the UserDetails interface, which will also implement several methods.
```java
@Entity
public class User implements UserDetails{

	//GranteAuthority requires user profile types, as (Admin, Buyer, NormalUser)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	//As we don't have these attributes, we'll return true!
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
```
* After we implemented the UserDetails, the method `getAuthorities`, requires a List of user profile types, which we gonna have to create, implemeting the GrantedAuthority  Class
```java
@Entity
public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Override
	public String getAuthority() {
		return this.name;
	}
} 


//----------------------------------------------------------
//Complement - User class
@Entity
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	//fetchtype Eager, will load all user profiles when the User class is called;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}
}
```

Within the SecurityConfiguration class, there will be 3 `configure` methods essential for configuration.

* `configure (HttpSecurity http)`: configure who can access the URL;
	```java
	//We will allow the acess only for the GET method, otherwise, will be necessary the authentication sending the formLogin!
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers(HttpMethod.GET, "/topic").permitAll().
		antMatchers(HttpMethod.GET, "/topic/*").permitAll().
		anyRequest().authenticated().
		and().formLogin();
	}
	```
	* Try to load the page - `http://localhost:8080/` <br> <img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/login.png?raw=true" width=380 height=300>
* `configure (AuthenticationManagerBulder auth)`: configures user authentication;
	*	In order to authenticate users, it is necessary to create a Service, which will be called `userDetailsService`;
		1. Create the class `UserService`, inside the **_config.security_**, with the `@Service` Annotation;
		2. Implement the `UserService` and implement the requested method;
			* The method `loadUserByUserName` requires the login/email and and to locate the user, it is necessary to **create a repository**, which will search for the `User` based on the assigned **email**;
		3.  To **encrypt the password** that will be passed by the user, we will use the method `.passwordEncoder(new BCryptPasswordEncoder())`
			* We also need to change the password in our "data.sql", because now it's not longer "123456".
			* To get new password encrypted by BCrypt, try to use the code below:
			```java
			public static void main(String[] args) {
				System.out.println(new BCryptPasswordEncoder().encode("123456"));
			}
			```

	```java
	@Service
	public class UserService implements UserDetailsService {

		@Autowired
		private UserRepository userRepository;
		
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Optional<User> user = userRepository.findByEmail(username);
			if(user.isPresent()) {
				return user.get();
			}
			throw new UsernameNotFoundException("User not found");
		}
	}

	//----------------------------------------------------------------
	//UserRepository
	public interface UserRepository extends JpaRepository<User, Long>{
		Optional<User> findByEmail(String email);
	}

	//----------------------------------------------------------------
	//SecurityConfiguration
	@EnableWebSecurity
	@Configuration
	public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
		
		@Autowired
		private UserDetailsService userDetailsService;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
	}
	```
* `configure (WebSecurity web)`: configure permission for static resources (css, js, images);

## <a name="jjwt"></a>Java Json Web Token  (JJWT)  - Stateless
In the "Rest" model, **all communication has to be stateless**, that is, the server should not store authentication information in the session. To solve this problem, "Rest" will use the **_Json Web Token (JWT)_**

#### How to use?
Add into the pom.xml the dependency below:
```xml
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
</dependency>
```
As we will no longer use login via session, but via Token, we have to make changes to the `configure` method:
* We must remove the `and().FormLogin()` - because we won't use;
* Disable .csrf attacks - `csrf().Disable()`.
* Disable sessions - `sessionManagement().SessionCreationPolicy(SessionCreationPolicy.STATELESS)`

As we no longer have a "UserController" (previously created internally in Spring), we will have to create a controller that will be responsible for the authentication part, called LoginController;
The Controller will respond to requests ("/ auth") - _it's necessary to add this request into the `SecurityConfiguration`._

```java
//SecurityConfiguration
@Override
@Bean 
protected AuthenticationManager authenticationManager() throws Exception {
	// TODO Auto-generated method stub
	return super.authenticationManager();
}

@Override
protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().
	antMatchers(HttpMethod.GET, "/topic").permitAll().
	antMatchers(HttpMethod.GET, "/topic/*").permitAll().
	antMatchers(HttpMethod.POST, "/auth").permitAll().
	anyRequest().authenticated().
	and().csrf().disable().
	sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
}
```
When the user logs in, through the "front", the "body" with the login and password information will be sent via Post. To get that information, we must use a LoginForm.

```java
public class LoginForm {
 
	public String email, password;
	//Getters and Setters
}

//-----------------------------------------------
//LoginController
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@PostMapping
	public ResponseEntity<?> authenticat(@RequestBody @Valid LoginForm form){
		//Testing if the login/password are coming by the request		
		System.out.println(form.getEmail());
		System.out.println(form.getPassword());
		return ResponseEntity.ok().build();
	}
}
```
* To test, open the Postman  and send the request via POST `http://localhost:8080/auth`, using the .json below -  **_we must get a status 200_**
```json
{
	"email":"student@email.com.br",
	"password":"123456"
}
```
#### Working inside the LoginController
Some changes will be necessaries to generate the Token and to get the Login.
* It's necessary to inject the class  `AuthenticationManager`, with `@Autowired`;
	* Spring doesn't recognize this injection, so we have to add this class using the `SecurityConfiguration`, which has the method `AuthenticationManager`;
* The `AuthenticationManager` has a method, called `authenticate()` that must recive a `UserNamePasswordAuthenticationToken(login,password)` object;
	* It's necessary to create inside the `LoginForm` a method, that returns the login and password, to abstract from controller;
* The class responsible for the token, it will be the `TokenService`, which will have the method `tokenGenerator(authentication)`, that recives the authentication variable;
	* To generate the token, we have to use the JWTS library and add into the `application.properties` the code bellow:
		```
		# jwt
		forum.jwt.secret=rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\}x3?JR3.2zr~v)gYF^8\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\J?N,nvH.<2\.r~w]*e~vgak)X"v8H`MH/7"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/
		forum.jwt.expiration=86400000
		```
	* To use the codes above, we need to inform the Spring how to find it. To do that, we must use `@Value("${forum.jwt.secret}")`;
	```java
	@Service
	public class TokenService {

		@Value("${forum.jwt.expiration}")
		private String experation;
		
		@Value("${forum.jwt.secret}")
		private String secret;
		
		@SuppressWarnings("deprecation")
		public String tokenGenerator(Authentication authentication) {
			/**
			 * .setIssuer - inform, the API responsible for the token generation;
			 * .setSubject - inform the user resposible;
			 * .setIssuedAt - inform the Current date;
			 * .setExpiration - inform the time to expire the token - in our case, it will be 1 day;
			 * .signWith - it's the type of the algorith that will be generated, and our secret password;
			 * .compact - transform to String; 
			 */
			User user = (User) authentication.getPrincipal();
			return Jwts.builder()
					.setIssuer("Igor - ForumAPI")
					.setSubject(user.getId().toString())
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime() + experation))
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		}
	}

	//-------------------------------------------------------------
	//LoginController
	@RestController
	@RequestMapping("/auth")
	public class LoginController {
		
		@Autowired
		private AuthenticationManager authManager;
		
		@Autowired
		private TokenService tokenService;

		@PostMapping
		public ResponseEntity<?> authenticat(@RequestBody @Valid LoginForm form){
			UsernamePasswordAuthenticationToken login = form.toUser();
			//we need to handle the error in case the login does not exist.
			try {
				//When Spring calls this method, this method will call the Service that will call the Repository, 
				// that will validate the User and password;
				Authentication authentication = authManager.authenticate(login);
				
				//If the authentication worked, we must return the token, from the JJWT library
				// but the the token generation will be absctracted from controller, using the tokenService!
				String token = tokenService.tokenGenerator(authentication);
				System.out.println(token);
				
				return ResponseEntity.ok().build();
			} catch (AuthenticationException e) {
				return ResponseEntity.badRequest().build();
			}
			
		}
	}
	```
#### <a name="returningtoken"></a>Returning the token as a response
In order to return the token, we need to create a `TokenDTO`, which will return the token and the type of authentication (**_Bearer_**);
	* Change the `ResponseEntity<?>` sendint the TokenDTO;
	* Remove the `sysout`;
```java
public class TokenDTO {

	private String token;
	private String typeAuthentication;

	public TokenDTO(String token, String typeAuthentication) {
		this.token = token;
		this.typeAuthentication = typeAuthentication;
	}
	//Getters
}

//-------------------------------------------------
//LoginController
@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> authenticat(@RequestBody @Valid LoginForm form){
		UsernamePasswordAuthenticationToken login = form.toUser();
		
		//we need to handle the error in case the login does not exist.
		try {
			//When Spring calls this method, this method will call the Service that will call the Repository, 
			// that will validate the User and password;
			Authentication authentication = authManager.authenticate(login);
			
			//If the authentication worked, we must return the token, from the JJWT library
			// but the the token generation will be absctracted from controller, using the tokenService!
			String token = tokenService.tokenGenerator(authentication);
			
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
```
Send the Auth into the Postman, as POST. We may recive the token, as below:
```json
{
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJJZ29yIC0gRm9ydW1BUEkiLCJzdWIiOiIxIiwiaWF0IjoxNTg2NjcwNjY3LCJleHAiOjE1ODY3NTcwNjd9.DjHo6ynjQAjLUVT3QIwJ7f-_yiXLoqI5LmoiBOjfXn4",
	"typeAuthentication": "Bearer"
}
```

## <a name="validatingtoken"></a>Validating the Token
Token validation will occur after implementing "Authorization" in the header of our request.<br>
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/typeauthentication.png?raw=true" width=700 height=400>
OR
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/authentication2.png?raw=true" width=700 height=200>

### Understanding how authentication works
As this is a "Stateless" validation, the server will not store tokens, that is, every time the customer makes a request, **it will be necessary to check if the Token is in agreement**.<br>To validate each request, we need to implement a filter, which we will call `TokenAuthenticatorFilter`, which extends the `OncePerRequestFilter` class.
1. Create inside the package **_config.security_**, the classe `TokenAuthenticatorFilter` extending the `OncePerRequestFilter` class;
2. Add the abstract method `doFilterInternal`;
	* This method must use the `filterChain.doFilter`;
	* We need to get the token, so we will use the method `tokenValidate`;
	* We need to verify if the token it's correct, with the method `isValid`;

```java
public class TokenAuthenticatorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		boolean tokenIsValid = tokenService.isValid(token);
			
		filterChain.doFilter(request, response);
	}

	//this method will validate if the token was sent
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token.isEmpty() || token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}

//-----------------------------------------------------------------
//TokenService
public boolean isValid(String token) {
	try {
		Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
		return true;
	} catch (Exception e) {
		return false;
	}
}
```
* After creating the filter, for Spring find this filter it will be necessary to include another attribute within the `configure` method of the `SecurityConfiguration` class.
```java
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	//As the Token filter can't inject the tokenService, we have inject here
	@Autowired
	private TokenService tokenService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers(HttpMethod.GET, "/topic").permitAll().
		antMatchers(HttpMethod.GET, "/topic/*").permitAll().
		antMatchers(HttpMethod.POST, "/auth").permitAll().
		anyRequest().authenticated().
		and().csrf().disable().
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().addFilterBefore(new TokenAuthenticatorFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
	}
	
	//omitted methods
}
``` 
## <a name="tokenspring"></a>Validating Token via Spring Security

Once it has been verified that the token is valid, we need to inform SpringSecurity that the user is allowed to execute the methods.

1. Create the `authUser` method inside `TokenAuthenticatorFilter`, which will allow the user to SpringSecurity;
2. In the `authUser` method, we will use the class `SecurityContextHolder.getContext().SetAuthentication()` - this class requests an object of type **Authentication**, so we will create with the class `UsernamePasswordAuthenticationToken` that receives a (User, null password, user.getType ());
3. The `token` object has the User, because in the `TokenService` class we pass through the `.setSubject (user.getId().ToString())` method - to receive this User by the token we will create a method in the `TokenService`, called `getUserId (token)`;
4. With the Id it is possible to return the User, however it is necessary to use the UserRepository, which will have to be injected through the constructor, that is, it will be injected by the class `SecurityConfigutarion`;

#### <a name="tokencomplete"></a>Token Complete
``` java
public class TokenAuthenticatorFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UserRepository userRepository;
	
	public TokenAuthenticatorFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		

		String token = getToken(request);
		boolean tokenIsValid = tokenService.isValid(token);
		if (tokenIsValid) {
			authUser(token);
		}		
		filterChain.doFilter(request, response);
	}

	private void authUser(String token) {
		Long idUser = tokenService.getUserId(token);
		User user = userRepository.findById(idUser).get();
		UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	//this method will validate if the token was sent
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token.isEmpty() || token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
}

//---------------------------------------------------------------------
//TokenService
@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String experation;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String tokenGenerator(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		/**
		 * .setIssuer - inform, the API responsible for the token generation;
		 * .setSubject - inform the user resposible;
		 * .setIssuedAt - inform the Current date;
		 * .setExpiration - inform the time to expire the token - in our case, it will be 1 day;
		 * .signWith - it's the type of the algorith that will be generated, and our secret password;
		 * .compact - transform to String; 
		 */
		return Jwts.builder()
				.setIssuer("Igor - ForumAPI")
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + Long.parseLong(experation)))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//Will return the id
	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}

//---------------------------------------------------------------------
//TokenService
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers(HttpMethod.GET, "/topic").permitAll().
		antMatchers(HttpMethod.GET, "/topic/*").permitAll().
		antMatchers(HttpMethod.POST, "/auth").permitAll().
		anyRequest().authenticated().
		and().csrf().disable().
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		and().addFilterBefore(new TokenAuthenticatorFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
``` 
* Teste the methods DELETE, POST, PUT in the Postman;


## <a name="swagger"></a>Swagger + SpringFox - documenting the API

To make it easier for other developers / customers to consume the API, it is necessary that we create a document that shows how to make requests, methods and tests.

[Swagger](https://swagger.io/solutions/api-documentation/) is a tool that reads the source code and creates the document, but together with Swagger, we have [SpringFox](https://springfox.github.io/springfox/) that uses the swagger library to return a front with API data!

### How to use the SpringFox
Add into the pom.xml the dependency below:
```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>

<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
```
To enable the Swagger, we must add the `@EnableSwagger2` annotation in the main method.
```java
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
}
```
### Swagger Configuration
Swagger needs some data to be informed, so that it knows how to build the documentation.

1. Create the class `SwaggerConfiguration` inside the package **_config.swagger_**, with the annotation  `@Configuration`;
2. Create the `forumAPI` method, which will return an object of type `Docket`, with the annotation `@Bean`;
```java
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.forum.forum"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(User.class);
	}
}
```
* However, for the front/documentent be displayed, it will be necessary to enable authorizations in `SecurityCofiguration`!
	```java
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
	```

Enter into the URL: `http://localhost:8080/swagger-ui.html` <br>
<img src="https://github.com/igorgrv/ForumAPI/blob/master/readmeImage/swagger.PNG?raw=true" width=330 height=400>