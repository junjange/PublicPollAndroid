package com.junjange.myapplication.utils

object API{
    const val BASE_URL : String = "http://13.209.119.116:8080" // 서버 주소
    const val GET_ALL_POLLS : String = "/poll/all"
    const val GET_HOT_POLLS : String =  "/poll/hot"
    const val GET_VIEW_POLLS : String =  "/poll/{pollId}"
    const val GET_QUICK_POLLS : String = "/poll/speed"
    const val GET_HASHTAG_NAME : String =  "/hashtag/name/{keyword}"
    const val GET_HASHTAG : String =  "/hashtag/id/{hashtagId}"
    const val GET_COMMENT : String = "/comment/id/{pollId}"
    const val POST_COMMENT : String =  "/comment/add"
    const val POST_BALLOT : String =  "/ballot/add"
    const val POST_REVOTE : String =  "/ballot/revote"
    const val DELETE_POLLS : String =  "/poll/{pollId}"
    const val MY_POLLS : String = "/poll/my"
    const val MY_BALLOT : String = "/ballot/my"

}