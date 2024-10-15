package com.parking.api.common.dto

import com.parking.domain.exception.enum.ExceptionCode

data class PageContentDTO<T>(
    val code: String = "",
    val message: String? = null,
    val content: T? = null,
    val page: Int,
    val totalPage: Int
) {
    companion object {
        fun <T> success(content: T? = null, page: Int, totalPage: Int): PageContentDTO<T> {
            return PageContentDTO(
                code = ExceptionCode.SUCCESS.name,
                content = content,
                page = page,
                totalPage = totalPage
            )
        }
    }
}
