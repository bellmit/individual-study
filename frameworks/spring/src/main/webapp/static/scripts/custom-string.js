function stringLearn() {
    var x = "JohnJohn"; // x 是字符串
    console.log(x);
    y = x.charAt(2); // h
    console.log(y);
    y = x.charCodeAt(2); // 104
    console.log(y);
    y = x.concat(y, y); // JohnJohn104104, x+y+y
    console.log(y);
    y = x.indexOf('h'); // 2, 索引从0开始
    console.log(y);
    y = x.lastIndexOf('h'); // 6
    console.log(y);
    y = x.slice();
    console.log(y);
    y = x.split('o'); //J,hnJ,hn
    console.log(y);
    y = x.substr(2); // hnJohn
    console.log(y);
    y = x.substring(2,4) // hn，[2,3]
    console.log(y);
    y = x.toLocaleLowerCase(); // johnjohn,小写
    console.log(y);
    y = x.toLocaleUpperCase(); // JOHNJOHN,大写
    console.log(y);
    y = x.toString(); // 转成Stirng
    console.log(y);
    y = x.toUpperCase(); // JOHNJOHN,大写
    console.log(y);
    y = x.trim(); // JohnJohn,去除两端的空格
    console.log(y);
    y = x.valueOf(); // 返回某个字符串对象的原始值
    console.log(y);
}