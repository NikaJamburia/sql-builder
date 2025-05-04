package ge.nika.sqlbuilder.where

class QueryParam(
    val value: Any?,
) {
    companion object {
        fun of(any: Any?): QueryParam = QueryParam(any)
    }
}
