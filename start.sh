#!/bin/bash

#查找JAVA命令
findjava() {
	if [ -n "${JAVA_BINDIR}" ] && [ -x "${JAVA_BINDIR}/java" ]
	then
		JAVACMD="${JAVA_BINDIR}/java"
	elif [ -n "${JAVA_HOME}" ] && [ -x "${JAVA_HOME}/bin/java" ]
	then
		JAVACMD="${JAVA_HOME}/bin/java"
	else
		JAVACMD=$(which java)
		if [ -n "${JAVACMD}" ] && [ -x "${JAVACMD}" ]
		then
			echo "在\$PATH中找到java."
		elif [ -x /usr/bin/java ]
		then
			JAVACMD=/usr/bin/java
		fi
	fi

	if [ -n "${JAVACMD}" ] && [ -x "${JAVACMD}" ]
	then
		return 1
	else
		echo "找不到JAVA虚拟机，请定义相关环境变量，比如JAVACMD,JAVA_BINDIR,JAVA_HOME,PATH"
		return 0
	fi
}

findjava
if [ $? -ne 1 ]
then
	exit 1
fi

#设置运行环境变量
RUNLIB="./web/WEB-INF/classes"
for jar in `ls ./lib/*.jar`
do
   RUNLIB="${RUNLIB}:${jar}" 
done
${JAVACMD} -Xmx512M -cp $RUNLIB aladdin.AladdinServer