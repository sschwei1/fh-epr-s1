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

run:
	@find -name "*.java" > sources.txt
	@javac -d bin @sources.txt
	@rm sources.txt
	@java -cp bin App
	@echo -e "\n<Press any key to continue>"
	@read
	@clear
