# This makefile is supposed to work under Linux and will
# most likely break under Windows or Mac systems
#
# Since I am new to java, this is probably not the ideal
# way to compile/run a java project, as I get more knowledge
# this file will be updated
#
# Pre-requisite:
# - JDK (tested with version 22.0.2)
# - JRE (tested with version 22.0.2)

CLASSNAME = App
JAVA_DEBUG_FLAGS = -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005

default: run

run:
	@make compile > /dev/null || { echo "Compilation failed!"; exit 1; }
	@java -cp bin ${CLASSNAME} || { echo "Failed to run the application!"; exit 1; }
	@echo -e "\n<Press any key to continue>"
	@read
	@clear

debug:
	@make compile DEBUG_FLAGS="-g" > /dev/null || { echo "Compilation failed!"; exit 1; }
	@java -cp bin ${JAVA_DEBUG_FLAGS} ${CLASSNAME} || { echo "Failed to run the application!"; exit 1; }
	@echo -e "\n<Press any key to continue>"
	@read
	@clear

compile:
	@find -name "*.java" > sources.txt
	@javac $(DEBUG_FLAGS) -d bin @sources.txt
	@rm sources.txt

zip:
	@rm -f abgabe.zip
	@zip -r abgabe.zip . -x "./.vscode/*" > /dev/null
	@echo "  Zip file created"