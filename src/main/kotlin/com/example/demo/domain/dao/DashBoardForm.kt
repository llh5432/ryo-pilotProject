package com.example.demo.domain.dao

import com.example.demo.domain.Enum.MenuType
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class DashBoard(
        var adminTotal: Int = 0,
        var adminTopUser: TopUser? = null,
        var adminTopMenu: TopMenu? = null,
        var adminTopType: TopType? = null,
        var adminUserCount: Int = 0,
        var adminOrderCount: Int= 0
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class TopUser(

        var topUserAccount: String? = "",
        var total: Int? = 0
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class TopMenu(

        var topMenu: String? = "",
        var total: Int? = 0
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class TopType(

        var topType: MenuType,
        var total: Int? = 0
)
