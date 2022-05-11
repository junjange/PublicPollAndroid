package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

/**
@SerializedName("result") 은 서버에서 가져온 객체의 'result' 값을 매핑된 변수에 넣겠다는 뜻
서버에서 가져온 변수명과 내가 쓰려는 변수명을 다르게 해도 된다.
 **/

// 리사이클러 뷰 아이템 클래스
data class ModelBoard(@SerializedName("result") val board: List<ModelBoardComponent>)


