package cn.gyw.platform.plugin.mgb

def name = "cs_orr"
StringBuilder sb = new StringBuilder()
String[] strs = name.toLowerCase().split("_")
for (String s : strs) {
	def newStr = s.substring(0, 1).toUpperCase() + s.substring(1)
	println ">> $newStr"
	sb.append(newStr)
}
def str = sb.toString()

println str