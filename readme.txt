Flashcard player for Chinese Characters 1.0

1) Download flashcard4cn-bin-1.0.zip
2) Unzip
3) Look at doc/UserGuide.pdf for installation and user guide

How to run?
Use one the method to start Flashcard player
1) ant run
2) ant jar
   cd dist
   chmod u+x startup.sh
   ./startu.sh
3) In Netbeans IDE, at Projects tab, right click flashcard4cn, select Run
4) In Netbeans IDE, at Projects tab, right click flashcard4cn, select Debug

Package for release
1) Open release.xml
   change version.id property value to a desired one, e.g.
       <property name="version.id" value="1.1"/>
2) Execute the folloeing command
   ant -f release.xml
3) Zip
     flashcard4cn-bin-<version>/
     flashcard4cn-src-<version>/
