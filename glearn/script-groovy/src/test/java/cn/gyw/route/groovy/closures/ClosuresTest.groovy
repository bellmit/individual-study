package cn.gyw.route.groovy.closures

/**
 * 闭包学习
 */
// groovy feature method
cn.gyw.groovy.route
def partyPeople = 100
1.upto(partyPeople) {guestNumber ->
	//	println "1>$guestNumber"
	clinksWithGuest = guestNumber - 1
	totalClinks += clinksWithGuest
	//	println "2>$guestNumber"
}

assert partyPeople == 100
//println (totalClinks)

// 1.List 遍历
[1, 2, 3].each { item ->
	//	print item + "\t"
}

// 简单定义
def printer = {line -> println "$line" }
//printer("111")

// 方法定义
def Closure getPrinter() {
	return { line -> println line }
}

// 方法引用为闭包 .&
class SizeFilter {
	Integer limit
	boolean sizeUpTo(String value) {
		return value.size() <= limit
	}
}
SizeFilter filter6 = new SizeFilter(limit:6)
SizeFilter filter5 = new SizeFilter(limit:5)
Closure sizeUpTo6 = filter6.&sizeUpTo
def words = ['long string', 'medium', 'short']
assert 'medium' == words.find(sizeUpTo6)
assert 'short' == words.find(filter5.&sizeUpTo)

// 多个方法运行时重载
class MultiMethodSample {
	int mysteryMethod (String value) {
		return value.length()
	}

	int mysteryMethod (List list) {
		return list.size()
	}

	int mysteryMethod (int x, int y) {
		return x +y
	}
}
MultiMethodSample instance = new MultiMethodSample()
Closure multi = instance.&mysteryMethod
assert 10 == multi ('string arg')
assert 3 == multi (['list', 'a', 'ccc'])
assert 5 == multi (2,3)

// 闭包调用
def benchmark (int repeat, Closure worker) {
	def start = System.nanoTime()
	
	repeat.times { worker(it) }
	
	def stop = System.nanoTime()
	return stop - start
}
def slow = benchmark(10000) { it /2 }
def fast = benchmark(1000) { it.intdiv(2) }
assert fast * 2 < slow

def fib
fib = { it < 2 ? 1 : fib(it - 1) + fib (it -2)}
fib.memoize()
assert fib(40) == 165_580_141