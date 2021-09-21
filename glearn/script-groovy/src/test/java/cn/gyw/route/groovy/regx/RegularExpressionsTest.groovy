package cn.gyw.route.groovy.regx

assert '123456' =~ /\d+/
assert 'xxx' == '123'.replaceAll(/\d/, 'x')

def code = '0022'

assert '0011,   0022    ,0043' ==~ /.*,(\s)*${code}(\s)*,.*/