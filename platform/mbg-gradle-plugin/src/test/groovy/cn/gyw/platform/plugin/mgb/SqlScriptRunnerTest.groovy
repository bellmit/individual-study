package cn.gyw.platform.plugin.mgb

import cn.gyw.platform.plugin.mbg.SqlScriptRunner

def driver = "com.mysql.jdbc.Driver"
def url    = "jdbc:mysql://127.0.0.1:3306/cs_order?useSSL=false"
def username = "root"
def password = "root"

def sqlScriptRunner =
		new SqlScriptRunner("", url, username, password)

def tables = sqlScriptRunner.getAllTableName()
println ">>$tables"