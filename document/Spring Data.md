## The basic camps they fall into are ##
1. Key/value
	A familiar data model, much like a hashtable.
1. Column family
	An extended key/value data model in which the value data type can also be a sequence of key/value pairs.
1. Document
	Collections that contain semistructured data, such as XML or JSON.
1. Graph
	Based on graph theory. The data model has nodes and edges, each of which may have properties.
## source code ##
`git clone https://github.com/SpringSource/spring-data-book`
`spring-data-book>mvn install -Dmaven.test.skip=true`

#JPA Repositories
- Hibernate http://www.hibernate.org/
- EclipseLink http://www.eclipse.org/eclipselink/
- OpenJpa http://openjpa.apache.org/

to configure the general JPA infrastructure (i.e., a DataSource connecting to a database as well as a
JPA EntityManagerFactory).

Spring JavaConfig configuration class

    @Configuration
	@ComponentScan
	@EnableTransactionManagement
	class ApplicationConfig {
	@Bean
	public DataSource dataSource() {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	@Bean
	public PlatformTransactionManager transactionManager() {

1. @Configuration annotation declares the class as a Spring JavaConfig configuration class. 
1. @ComponentScan instructs Spring to scan the package of the ApplicationConfig class and all of its subpackages for Spring components (classes annotated with @Service, @Repository, etc.).
1. @EnableTransactionManagement activates Spring-managed transactions at methods annotated with @Transactional.
1. The methods annotated with @Bean now declare the following infrastructure components:dataSource() sets up an embedded data source using Spring’s embedded database support. This allows you to easily set up various in-memory databases for testing purposes with almost no configuration effort. We choose HSQL here (other options are H2 and Derby). 
1. On top of that, we configure an EntityManagerFactory. We use a new Spring 3.1 feature that allows us to completely abstain from creating a persistence.xml file file to declare the entity classes. Instead, we use Spring’s classpath scanning
feature through the packagesToScan property of the **LocalContainerEntityManagerFactoryBean**. This will trigger Spring to scan for classes annotated with @Entity and @MappedSuperclass and automatically add those to the JPA PersistenceUnit.


	@RunWith(SpringJunit4ClassRunner.class)
	@ContextConfiguration(classes = ApplicationConfig.class)
	class CustomerRepositoryIntegrationTests {
	@Autowired
	CustomerRepository customerRepository;
	@Test
	public void savesAndFindsCustomerByEmailAddress {

