package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("number")
    val number: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("size")
    val size: Int,
    @SerializedName("content")
    val content: List<ContentItem>,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("empty")
    val empty: Boolean
) {
    data class Sort(
        @SerializedName("empty")
        val empty: Boolean,
        @SerializedName("sorted")
        val sorted: Boolean,
        @SerializedName("unsorted")
        val unsorted: Boolean
    )

    data class ContentItem(
        @SerializedName("product")
        val product: Product,
        @SerializedName("isLike")
        val isLike: Boolean
    )

    data class Product(
        @SerializedName("id")
        val id: Long,
        @SerializedName("accountBookId")
        val accountBookId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("purchaseDate")
        val purchaseDate: String,
        @SerializedName("detail")
        val detail: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("like")
        val like: Int,
        @SerializedName("productType")
        val productType: String
    )

    data class Pageable(
        @SerializedName("sort")
        val sort: Sort,
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("pageNumber")
        val pageNumber: Int,
        @SerializedName("pageSize")
        val pageSize: Int,
        @SerializedName("paged")
        val paged: Boolean,
        @SerializedName("unpaged")
        val unpaged: Boolean
    )
}
