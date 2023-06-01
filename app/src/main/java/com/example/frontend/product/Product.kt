package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class Product(val name: String, val price: String)

//data class Product(
//    @SerializedName("email") val number: Int,
//    @SerializedName("sort") val sort: SortInfo,
//    @SerializedName("size") val size: Int,
//    @SerializedName("content") val content: List<ContentItem>,
//    @SerializedName("numberOfElements") val numberOfElements: Int,
//    @SerializedName("pageable") val pageable: PageableInfo,
//    @SerializedName("first") val first: Boolean,
//    @SerializedName("last") val last: Boolean,
//    @SerializedName("empty") val empty: Boolean
//){
//    data class SortInfo(
//        @SerializedName("empty") val empty: Boolean,
//        @SerializedName("sorted") val sorted: Boolean,
//        @SerializedName("unsorted") val unsorted: Boolean
//    )
//
//    data class ContentItem(
//        @SerializedName("id") val id: Int,
//        @SerializedName("accountBookId") val accountBookId: Int,
//        @SerializedName("name") val name: String,
//        @SerializedName("price") val price: Int,
//        @SerializedName("purchaseDate") val purchaseDate: String,
//        @SerializedName("detail") val detail: String,
//        @SerializedName("country") val country: String,
//        @SerializedName("city") val city: String,
//        @SerializedName("like") val like: Int
//    )
//
//    data class PageableInfo(
//        @SerializedName("sort") val sort: SortInfo,
//        @SerializedName("offset") val offset: Int,
//        @SerializedName("pageNumber") val pageNumber: Int,
//        @SerializedName("pageSize") val pageSize: Int,
//        @SerializedName("paged") val paged: Boolean,
//        @SerializedName("unpaged") val unpaged: Boolean
//    )
//}
