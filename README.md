

<h1>Group 19 - LaserHogs</h1>
<h3>Software Engineering CSCE 3513: Jim Strother</h3>
<h3>Members:<br>Conner Simpson, Eric Iglesias, Juan Marin, Julio Morales, Michael Jones</h3>
---------------------------------------------------------------------------------------------------
<p>
<strong>Project Goal:</strong><br>To design and create a usable interface for the original Laser Tag system.

<strong>Tools and programs used:</strong> 
<br>Operating system: <strong>Microsoft Windows 10 or 11</strong>
<br>Programming Language: <strong>Java using JDK 19</strong>
<br>Prefered Programming IDE: <strong>JetBrains IntelliJ IDEA</strong>
<br>GUI tools and libraries: <strong>Gluon Sceen Builder </strong>and<strong> JavaFX</strong>
<br>Database hosting: <strong>Supabase using PostgreSQL</strong>

<strong>Testing Instructions:</strong><br>
Begin with having the current version of Java installed on your computer. <br><br>
Using IntelliJ as an example IDE, navigate to the project folder (example path, C:\Users\Michael Jones\OneDrive - University of Arkansas\Software Engineering\LaserHogs) and open. <br><br>
Once loaded, navigate to LaserTag.java (LaserHogs/src/main/java/com.example.lasertagproject/LaserTag.java) and run using the green play button in the top right corner of the IDE. <br><br>

The program will enter the splash screen. <br><br>

Now you are able to enter player ID's and Names, pressing F5 will start game countdown timer of 30 seconds and then switch to the play action screen with a game run timer of 6 minutes. The program connects through a UDP client to a UDP server automatically, the server and the client run as a class within the same program, so there is no need to run additional program. <br><br>

This program uses SupaBase to calculate team scores and store information, please delete players from tables "green_table" and "red_table" after each test, otherwise the result will not be the expected. Table "player" does not need to be cleared after each test. <br><br>
  
For the program to connect correctly to the database server (SupaBase), it needs a driver to be added to the program path/structure. The driver could be found in the following link: https://jdbc.postgresql.org/ <br><br> 

In the play screen, the top scorer name is updated after the round is over. Name will match the player with the highest score regardless of the team. Same condition applies for the total scores of each team. <br><br> 
<p>

