package ge.nika.sqlbuilder

import ge.nika.sqlbuilder.builder.SelectBuilder
import ge.nika.sqlbuilder.db.DbField
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

    val f: SqlFunctions = SqlFunctions(dialect)

    fun select(
        vararg fields: DbField,
    ): SelectBuilder {
        val builder = SelectBuilder(fields.toList(), dialect)
        return builder
    }
}