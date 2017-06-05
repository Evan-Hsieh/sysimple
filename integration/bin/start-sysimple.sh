#!/bin/bash
bin_dir=$(cd `dirname $0`; pwd)
sysimple_home=${bin_dir}/..
java -jar  -DSYSIMPLE_HOME=${sysimple_home} -Dlog4j.configuration=${sysimple_home}/conf/sysimple-log4j.properties ${sysimple_home}/server/webapp/web.war ${sysimple_home}/server/webapp/web.war
