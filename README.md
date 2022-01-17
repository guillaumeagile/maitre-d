# Maître d' kata

## TODO
1. Prochaine étape remonter le reservation number dans les méthode reserve(...)
2. continuer en produisant ce numéro de reservation (à faire porter par un truc => SRP)
3. ce numéro de réservation doit probablement apparaitre dans les événements 
4. Passer en collections réellement immutable  https://www.baeldung.com/kotlin/immutable-collections


a été voté
option 1) nom du client <=> id du client et horodatage de la réservation 


Changer idreservation et faire porter cet id par customerName & reservationDate



## Énoncé
[Sujet original de Mark Seemann](https://blog.ploeh.dk/2020/01/27/the-maitre-d-kata/)

### Besoin 1 
Gérer les reservations d'un restaurant nommé "La boutique" qui n'a qu'une seule table de 12 places que les convives se partagent et qu'un seul service.
### Besoin 2
Gérer les réservations d'un restaurant nommé "Haute Cusine" qui a plusieurs tables privées (1 table par réservation) de différentes tailles.
### Besoin 3
Gérer les réservations d'un restaurant ayant plusieurs services. 
### Besoin 4
Gérer les réservations d'un restaurant ayant des tables qu'il est possible de combiner (2 tables de 2 = 1 table de 4).

### Cumul des besoins
Les besoins décrits ci-dessus sont à adresser dans l'ordre et s'accumulent. 
En fin d'exercice, on doit être en mesure de gérer les réservations de restaurants ayant plusieurs services et plusieurs tables (partagées ou privées) qu'il est possible de combiner entre elles. 

## Particularité
L'implémentation proposée dans ce repository s'appuie sur l'Event Sourcing pour la persistence des données.
Pour les besoins de l'exercice, les différents évènements sont stockés sous forme de DTOs en mémoire.




