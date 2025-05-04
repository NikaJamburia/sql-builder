package ge.nika.sqlbuilder.where

import ge.nika.sqlbuilder.dialect.SqlDialect

internal data class AndWhere(
    val left: Where,
    val right: Where,
) : Where {
    override fun and(other: Where): Where {
        return AndWhere(this, other)
    }

    override fun or(other: Where): Where {
        TODO("Not yet implemented")
    }

    override fun sql(dialect: SqlDialect): String {
        return "(${left.sql(dialect)}) AND (${right.sql(dialect)})"
    }
}