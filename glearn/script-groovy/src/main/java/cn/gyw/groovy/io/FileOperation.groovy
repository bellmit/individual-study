/**
 * 
 */
package cn.gyw.groovy.io

/**
 * @author yuewu_guan_ext
 *
 */

// 文件读取
def number = 0
new File('D:\\7_Temp\\datas.txt').eachLine { line ->
	number ++
	println "$number : $line"
}
