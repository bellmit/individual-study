package cn.gyw.route.groovy.dynamic

// 1.Assigning a ProxyMetaClass to a GroovyObject for tracing method calls
class InspectMe {
	int outer() {
		return inner();
	}
	
	private int inner() {
		return 1;
	}
}
// set up
def tracer = new TracingInterceptor(writer: new StringWriter());
def proxyMetaClass = ProxyMetaClass.getInstance(InspectMe)
proxyMetaClass.interceptor = tracer
// Assigning a metaClass
InspectMe inspectMe = new InspectMe()
inspectMe.metaClass = proxyMetaClass

assert 1 == inspectMe.outer()
assert "\n" + tracer.writer.toString() == """
before cn.gyw.route.groovy.dynamic.InspectMe.outer()
  before cn.gyw.route.groovy.dynamic.InspectMe.inner()
  after  cn.gyw.route.groovy.dynamic.InspectMe.inner()
after  cn.gyw.route.groovy.dynamic.InspectMe.outer()
"""

// 2. Add methods by metaclass
class MyGroovy1 {}
def before = new MyGroovy1()
MyGroovy1.metaClass.myProp = "MyGroovy prop"
MyGroovy1.metaClass.test = { -> myProp}
try {
	before.test()
	assert false, "should throw error"
} catch(e) {}
assert new MyGroovy1().test() == "MyGroovy prop"

// 3.modify java instance
def str = new String()
str.metaClass.myProp = "MyGroovy prop"
str.metaClass.test = { -> myProp}
try {
	new String().test()
	assert false, "should throw error"
} catch(e) {}
assert str.test() == "MyGroovy prop"

// 4.add static method to instance
Integer.metaClass.static.answer = { -> 27}
assert Integer.answer() == 27