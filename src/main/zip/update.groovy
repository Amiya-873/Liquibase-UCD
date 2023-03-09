/**
 * (c) Copyright IBM Corporation 2014, 2017.
 * This is licensed under the following license.
 * The Eclipse Public 1.0 License (http://www.eclipse.org/legal/epl-v10.html)
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import com.urbancode.air.plugin.helpers.AirPluginTool

def apTool = new AirPluginTool(this.args[0], this.args[1])

final def workDir = new File('.').canonicalFile
final def props = apTool.getStepProperties()
final def inputPropsFile = new File(args[0])

def command = props['command']
def driver = props['driver']
def driverClasspath = props['driverClasspath']
def jdbcURL = props['jdbcURL']
def username = props['username']
def password = props['password']
def changeLogFile = props['changeLogFile']
def updateCnt = props['updateCount']
def preview = props['preview'].toBoolean()

def lqcmd = command + " "
lqcmd = lqcmd + "--driver=" + driver + " "
lqcmd = lqcmd + "--classpath=" + driverClasspath + " "
lqcmd = lqcmd + " --url=" + jdbcURL + " "
lqcmd = lqcmd + "--username=" + username + " "
lqcmd = lqcmd + "--password=" + password + " "
lqcmd = lqcmd + "--changeLogFile=" + changeLogFile + " "

if (updateCnt) {
	if (preview) lqcmd = lqcmd + "updateCountSQL "
	else lqcmd = lqcmd + "updateCount "
	lqcmd = lqcmd + updateCnt
}
else {
	if (preview) lqcmd = lqcmd + "updateSQL"
	else lqcmd = lqcmd + "update "
}

println lqcmd

def proc = lqcmd.execute()
proc.waitForProcessOutput(System.out, System.out)
proc.waitFor()

System.exit proc.exitValue()
