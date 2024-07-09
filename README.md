folder structure:
 -menifests (this file is responsible for  Permissions Declaration , Application Metadata etc. we don't need and we havent change it from beginning)
 
 -java (this folder is responsible for our application all the java codes and our entry point of app resides here)
 
 -cpp (this folder is responsible for our c++ side of app our app. it has code which will be called form java using JNI (Java Native Interface).CMakeLists.txt serves as a blueprint for CMake to   manage dependencies)
 
 -res ( here it resides the ui of app)

objective:
create an file using java and read it using c++

here is what we are actually doing in overview 

1. use java for checking upwork.txt file if it's already created or not. 
2. if it's not created then we will create and if it's already created then we will not create it.
3. we will use c++ only for reading file content and it won't do anything more.
4. once it reads the content of file it returns it to mainActivity.java so we can use it on android side.

now these files are very important here because most of the or almost all of our functionality takes places here

1. MainActivity.java
-  this file handles the main logic of the application.
-   Binds the UI elements defined in the XML layout file to the Java code and initializes views using onCreate()
-  saveDataToFile(String data). this function Writes user input to a file named upwork.txt
-  readFileFromCpp(String filePath). this function is really important because  it calls a native method to read file content from a specified file path using C++ (basically we are sending path of upwork.txt as argument to this function)
-  showToast(String message). this is responsible for showing toast messages

2.native-lib.cpp
- stringFromJNI(). it's basically a starting boilerplate code which i didn't changed it returns  string from the native layer.
- readFileFromCpp(const char *path).  it basically reads content from a file specified by the file path and returns it as a string to the Java layer.

3. AndroidManifest.xml
- it basically has our ui

here are the main components that really impact our app

our cpp files readFileFromCpp function takes one path of file and just returns the string which is actually a content of file to Java layer through JNI.
our java file is just getting that string from jni which interacts with c++ file. so we can access that in our java file 



finished.
