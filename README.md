# ClassCreator
Reads in text file that outlines the classes that will be used in a Java project. The outline will include the classes, fields in each class, and the methods to be included in each class.

Getter and Setter methods will automatically be set up for each field that is specified in the class. A toString method will also be provided automatically.
The methods that are specified will have the method stub and an empty body.

The ExampleOutline.txt is provided as an example to base your text file off of, you can run the ClassCreator using this ExampleOutline.txt to see how the program works. The directory that the text file is in is the directory that the .java files will be written to. 
The Outline.txt file in the src folder also provides an example, with descriptions as well. This file cannot be used by the ClassCreator, an error will occur.

When using the Driver version of the project, the name of the text file should be provided as a command line argument.

When using the Server/Client version of the project, you will not supply any command line arguments, you will be prompted for the text file name during runtime.
