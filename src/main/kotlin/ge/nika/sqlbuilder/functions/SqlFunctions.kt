package ge.nika.sqlbuilder.functions

import ge.nika.sqlbuilder.db.DbField
import ge.nika.sqlbuilder.db.FunctionOverDbField
import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.dialect.SqlFunctionType

class SqlFunctions(
    private val dialect: SqlDialect,
) {
    fun max(field: DbField): DbField = FunctionOverDbField(
        functionName = dialect.functionName(SqlFunctionType.MAX),
        dbField = field,
    )
}