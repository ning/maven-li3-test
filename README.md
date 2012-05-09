Maven Li3 Test Plugin
=============

This plugin allows you to run li3 php tests from maven and fails under conditions that it should fail (exceptions and test failures and the like).

Usage
-------
Insert the following into your pom.xml:
```
      <plugin>
	<groupId>com.ning.maven.plugins</groupId>
	<artifactId>li3-test</artifactId>
	<version>0.0.9</version>    
	<executions>
	  <execution>
	    <id>run-lithium-tests</id>
	    <phase>test</phase>
	    <goals>
	      <goal>li3-test</goal>
	    </goals>
	    <configuration>
	      <li3ScriptPath>${basedir}/path/to/lithium/lithium/console/li3</li3ScriptPath>
	      <li3TestPath>${basedir}/path/to/app/tests</li3TestPath>
	    </configuration>
	  </execution>
	</executions>
      </plugin>
```

Then you can run tests with maven!

```
mvn test
```
