class Logger {

  constructor(moduleName) {
    this.moduleName = moduleName;
  }

  debug(content) {
    this.sendLog('debug', content);
  }

  info(content) {
    this.sendLog('info', content);
  }

  warn(content) {
    this.sendLog('warn', content);
  }

  error(content) {
    this.sendLog('error', content);
  }

  sendLog(level, content) {
    let logData = {
      'level': level,
      'module': this.moduleName,
      'content': content,
    };
    let jsonString = JSON.stringify(logData);
    // 暂时使用console工具
    console.log(jsonString);
  }
}

class LoggerFactory {

  static getLogger(moduleName) {
    return new Logger(moduleName);
  }

}

export { LoggerFactory as default };