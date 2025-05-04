package ge.nika.sqlbuilder.db

internal class AliasedDbField(
    private val alias: String,
    private val dbField: DbField,
): DbField {

    companion object {
        fun DbField.aliasedBy(alias: String): AliasedDbField = AliasedDbField(alias, this)
    }

    override val fieldName: String
        get() = "${dbField.fieldName} AS $alias"
}