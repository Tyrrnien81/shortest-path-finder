runApp: App.class
        java --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar App

App.class: App.java
        javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar App.java

runTests: FrontendDeveloperTests.class
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests
	
FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java

runBDTests:
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

clean:
	rm -rf *.class
