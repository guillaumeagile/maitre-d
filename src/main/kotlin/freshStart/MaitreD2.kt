package freshStart

import freshStart.commands.ICommand
import freshStart.commands.ReservationCommand
import freshStart.events.Event
import freshStart.events.ReservationIsConfirmedOnSharedTable
import freshStart.events.ReservationIsDeclinedOnSharedTable
import freshStart.events.ReservationIsProposedOnSharedTable

class MaitreD2(val sharedTableInInitialState: SharedTable) {

    var events: Collection<Event> =
        setOf<Event>() // les archiver dans un autre système pour des stats => reporting..., pour la compta... (Read ONLY)
    // l'Audit est gratuit

    fun handle(command: ICommand) {

        val sharedTableCurrentState = sharedTableInInitialState.replayOn(listEvents = events)
        val reservationCommand = (command as ReservationCommand)
        if (sharedTableCurrentState.canIReserve(
                date = reservationCommand.wishedDate,
                qtte = Quantity(reservationCommand.guestsCount)
            )
        ) {
            events = events.plus(ReservationIsProposedOnSharedTable(reservationNumber = 1))
            events = events.plus(
                ReservationIsConfirmedOnSharedTable(
                    reservationNumber = 1,
                    date = command.wishedDate,
                    qtte = Quantity(command.guestsCount)
                )

            )
        } else {
            events = events.plus(ReservationIsDeclinedOnSharedTable(reservationNumber = 1))
        }
    }
}
