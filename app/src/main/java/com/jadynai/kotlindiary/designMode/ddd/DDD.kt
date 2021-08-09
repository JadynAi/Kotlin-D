package com.jadynai.kotlindiary.designMode.ddd

/**
 *JadynAi since 2021/8/4
 */
class UserController {

    private val userService = UserService()

    private fun getUserById(userId: Long): UserVo {
        val userBo = userService.getUserById(userId)
        // convert Bo to Vo
        return UserVo()
    }
}

private class UserVo {

}

/**
 * business ：业务层
 * 
 * 贫血和充血的区别就在这里。
 * 单纯的UserBo只是纯数据，不包含业务，这就是贫血
 * 充血则在业务层新建Domain来替换Bo，Domain既包含业务又包含数据，另外UserService则显得单薄了
 * */
private class UserService {

    private val userRepository = UserRepository()

    fun getUserById(userId: Long): UserBo {
        val userEntity = userRepository.getUserById(userId)
        // convert entity to Bo
        val userBo = UserBo()
        return userBo
    }

}

private class UserBo {
    private val userId = 0
}

/**
 * repository： 数据访问层
 * */
private class UserRepository {

    fun getUserById(userId: Long): UserEntity {
        return UserEntity()
    }

}

private class UserEntity {
    // 纯数据
}


