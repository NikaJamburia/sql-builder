package ge.nika.sqlbuilder.dialect

import ge.nika.sqlbuilder.where.QueryParam
import ge.nika.sqlbuilder.where.WhereOperator

interface SqlDialect {
    fun queryParamAsSql(queryParam: QueryParam): String
    fun whereOperatorToSql(operator: WhereOperator): String
    fun functionName(functionType: SqlFunctionType): String
}