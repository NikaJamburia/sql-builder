package ge.nika.sqlbuilder

import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.dialect.SqlDialectType
import ge.nika.sqlbuilder.functions.SqlFunctions
import ge.nika.sqlbuilder.postgres.PostgresqlDialect

class Sql private constructor(
    private val dialect: SqlDialect,
) {

    companion object {
        fun ofDialect(dialectType: SqlDialectType): Sql = Sql(
            when(dialectType) {
                SqlDialectType.POSTGRES_SQL -> PostgresqlDialect()
                else -> error("Dialect not supported yet")
            }
        )
    }

    val functions: SqlFunctions = SqlFunctions(dialect)

    fun buildSql(builderFun: SqlBuilder.() -> Unit): String {
        val builder = SqlBuilder(dialect)
        builder.builderFun()
        return builder.build()
    }
}