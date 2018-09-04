# PruebaAndroidRappi

La aplicacion esta compuesta por las siguientes capas:
model
dao
services
repository
viewmodel
y las vistas(Activities)

La capa model contiene las clases que representan información de las películas 
tenemos las clase Movie la cual almacena la informacion de las peliculas cuando se consume la appi online
y la clase movieDBOffline la cual almacena la informacion de las peliculas offline(Cache)
Dentro de esta capa encontramos otras clases que son necesaria para poder almacenar informacion adiciona que nos arroja la api (themovieDdb), tales como
Genres, Dates, Images, ProductionCompany, ProductionCountry, SpokenLanguages

La capa dao contiene las clases que nos sirven de interfaz entre la vista y los datos de las peliculas que se almacenan de manera offline(cache)
Esta capa contien las clases:
MovieDBOfflineDAO: la cual represtan la intefaz de las consultas que vamos a realizar en nuestra base de datos local
y la calse abstracta MovieDBRoomDatabase: la cual es la implemetancion y uso de la libreria Room que nos ayuda con todas las tareas de sqllite

La capa Services contiene las clases de implementacion del consumo de la api themoviedb,  encargadas de proveernos los servcios de consultas de la informacion de las peliculas.
Esta capa cotiene las clases: MovieDBDataServices la cual es una interfaz con los metodos que consultan la informacion de las peliculas
y la clases RetrofitClienInstance: la cual nos provee toda la implementacion de Retrofit para para realizar las peticiciones a la api de consulta de las peliculas

La capa repository contiene las clases que nos sirven de puente entre el dao y la capa viewmodel de la aplicación
el objetivo de tener la clase MovieDBRepository es tener una clases que nos provea la inforamcion de multiples fiuentes de datos (offilne y online)
La capa viewmodel tiene las clases encargadas deproveer los datos de la consultas de la inforamcion de las peliculas offline a las vistas(Activities)
___
1. En qué consiste el principio de responsabilidad única? Cuál es su propósito? 
El principio de responsabilidad unica nos indica que cada clase o componente de nuestra aplicacion debe tener responsabilidad sobre una sola parte o funcionalidad de nuestro sistema o aplicacio
2. Qué características tiene, según su opinión, un “buen” código o código limpio? 
Un codigo limpio debe seguir buenas pactricas de desarrollo dentro de las cuales estan las siguentes:
Desarrollar clases y metodos que cumplan con una unica funciona
nombrar las variables y metodos de acuerdo a la funcionalidad que cumplan
realizar pruebas unitarias a cada metodo que se desarrolle
no tener metodos ni clases demasiado grande que nos dificulten realizar refactor
desarrollar nuestro código de modo que sea autocomentado (selfcommented)
___
#Futuras mejoras:

Implementar un Android Service para descargar la informacion y las imagenes de las peliculas para las consultas offline(Cache) 

Implementar paginacion en la consulta de las peliculas 

Realizar pruebas unitarias
