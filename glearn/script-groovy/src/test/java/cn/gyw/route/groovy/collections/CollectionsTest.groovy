package cn.gyw.route.groovy.collections

import java.text.SimpleDateFormat

import cn.gyw.groovy.model.Teller

//for (i in 1..10) {
//	println ">> $i"
//}

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

List<Teller> datas = []
datas.add(new Teller(name: "aaa", age: 23, date: sdf.parse("2020-08-07 10:23:19")))
datas.add(new Teller(name: "bbb", age: 24, date: sdf.parse("2020-08-07 10:23:20")))
datas.add(new Teller(name: "ccc", age: 25, date: sdf.parse("2020-08-07 10:23:31")))
datas.add(new Teller(name: "ddd", age: 25, date: sdf.parse("2020-08-07 10:23:18")))
datas.add(new Teller(name: "mmm", age: 25, date: sdf.parse("2020-08-07 10:23:20")))
datas.add(new Teller(name: "eee", age: 27, date: sdf.parse("2020-08-07 10:23:23")))
datas.add(new Teller(name: "fff", age: 28, date: sdf.parse("2020-08-07 10:23:24")))

datas.sort { a, b ->
	println "${a} -- ${b}"
	if (a.age > b.age) {
		return 1
	}
	if (a.age < b.age) {
		return -1
	}
	if (a.age == b.age) {
		println '-------------------------->'
		return a.date <=> b.date
	}

}

GroovyCollections.

println '------------------------------'
println datas