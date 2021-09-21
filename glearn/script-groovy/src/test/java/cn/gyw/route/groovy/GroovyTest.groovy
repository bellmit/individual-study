package cn.gyw.route.groovy

import groovy.xml.XmlSlurper
import groovy.json.JsonOutput

// 文件读取
/*def number = 0
 new File('D:\\7_Temp\\datas.txt').eachLine { line ->
 number ++
 println "$number : $line"
 }*/

// 集合
/*def classes = [String, List, File]
 for (clazz in classes) {
 println clazz.package.name
 }
 // --> 简介写法，遍历
 println classes*.package*.name*/

// XML 操作
//def filePathStr = this.getClass().getClassLoader().getResource('customers.xml').getFile()
//println "filePath : $filePathStr"
//def customers = new XmlSlurper().parse(new File(filePathStr))
//for (customer in customers.corporate.customer) {
//	// @:属性访问
//	println "${customer.@name} work in ${customer.@company}"
//}
//
//// 网站信息拉取
//def urls = ['http://www.baidu.com', 'http://www.bing.com']*.toURL()

def txnFields = [_type:"com.aw.teller.transaction.TellerTransaction.GET_HJDK_BY_NO",
 _response:"OK",
 _clientTime:null,
 _serverTime:"2020-06-19 11:20:28.341",
 _sourceAccountNumber:null,
 _targetAccountNumber:null,
 _requestAmount:null,
 _transactionStatus:0,
 hjdkinformation_customerimage:"",
 hjdkinformation_screenshot:"",
 request_targetaccount:null,
 tellerscripts_confirmscript:"请问您是张三丰本人吗？麻烦您调整一下镜头距离。这边需要拍照认证对比。1.对比不通过：抱歉网上无法通过。后台人员核实后会再联系您。感谢您的配合。2.认证通过：您好，认证已经通过。请问您的出生日期是1964/12/02吗？请问网站上的贷款是您本人自愿申请的吗？",
 tellerscripts_rightsconfirmscript:"请问您是否已知晓授权我行查询征信？是否知晓我行合同中的相关条款及解释？1-3个工作日后还需您登陆手机银行进行合同签约放款。",
 hjdkinformation_contractno:"C20200520988723-2",
 request_method:"AUTHORIZE",
 tellerscripts_endingscript:"好的，感谢您的配合。请问你是否还需要其他帮助？",
 hjdkinformation_annualrate:5.4,
 response_result:null,
 request_transactionnumber:"05480002006191120285697248",
 tellerrequest_institutionid:null,
 tellerrequest_querytype:null,
 request_terminaltransactionkey:null,
 request_thirdpartyaccount:null,
 request_businessdate:null,
 request_rawmessage:null,
 tellerrequest_tellername:null,
 response_backendresponsecode:null,
 systemprocessing_detail:"processed by route transaction/get-hjdk-by-no",
 response_systemreferencenumber:null,
 tellerrequest_data:null,
 response_responseaction:null,
 hjdkinformation_code:"FPD1029",
 systemprocessing_node:"Transaction-Service-10.1.6.91",
 hjdkinformation_amount:"170000.00 CNY",
 response_rawmessage:null,
 request_transactiontime:"Fri Jun 19 11:20:28 CST 2020",
 request_requestamount:null,
 tellerscripts_openingscript:"您好！张先生，我是AW银行客服中心的视频客服，工号00134。很高兴为您服务。下面我们将开始视频录制。整个视频录制请勿挂机。感谢您的配合。",
 response_responsecode:null,
 response_businessdate:null,
 hjdkinformation_facerecognition:"",
 response_feeamount:null,
 tellerrequest_sessionid:null,
 hjdkinformation_name:"互金贷款",
 hjdkinformation_duration:12,
 request_feeamount:null,
 tellerrequest_transactioncode:null,
 request_pinblock:null,
 hjdkinformation_category:"企业贷款",
 tellerscripts_contractscript:"本次贷款金额为1700万元。本地贷款利率为5.4%每年。本地贷款期限为12个月，是否清楚明白。",
 tellerrequest_tellerid:"a6f50c73-f767-43ab-bffe-a6f6e6995136",
 request_sourceaccount:null]

println JsonOutput.toJson(txnFields)