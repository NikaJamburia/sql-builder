package ge.nika.sqlbuilder

import ge.nika.sqlbuilder.db.AliasedDbField.Companion.aliasedBy
import ge.nika.sqlbuilder.dialect.SqlDialectType
import ge.nika.sqlbuilder.where.SimpleWhere.Companion.greaterThen
import ge.nika.tstimpl.datamodel.User
import ge.nika.sqlbuilder.where.SimpleWhere.Companion.greaterThenEquals
import ge.nika.sqlbuilder.where.SimpleWhere.Companion.isIn
import java.math.BigDecimal
import java.time.LocalDate

fun main() {
    val SQL = Sql.ofDialect(SqlDialectType.POSTGRES_SQL)

    val sql = SQL.select(
        SQL.f.max(User.Fields.balance).aliasedBy("maxBalance"),
        User.Fields.birthday,
        User.Fields.balance,
    ).from(User)
        .where(
            User.Fields.balance.greaterThenEquals(BigDecimal.TEN)
                .and(User.Fields.birthday.isIn(
                    listOf(
                        LocalDate.now(),
                        LocalDate.now().minusDays(1),
                        LocalDate.now().minusDays(2)
                    ))
                ).and(User.Fields.id.greaterThen(0))
        )
        .paged(pageNumber = 1, pageSize = 20)
        .formatted(true)
        .build()

    println(sql)
}