package cn.gyw.groovy.classloader

/*
groovy.lang.GroovyClassLoader$InnerLoader@45815ffc
groovy.lang.GroovyClassLoader@6ce86ce1
org.codehaus.groovy.tools.RootLoader@17f052a3
sun.misc.Launcher$AppClassLoader@14dad5dc
sun.misc.Launcher$ExtClassLoader@3d3ba765
 */
def cl = this.getClass().getClassLoader()

while (cl) {
	println "$cl"
	cl = cl.parent
}