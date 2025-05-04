package ge.nika.sqlbuilder.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseTable(
    val name: String
)
