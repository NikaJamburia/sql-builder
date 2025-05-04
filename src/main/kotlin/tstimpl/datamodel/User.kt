package ge.nika.tstimpl.datamodel

import ge.nika.sqlbuilder.db.DbField
import ge.nika.sqlbuilder.annotations.DatabaseTable
import java.math.BigDecimal
import java.time.LocalDate

@DatabaseTable("users")
data class User(
    val id: Long,
    val username: String,
    val email: String,
    val balance: BigDecimal,
    val birthday: LocalDate,
) {
    companion object {

    }

    enum class Fields(override val fieldName: String) : DbField {
        id("users.id"),
        username("users.user_name"),
        email("users.email"),
        balance("users.balance"),
        birthday("users.birth_day"),
        all("users.*");
    }
}
