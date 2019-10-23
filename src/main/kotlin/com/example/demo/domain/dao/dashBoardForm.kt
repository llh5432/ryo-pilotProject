package com.example.demo.domain.dao

import com.example.demo.domain.Enum.MenuType
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class DashBoard(
        var adminTotal : Int = 0,
        var adminGrateUser : TopUser,
        var adminGrateMenu : TopMenu,
        var adminGrateType : TopType,
        var adminUsersNum : Int = 0,
        var adminOrdersNum : Int= 0
)

data class TopUser(

        var topUserAccount: String = "",
        var total: Int? = 0
)

data class TopMenu(

        var topMenu: String = "",
        var total: Int? = 0
)

data class TopType(

        var topType: MenuType,
        var total: Int? = 0
)