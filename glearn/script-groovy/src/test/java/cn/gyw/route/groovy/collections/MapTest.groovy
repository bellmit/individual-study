package cn.gyw.route.groovy.collections

def timestampFormat = 'yyyy-MM-dd HH:mm:ss.SSS'
def callDispatched = null

if (callDispatched == null) {
	callDispatched = []
}

callDispatched << [
	'dispatchTime': new Date().format(timestampFormat),
	'roomId': '12345',
	'customerId': '2345',
	'tellerId': '33333',
	'priority': 20
]

callDispatched << [
	'dispatchTime': new Date().format(timestampFormat),
	'roomId': '777',
	'customerId': '888',
	'tellerId': '999',
	'priority': 21
]

println "${callDispatched[-1]}"
println "${callDispatched}"
println "${callDispatched[1]}"

def priority = callDispatched[-1]?.priority?:0
println ">>$priority"