package freshStart

import freshStart.errors.InvalidQuantityForReservation

data class Quantity(val value: Int) {

    companion object {
        fun create(value: Int): Result<Quantity> {
            if ((value <= 0))
                return Result.failure(InvalidQuantityForReservation())
            return Result.success(Quantity(value))
        }
    }

    override operator fun equals(other: Any?): Boolean {
        if (other is Int)
            return other.toInt() == value
        if (other is Quantity)
            return (other.value == this.value)
        //    return (other === this)  // ne fonctionne pas!  parce que comme on est en train de redefinir l'égalité,
        //  l'égalité 'par défaut' des data class telle que fabriquée par le compilateur ne fonctionne plus.
        return false
    }

    operator fun plus(quantity: Quantity): Quantity = Quantity(this.value + quantity.value)

    operator fun compareTo(q1: Quantity): Int {
        return this.value - q1.value
    }
}
