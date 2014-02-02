1.Cobertura
"Cobertura" is the Spanish and Portuguese word for "coverage." http://cobertura.sourceforge.net/faq.html

Code coverage analysis works in three steps. 
1.First, it modifies (or “instruments”) your application classes, to make them keep a tally of the number of times each line of code has been executed.† They store all this data in a special data file (Cobertura uses a file called cobertura.ser).
2.When the application code has been instrumented, you run your tests against this instrumented code. At the end of the tests, Cobertura will have generated a data file containing the number of times each line of code was executed during the tests.
3.Once this data file has been generated, Cobertura can use this data to generate a report in a more usable format, such as XML or HTML.

code coverage will only be recorded for tests executed during the test life cycle phase, and not for tests executed during the integration-test phase.

<cobertura.plugin.version>2.5.2</cobertura.plugin.version>

1.1.[Maven]Basic Configuration
<project>
...
	<build>
		<plugins>
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>cobertura-maven-plugin</artifactId>
				<version>${cobertura.plugin.version}</version>
				<configuration>
					<formats>
						<format>html</format>
						<format>xml</format>
					</formats>
				</configuration>
			</plugin>
		</plugins>
	...
</project>
mvn cobertura:cobertura
The code coverage data will be generated in the target/site/cobertura directory, in a file called coverage.xml.

1.2.[Maven]Profile
<profiles>
	<profile>
		<id>metrics</id>
		<build>
			<plugins>
				<plugin>
					<groupId>org.codehaus.mojo</groupId>
					<artifactId>cobertura-maven-plugin</artifactId>
					<version>${cobertura.plugin.version}</version>
					<configuration>
						<formats>
							<format>html</format>
							<format>xml</format>
						</formats>
					</configuration>
				</plugin>
			</plugins>
		</build>
	</profile>
</profiles>
In this case, you would invoke the Cobertura plugin using the metrics profile to generate the code coverage data:
$ mvn cobertura:cobertura -Pmetrics

1.3.[Maven]Reporting
<reporting>
	<plugins>
		<plugin>
			<groupId>org.codehaus.mojo</groupId>
			<artifactId>cobertura-maven-plugin</artifactId>
			<version>${cobertura.plugin.version}</version>
		</plugin>
	</plugins>
</reporting>
$ mvn site

1.4.[Jenkins]Install Plugin
http://wiki.jenkins-ci.org/display/JENKINS/Cobertura+Plugin

1.5.[Jenkins]Configure in job
Build
	Goals and options
		clean install cobertura:cobertura -Pair-cobertura

2.Checkstyle
http://checkstyle.sourceforge.net/
https://wiki.jenkins-ci.org/display/JENKINS/Checkstyle+Plugin
For a Maven 3 project, you need to add the plugin to the <reportPlugins> element of the <configuration> section of the maven-site-plugin:
<sonar.url>http://192.168.0.159:9000</sonar.url>
<checkstyle.plugin.version>2.9.1</checkstyle.plugin.version>
<site.plugin.version>3.2</site.plugin.version>
<build>
...
<plugins>
...
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-site-plugin</artifactId>
	<version>${site.plugin.version}</version>
	<configuration>
		<reportPlugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-checkstyle-plugin</artifactId>
				<version>${checkstyle.plugin.version}</version>
				<configuration>
					<configLocation>
						${sonar.url}/rules_configuration/export/java/My_Rules/checkstyle.xml
					</configLocation>
				</configuration>
			</plugin>
		</reportPlugins>
	</configuration>
</plugin>
mvn checkstyle:checkstyle or mvn site

3.PMD/CPD
http://pmd.sourceforge.net/
https://wiki.jenkins-ci.org/display/JENKINS/PMD+Plugin
PMD is another popular static analysis tool. It focuses on potential coding problems such as unused or suboptimal code, code size and complexity, and good coding practices.
PMD also comes with CPD, a robust open source detector of duplicated and near-duplicated code.

If you are using Maven 3, you would place the plugin definition in the <maven-siteplugin> configuration section. This example also shows how to use a ruleset in another dependency (in this case the pmd-rules.jar file):
<pmd.plugin.version>2.7.1</pmd.plugin.version>
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-site-plugin</artifactId>
	<version>${site.plugin.version}</version>
	<configuration>
		<reportPlugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-pmd-plugin</artifactId>
				<version>${pmd.plugin.version}</version>
				<configuration>
<!-- PMD options -->
					<targetJdk>1.6</targetJdk>
					<aggregate>true</aggregate>
					<format>xml</format>
					<rulesets>
						<ruleset>/pmd-rules.xml</ruleset>
					</rulesets>
<!-- CPD options -->
					<minimumTokens>50</minimumTokens>
					<ignoreIdentifiers>true</ignoreIdentifiers>
				</configuration>
			</plugin>
		</reportPlugins>
	</configuration>

	<dependencies>
		<dependency>
			<groupId>this.is.a.demo.jar</groupId>
			<artifactId>pmd-rules</artifactId>
			<version>1.0.1</version>
		</dependency>
	</dependencies>
</plugin>
Now, you can run either mvn site or mvn pmd:pmd pmd:cpd to generate the PMD and CPD reports.

4.FindBugs
https://wiki.jenkins-ci.org/display/JENKINS/FindBugs+Plugin
FindBugs is a powerful code quality analysis tool that checks your application byte code for potential bugs, performance problems, or poor coding habits. FindBugs is the result of research carried out at the University of Maryland lead by Bill Pugh, that studies byte code patterns coming from bugs in large real-world projects, such as the JDKs, Eclipse, and source code from Google applications. FindBugs can detect some fairly significant issues such as null pointer exceptions, infinite loops, and unintentionally accessing the internal state of an object. Unlike many other static analysis tools, FindBugs tends to find a smaller number of issues, but of those issues, a larger proportion will be important.
<findbugs.version>2.5.2</findbugs.version>
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-site-plugin</artifactId>
	<version>${site.plugin.version}</version>
	<configuration>
		<reportPlugins>
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>findbugs-maven-plugin</artifactId>
				<version>${findbugs.version}</version>
				<configuration>
					<effort>Max</effort>
					<xmlOutput>true</xmlOutput>
				</configuration>
			</plugin>
		</reportPlugins>
	</configuration>
</plugin>
In both cases, you can generate the XML reports by running mvn site or mvn findbugs:findbugs.

5.Others
Clover
https://wiki.jenkins-ci.org/display/JENKINS/Clover+Plugin
JavaNCSS
JDepend

6.Integrating with Sonar
Sonar is a tool that centralizes a range of code quality metrics into a single website. It uses several Maven plugins (Checkstyle, PMD, FindBugs, Cobertura or Clover, and others) to analyse Maven projects and generate a comprehensive set of code
quality metrics reports.