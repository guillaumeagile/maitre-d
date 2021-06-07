package freshStart

 data class Quantity(val value_: Int) {

    companion object {
        fun create(value_ : Int ) : Result<Quantity> {
            if ((value_ <=0) )
                return Result.failure(InvalidQuantityForReservation())
            return Result.success(Quantity(value_))
        }
    }

    override operator  fun equals(other: Any?): Boolean {
       if (other is Int)
            return other.toInt() == value_
        if (other is Quantity)
            return (other.value_ === this.value_)
            //    return (other === this)  // ne fonctionne pas!  parce que comme on est en train de redefinir l'égalité,
            //  l'égalité 'par défaut' des data class telle que fabriquée par le compilateur ne fonctionne plus.
        return false
    }

/*
     override fun toString(): String {
         val tostr = super.toString()
         return "hello{$value_}::{$tostr}"
         //return super.toString()
     }*/
}
