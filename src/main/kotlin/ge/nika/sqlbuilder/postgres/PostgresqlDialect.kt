package ge.nika.sqlbuilder.postgres

import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.dialect.SqlFunctionType
import ge.nika.sqlbuilder.where.QueryParam
import ge.nika.sqlbuilder.where.WhereOperator
import java.math.BigDecimal
import java.time.LocalDate

internal class PostgresqlDialect : SqlDialect {
    override fun queryParamAsSql(queryParam: QueryParam): String {
        val value = queryParam.value
        return when (value) {
            null -> "NULL"
            is String -> "'${value}'"
            is BigDecimal -> "NUMERIC(${value.unscaledValue()}, ${value.scale()})"
            is Number -> value.toString()
            is LocalDate -> "DATE($value)"
            is Iterable<*> -> buildString {
                append("(")
                value.forEach { append("${queryParamAsSql(QueryParam.of(it))}, ") }
                this.delete(length - 2, length)
                append(")")
            }
            else -> error("Can not pass variable of $value to Postgresql query!")
        }
    }

    override fun whereOperatorToSql(operator: WhereOperator): String = when(operator) {
        WhereOperator.EQ -> "="
        WhereOperator.NOT_EQ -> "IS NOT"
        WhereOperator.GT -> ">"
        WhereOperator.LT -> "<"
        WhereOperator.LTE -> "<="
        WhereOperator.GTE -> "=>"
        WhereOperator.IN -> "IN"
    }

    override fun functionName(functionType: SqlFunctionType): String = when(functionType) {
        SqlFunctionType.MAX -> "MAX"
    }
}