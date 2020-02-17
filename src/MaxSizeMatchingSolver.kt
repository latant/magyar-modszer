
class MaxSizeMatchingSolver(val e: Matrix<Boolean>) {

    class AlternatingRoute(val a: A, val b: B, val next: AlternatingRoute? = null) {
        fun shift() {
            a.pair?.let { a.neighbours += it }
            a.neighbours -= b
            a.pair = b
            b.pair?.let { b.neighbours += it }
            b.neighbours -= a
            b.pair = a
            next?.shift()
        }
    }

    class B(val number: Int, val neighbours: MutableSet<A> = mutableSetOf(), var pair: A? = null)
    class A(val number: Int, val neighbours: MutableSet<B> = mutableSetOf(), var pair: B? = null) {
        fun findAlternatingRoute(exploredAs: Set<A> = setOf(this), exploredBs: Set<B> = setOf()): AlternatingRoute? {
            val uncheckedBs = (neighbours - exploredBs)
            uncheckedBs.firstOrNull { it.pair == null }?.let { return AlternatingRoute(this, it) }
            uncheckedBs.forEach { b ->
                b.pair!!.findAlternatingRoute(exploredAs + b.pair!!, exploredBs + b)
                    ?.let { return AlternatingRoute(this, b, it) }
            }
            return null
        }
        fun exploreBsOnAlternatingRoutes(exploredAs: Set<A> = setOf(this), exploredBs: Set<B> = setOf()): Set<B> {
            return (neighbours - exploredBs).flatMapTo(mutableSetOf()) { b ->
                b.pair!!.exploreBsOnAlternatingRoutes(exploredAs + b.pair!!, exploredBs + b) + b
            }
        }
    }

    val a = e.heights.map { A(it) }
    val b = e.widths.map { B(it) }

    fun solve() {
        a.forEachIndexed { i, ai ->
            b.forEachIndexed { j, bj ->
                if (e[i, j]) {
                    ai.neighbours += bj
                    bj.neighbours += ai
                }
            }
        }
        while (true) {
            a.asSequence().filter { it.pair == null }.map { it.findAlternatingRoute() }.filterNotNull()
                .firstOrNull()?.shift() ?: break
        }
    }

}