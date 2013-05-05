#!/bin/bash
#
# This script will setup the project to be installed correctly
#
# After this script is performed go to:
# http://linux.cis.drexel.edu:8080/~username
# to run the code

# get the user directory name
CURDIR=`basename $PWD`
cd ..
USERDIR=`basename $PWD`
cd ${CURDIR}

# make sure the right number of arguments have been entered
if [ "$#" -ne 2 ]; then
	echo -e "\nUsage: $0 USERNAME PASSWORD\n"
	exit 1
fi

# modify the configuration file to contain the username and password
sed -i "s|<USER>.*</USER>|<USER>$1</USER>|g" Config.xml
sed -i "s|<PASS>.*</PASS>|<PASS>$2</PASS>|g" Config.xml

# compile the java source code
javac -d ../public_html/WEB-INF/classes *.java

# copy the config files to the proper location
rm -f ../public_html/WEB-INF/classes/Config*
cp Config.xml ../public_html/WEB-INF/classes
cp Config.xsd ../public_html/WEB-INF/classes

# move the jsp files and movie folder to the public_html folder
rm -rf ../public_html/movies
cp -r movies ../public_html

rm -f ../public_html/AddMovie.jsp
rm -f ../public_html/FirstQuery.jsp
rm -f ../public_html/SecondQuery.jsp

cp AddMovie.jsp ../public_html
cp FirstQuery.jsp ../public_html
cp SecondQuery.jsp ../public_html

# Let the user know the system is ready to go
echo -e "\nYou can now go to: http://linux.cis.drexel.edu:8080/~$USERDIR/ to see the project work\n"


