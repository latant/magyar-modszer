open class Matrix<T> private constructor(
    val height: Int, val width: Int,
    private val list: MutableList<MutableList<T>>
) {
    constructor(height: Int, width: Int, init: (Int, Int) -> T) :
            this(height, width, MutableList(height) { i -> MutableList(width) { j -> init(i, j) } })
    operator fun get(i: Int, j: Int) = list[i][j]
    operator fun set(i: Int, j: Int, value: T) { list[i][j] = value }
    override fun toString() = list.toString()

    fun <R> map(transform: (T) -> R)
            = Matrix(height, width, list.map { it.map(transform).toMutableList() }.toMutableList())
    fun <R> mapIndices(transform: (Int, Int) -> R) = Matrix(height, width,
        list.mapIndexed { i, r -> r.mapIndexed { j, e -> transform(i, j) }.toMutableList() }.toMutableList())

    fun toList(): List<List<T>> = list
    val heights get() = 0 until height
    val widths get() = 0 until width
}