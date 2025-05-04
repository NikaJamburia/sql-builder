package ge.nika.sqlbuilder.db

internal class FunctionOverDbField(
    private val functionName: String,
    private val dbField: DbField,
) : DbField {
    override val fieldName: String
        get() = "$functionName(${dbField.fieldName})"
}