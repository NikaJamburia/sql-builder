package ge.nika.sqlbuilder.where

import ge.nika.sqlbuilder.dialect.SqlDialect

interface Where {
    fun and(other: Where): Where
    fun or(other: Where): Where
    fun sql(dialect: SqlDialect): String
}

enum class WhereOperator {
    EQ, LTE, GTE, GT, LT, NOT_EQ, IN
}