package cn.gyw.route.groovy.datatypes

import org.codehaus.groovy.runtime.typehandling.GroovyCastException

// 自动装箱、拆箱
assert 'ABCDE'.indexOf(67) == 2

// 1 + 1:自动装箱，1.plus(1)，Integer，更完全的面向对象

/*
 * 分派类型
 * 
 */
def a = 1 // java.lang.Integer
def b = 1.0f // java.lang.Float
int c = 1 // java.lang.Integer
float d = 1 // java.lang.Float

Integer myInt = 3;
try {
	myInt = new Object();
	assert false;
} catch (GroovyCastException e) {
	assert true;
}
