package ge.nika.sqlbuilder.where

import ge.nika.sqlbuilder.db.DbField
import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.where.SimpleWhere.Companion.lessThen

internal data class SimpleWhere(
    val left: DbField,
    val right: QueryParam,
    val operand: WhereOperator,
) : Where {
    companion object {
        fun DbField.eq(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.EQ,
        )

        fun DbField.lessThenEquals(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.LTE,
        )

        fun DbField.greaterThenEquals(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.GTE,
        )

        fun DbField.greaterThen(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.GT,
        )

        fun DbField.lessThen(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.LT,
        )

        fun DbField.isNot(other: Any) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.NOT_EQ,
        )

        fun DbField.isIn(other: Iterable<*>) = SimpleWhere(
            left = this,
            right = QueryParam.of(other),
            operand = WhereOperator.IN,
        )
    }

    override fun and(other: Where): Where {
        return AndWhere(this, other)
    }

    override fun or(other: Where): Where {
        TODO("Not yet implemented")
    }

    override fun sql(dialect: SqlDialect): String =
        "${left.fieldName} ${dialect.whereOperatorToSql(operand)} ${dialect.queryParamAsSql(right)}"
}