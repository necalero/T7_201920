package view;

import model.logic.MVCModelo;

public class MVCView 
{
	    /**
	     * Metodo constructor
	     */
	    public MVCView()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar viajes");
			System.out.println("2. Obtener las N letras más frecuentes por las que comienza el nombre de una zona");
			System.out.println("3.  Buscar los nodos que delimitan las zonas por Localización Geográfica (latitud, longitud)");
			System.out.println("4.  Buscar los tiempos promedio de viaje que están en un rango y que son del primer trimestre del 2018.");
			System.out.println("5. Buscar los N zonas que están más al norte.");
			System.out.println("6.  Busca entre un rango de horas y retorna los tiempos de los viajes de la zona.");
			System.out.println("7. Buscar los tiempos de espera que tienen una desviación estándar en un rango dado y que son del primer trimestre del 2018");
			System.out.println("8. Retornar todos los tiempos de viaje promedio que salen de una zona dada y a una hora dada");
			System.out.println("9.   Retornar todos los tiempos de viaje que llegan de una zona dada y en un rango de horas");
			System.out.println("10.  Obtener las N zonas priorizadas por la mayor cantidad de nodos que definen su frontera.");
			System.out.println("11.  Gráfica ASCII - Porcentaje de datos faltantes para el primer semestre 2018.");
			
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return:");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(MVCModelo modelo)
		{
			// TODO implementar
		}
}
