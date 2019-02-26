Program for Part 1
File Name: InvertedIndex.java

Program for Part 2
File Name: QueryEvaluation.java

Program for Part 3
File Name: PostingList.java

Program for Part 4
File Name: QueryResults.java


Requirements:
#The documents file
The path of the documents.txt file needs to be changed in the main function of the program.
In my computer the file was placed in a folder called SE Assignment 1 on my desktop.
	"C:\\Users\\ADMIN\\Desktop\\SE-Assignment 1\\documents.txt" 
The path of the file needs to be updated accordingly.
This is the line in the main function where we need to change the path according to our system:
	input = new Scanner(new File("C:\\Users\\ADMIN\\Desktop\\SE-Assignment 1\\documents.txt"));

#The Krovetz Stemmer 3.4
The jar file of Krovetz Stemmer needs to be added to the build path.
The link to download the jar file is this:
https://sourceforge.net/projects/lemur/files/lemur/KrovetzStemmer-3.4/

#Compile and Executing
All these files need to be added to Eclipse IDE.
Steps to install Eclipse for windows:
Click on the download button of the following link.
https://www.eclipse.org/downloads/download.php?file=/oomph/epp/photon/R/eclipse-inst-win64.exe
Once Eclipse is installed we can follow the further steps.

Steps if Eclipse already exists or completed installation
Step 1: We have to make a Java project.
Step 2: Then add all these files in the default package
Step 3: By right clicking on our project in the package explorer, we can see the option for "Build Path"
Step 4: Further expanding the drop box, click on "configure build path"
Step 5: From the pop up window click on libraries
Step 6: Click on "add external JAR's"
Step 7: Browse and locate the downloaded kstem-3.4.jar file
Step 8: Click on run as Java application


Click on the program name under 'package explorer' and then click on run as application for executing that code.