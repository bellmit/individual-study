package cn.gyw.route.groovy.control

/**
 * 控制结构
 */
cn.gyw.groovy.route false

if (null) {
	assert false
} else {
	assert true
}

def clinks = 0
for (i in 0..9) {
	clinks += i
}

def list = [0, 1, 2, 3]
for (j in list) {
	assert j == list[j]
}

list.each() { item ->
	assert item == list[item]
}

switch (3) {
	case 1 : assert false; break
	case 3 : assert true;break
	default: assert false
}
