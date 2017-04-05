@echo off
set bin_dir=%~dp0
set sysimple_home=%bin_dir%/..
java -jar  -DSYSIMPLE_HOME=%sysimple_home% -Dlog4j.configuration=file:%sysimple_home%/conf/sysimple-log4j.properties %sysimple_home%/server/webapp/web.war %sysimple_home%/server/webapp/web.war