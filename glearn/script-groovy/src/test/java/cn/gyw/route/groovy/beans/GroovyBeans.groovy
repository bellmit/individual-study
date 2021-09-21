package cn.gyw.route.groovy.beans

// bean definition
class BookBean {
	String title
}

def groovyBookBean = new BookBean()
groovyBookBean.setTitle('groovy in action')
assert groovyBookBean.getTitle() == 'groovy in action'
groovyBookBean.title = 'groovy in world'
assert groovyBookBean.title == 'groovy in world'