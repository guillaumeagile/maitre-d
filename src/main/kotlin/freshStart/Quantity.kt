package freshStart

data class Quantity(val value_: Int) {

    companion object {
        fun create(value_ : Int ) : Result<Quantity> {
            if (value_ <=0)
                return Result.failure(InvalidQuantityForReservation())
            return Result.success(Quantity(value_))
        }
    }

    override fun equals(other: Any?): Boolean {
       if (other is Int)
            return other.toInt() == value_
        return false
    }
}
