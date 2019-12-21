
Technology stack used:

1)Java 8
2)Spring boot
3)REST api
4)JSON
5)JUnit
6)Tomcat
7)MAVEN

REST api end point --> http://localhost:8080/plangenerator/generate-planTomcat is embedded and default port is 8080 
context path is added to application : /plangenerator

json payload --> {
"loanAmount": "5000",
"nominalRate": "5.0",
"duration": 24,
"startDate": "2018-01-01T00:00:01Z"
}

How to run application ??
In eclipse:
1)Import maven project and build it
2)Run maven install
3)Run com.pp.plangenerator.boot.PlanGeneratorApplication class as application (since it is spring boot)4)Enter endpoint with payload with POST method

Using command(window/linux) : 
1)Run maven command to build , install packages and download libraries for loan-plan-generator project 
2)Run com.pp.plangenerator.boot.PlanGeneratorApplication class
3)Enter endpoint with payload 

Improvements :  We could also do some improvements/refactoring for all functions to be in on class and could also add logging.
Due to time constraint I haven't added logging.
