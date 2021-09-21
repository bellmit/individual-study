package cn.gyw.route.groovy

/**
 * 语法糖
 */

def obj = [
	x: 'hello'
	]
println ">>${obj?.x}"
println ">>${obj?.y}"
println ">>${obj?:1234}"
