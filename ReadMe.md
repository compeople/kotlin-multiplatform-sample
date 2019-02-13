#Kotlin mulitplatform sample - swn 

This is an example project to test the possibilities of the Kotlin multiplatform technology. 
It takes a name, birthday and gender and calculate a random monthly insurance cost.
It targets android, iOS and web. 

Demo Web  (written with kotlin/JS and kotlin-react)
<br /> ![webDemo](art/webAppGifLow.gif) <br />

Demo android (written completely in kotlin) 
<br /> ![androidDemo](art/androidAppGif.gif) <br />

Demo iOS (written in kotlin/native and swift)
 <br /> ![iOSDemo](art/iOSAppGif.gif) <br />

##Getting started

If you don't have an android sdk, you can build the application without it, using: `./gradlew build -PskipAndroid`.

###Building the Code

+ make sure you have an Android SDK installed
+ Open the project in IntelliJ IDEA
+ create and add a file `local.properties` in the root directory of the project:
````sbtshell
sdk.dir="your local path to Android Sdk"
````
+ look under TODOs and change the url for the server call in `kotlinmulitplatform/commonClient/src/commonMain/kotlin/de.compeople.swn/tarifService/TarifClient`
+ Run `./gradlew build`

###Build only the server

run : `:server:build -PskipAndroid`

###Running the Android app

+ start the server
+ Create a run configuration of type "Android App"
+ Select module "app" in the run configuration settings
+ run the configuration

###Running the iOS-App

+ start the server
+ Open the XCode project under `native`
+ Run it as normal

###Running the tests
First you have to adjust the url for the server call: 
look under TODOs and change the url for the server call in 
`kotlinmulitplatform/commonClient/src/commonTest/kotlin/test.de.compeople.swn/tarifService/TarifClientTest`
                                                      
There a Tests in the common and the commonClient module. To run the Test use: 
+ `kotlinmultiplatform:common:check`
+ `kotlinmultiplatform:commonClient:check`

At the moment the tests for js are disabled, because mockk for JS is not ready yet for coroutines.

##Architecture
A simple block architecture for a quickly overview:
<br /> ![blockarchitecture](art/BlockArchitecture.png) <br />

##Built With
+ [Gradle](https://gradle.org/) - Dependency Management
+ [Ktor](https://ktor.io/)
+ [kotlin-Wrappers](https://github.com/JetBrains/kotlin-wrappers) - kotlin-react, kotlin-react-com for the web
+ [mockK](https://mockk.io/) - mocking library 

##License
This project is licensed under the MIT License.
