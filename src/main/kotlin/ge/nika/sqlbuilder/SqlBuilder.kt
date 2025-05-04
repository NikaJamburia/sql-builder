package ge.nika.sqlbuilder

import ge.nika.sqlbuilder.annotations.DatabaseTable
import ge.nika.sqlbuilder.db.DbField
import ge.nika.sqlbuilder.db.FunctionOverDbField
import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.dialect.SqlFunctionType
import ge.nika.sqlbuilder.where.Where

class SqlBuilder(
    private val dialect: SqlDialect,
) {

    private lateinit var from: String
    private lateinit var fields: List<DbField>
    private var where: Where? = null
    private var limit: Int? = null
    private var offset: Int = 0

    fun select(vararg fields: DbField) {
        this.fields = fields.toList()
    }

    fun from(table: Any) {
        from = getTableName(table)
    }

    fun limit(limit: Int) {
        this.limit = limit
    }

    fun offset(offset: Int) {
        this.offset = offset
    }

    fun paged(pageNumber: Int, pageSize: Int) {
        check(pageNumber >= 1)
        check(pageSize >= 1)

        offset = pageSize * pageNumber
        limit = pageNumber
    }

    fun where(wh: Where) {
        where = wh
    }

    fun max(field: DbField): DbField = FunctionOverDbField(
        functionName = dialect.functionName(SqlFunctionType.MAX),
        dbField = field,
    )

    fun build(): String {
        val fullFieldNames = fields
            .map { it.fieldName }
            .foldRight("") { acc, name -> "$acc, $name" }
            .dropLast(2)
        return """
            SELECT $fullFieldNames
            FROM $from
            ${ where?.let { "WHERE ${it.sql(dialect)}" } ?: "" }
            ${ limit?.let { "LIMIT $limit" } ?: "" }
            OFFSET $offset
        """.trimIndent()
    }

    private fun getTableName(dbTable: Any): String {
        val kClass = dbTable::class
        return if (kClass.isCompanion) {
            kClass.java.declaringClass
                .getDeclaredAnnotation(DatabaseTable::class.java)
                ?.name
                ?: error("Not a table")
        } else {
            error("Not a table")
        }
    }
}