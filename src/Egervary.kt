class Egervary(val w: Qube<Int>) {

    val ca = w.heights.map { i -> w.widths.maxBy { j -> w[i, j] }!! }.toMutableList()
    val cb = w.widths.map { j -> w.heights.maxBy { i -> w[i, j] }!! }.toMutableList()
    lateinit var maxMatch: MaxSizeMatchingSolver

    fun solve() {
        maxMatch = MaxSizeMatchingSolver(w.mapIndices { i, j -> ca[i] + cb[j] == w[i, j] })
        maxMatch.solve()
        if (maxMatch.a.all { it.pair != null }) return
        val b1 = maxMatch.b.filter { it.pair == null }.map { it.number }.toSet()
        val a1 = maxMatch.a.filter { it.pair == null }.map { it.number }.toSet()
        val b2 = maxMatch.a.filterIndexed { i, _ -> i in a1 }.flatMap { it.exploreBsOnAlternatingRoutes() }
            .map { it.number }.toSet()
        val a2 = maxMatch.b.filterIndexed { j, _ -> j in b2 }.map { it.pair!!.number }.toSet()
        val b3 = maxMatch.b.indices.toSet() - b1 - b2
        val a1ua2 = (a1 + a2)
        val b1ub3 = (b1 + b3)
        val d = a1ua2.maxBy { i -> b1ub3.maxBy { j -> ca[i] + cb[j] - w[i, j] }!! }!!
        a1ua2.forEach { i -> ca[i] -= d }
        b2.forEach { j -> cb[j] += d }
    }

}