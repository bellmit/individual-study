def packCommonResponseFields(data) {
    def result = _object?.body
    return [
        'errorCode' : result?.errorCode,
        'message'   : result?.message,
        'data'      : data ?: [:]
    ]
}