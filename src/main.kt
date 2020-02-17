import kotlin.math.max

fun matrix(height: Int, width: Int, vararg e: Pair<Int, Int>) =
    Matrix(height, width) { _, _ -> false }.apply { e.forEach { (i, j) -> set(i, j, true) } }

fun matrix(height: Int, width: Int, vararg w: Int): Matrix<Int> {
    if (w.size != height * width) error("Number of elements must be equal width height * width")
    return Matrix(height, width) { i, j -> w[i * width + j] }
}

fun Matrix<Int>.toPrettyString() = toList().joinToString("\n") { it.joinToString(" ") }

fun testMaxSizeMatchingSolver() {
    val m = matrix(5, 5,
        0 to 0,
        0 to 2,
        1 to 1,
        1 to 2,
        2 to 1,
        2 to 3,
        3 to 2,
        3 to 3,
        3 to 4,
        4 to 3)
    println(m.map { if (it) 1 else 0 }.toPrettyString())
    MaxSizeMatchingSolver(m).solve()
    println(m.map { if (it) 1 else 0 }.toPrettyString())
}

fun testHunMethod() {
    val m = matrix(4, 5,
        3, 3, 0, 7, 5,
        5, 3, 5, 7, 6,
        1, 0, 3, 4, 0,
        5, 5, 6, 9, 9)
    println(m.toPrettyString())
    println()
    val solution = solve(m)
    val r = m.mapIndices { i, j -> if (solution.maxMatch.a[i].pair == solution.maxMatch.b[j]) m[i, j] else 0 }
    println(r.toPrettyString())
    println()
    println(solution.ca)
    println(solution.cb)
}

fun solve(w: Matrix<Int>): Egervary {
    val q = Qube(max(w.height, w.width)) { i, j -> if (i in w.heights && j in w.widths) w[i, j] else 0 }
    return Egervary(q).apply { solve() }
}

fun main() = testHunMethod()