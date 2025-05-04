package ge.nika.sqlbuilder.builder

import ge.nika.sqlbuilder.annotations.DatabaseTable
import ge.nika.sqlbuilder.db.DbField
import ge.nika.sqlbuilder.dialect.SqlDialect
import ge.nika.sqlbuilder.where.Where

class SelectBuilder(
    private val fields: List<DbField>,
    private val dialect: SqlDialect,
) {

    private lateinit var from: String
    private var where: Where? = null
    private var limit: Int? = null
    private var offset: Int = 0
    private var formatted: Boolean = false

    fun from(table: Any): SelectBuilder {
        from = getTableName(table)
        return this
    }

    fun limit(limit: Int): SelectBuilder {
        this.limit = limit
        return this
    }

    fun offset(offset: Int): SelectBuilder {
        this.offset = offset
        return this
    }

    fun paged(pageNumber: Int, pageSize: Int): SelectBuilder {
        check(pageNumber >= 1)
        check(pageSize >= 1)

        offset = pageSize * pageNumber
        limit = pageNumber
        return this
    }

    fun where(wh: Where): SelectBuilder {
        where = wh
        return this
    }

    fun formatted(isFormatted: Boolean): SelectBuilder {
        formatted = isFormatted
        return this
    }

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
        """.trimIndent().formatIfNecessary()
    }

    private fun String.formatIfNecessary(): String {
        return if (!formatted) {
            this
        } else {
            this
                .replace("SELECT", "SELECT\n\t")
                .replace("FROM", "FROM\n\t")
                .replace("WHERE", "WHERE\n\t")
                .replace("LIMIT", "LIMIT\n\t")
                .replace("OFFSET", "OFFSET\n\t")
                .replace(" AND ", " AND\n\t\t")
        }
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