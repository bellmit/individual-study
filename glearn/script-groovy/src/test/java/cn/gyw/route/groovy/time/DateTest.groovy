package cn.gyw.route.groovy.time

import java.text.SimpleDateFormat

def sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
long time = 30 * 60 * 1000

def now = new Date()
def newDate = new Date(now.getTime() + time)
println "$time ---> ${sdf.format(now)} -- ${sdf.format(newDate)}"

//assert now.before(now)

def ft = new Date().parse('yyyy-MM-dd HH:mm:ss', '2020-08-19 09:44:30')
println ft