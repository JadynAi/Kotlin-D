package com.jadynai.kotlindiary.designMode.authentication

/**
 *JadynAi since 2021/8/11
 */
//interface ApiAuthenticator {
//
//    fun auth(url: String?)
//    fun auth(apiRequest: ApiRequest?)
//}
//
//class DefaultApiAuthenticatorImpl : ApiAuthenticator {
//
//    private var credentialStorage: CredentialStorage
//
//    constructor() {
//        credentialStorage = MysqlCredentialStorage()
//    }
//
//    constructor(credentialStorage: CredentialStorage) {
//        this.credentialStorage = credentialStorage
//    }
//
//    override fun auth(url: String?) {
//        val apiRequest: ApiRequest = ApiRequest.buildFromUrl(url)
//        auth(apiRequest)
//    }
//
//    override fun auth(apiRequest: ApiRequest) {
//        val appId: String = apiRequest.getAppId()
//        val token: String = apiRequest.getToken()
//        val timestamp: Long = apiRequest.getTimestamp()
//        val originalUrl: String = apiRequest.getOriginalUrl()
//        val clientAuthToken = AuthToken(token, timestamp)
//        if (clientAuthToken.isExpired()) {
//            throw RuntimeException("Token is expired.")
//        }
//        val password: String = credentialStorage.getPasswordByAppId(appId)
//        val serverAuthToken: AuthToken = AuthToken.generate(originalUrl, appId, password, timestamp)
//        if (!serverAuthToken.match(clientAuthToken)) {
//            throw RuntimeException("Token verfication failed.")
//        }
//    }
//}