package controller;

import java.util.Scanner;

import model.logic.MVCModelo;
import view.MVCView;

public class Controller {

	/* Instancia del Modelo*/
	private MVCModelo modelo;

	/* Instancia de la Vista*/
	private MVCView view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		modelo = new MVCModelo();
		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option)
			{
			case 1:
				view.printMessage("Se estan cargando los viajes: ");
				try
				{
					modelo.cargar();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				break;

			case 2:
				view.printMessage("Ingrese la cantidad de letras a consultar.");
				short N = (short) lector.nextInt();
				String respuesta1A = modelo.reqFunc1A(N);
				view.printMessage(respuesta1A);
				
				
			case 3:
				view.printMessage("Ingrese la latitud");
				double lat2a = lector.nextDouble();
				view.printMessage("Ingrese la longitud");
				double lon2a = lector.nextDouble();
				String respuesta2A = modelo.reqFunc2A(lat2a,lon2a);
				view.printMessage(respuesta2A);
				
			case 4:
				view.printMessage("Ingrese un limite bajo de segundos");
				double limMin3A = lector.nextDouble();
				view.printMessage("Ingrese un limite alto de segundos");
				double limMax3A = lector.nextDouble();
				view.printMessage("Ingrese la cantidad de resultados que desea mostrar");
				short N3A = (short) lector.nextDouble();
				String respuesta3A = modelo.reqFunc3A(limMin3A, limMax3A, N3A);
				view.printMessage(respuesta3A);
				
				
			case 5:
				view.printMessage("Ingrese la cantidad de resultados que desea mostrar");
				short N1B = (short) lector.nextDouble();
				String respuesta1B = modelo.reqFunc1B(N1B);
				view.printMessage(respuesta1B);
				
			case 6:
				view.printMessage("Ingrese la latitud");
				double lat2B = lector.nextDouble();
				view.printMessage("Ingrese la longitud");
				double lon2B = lector.nextDouble();
				String respuesta2B = modelo.reqFunc2B(lat2B,lon2B);
				view.printMessage(respuesta2B);
				
			case 7:
				view.printMessage("Ingrese el limite bajo de la desviacion estandar");
				double LimBajo3B = lector.nextInt();
				view.printMessage("Ingrese el limite alto de la desviacion estandar");
				double LimAlto3B = lector.nextDouble();
				String respuesta3B = modelo.reqFunc3B(LimBajo3B, LimAlto3B);
				view.printMessage(respuesta3B);
				
				
			case 8:
				view.printMessage("Ingrese el ID de la zona de salida");
				int ID1C = lector.nextInt();
				view.printMessage("Ingrese la hora");
				double hora1C = lector.nextDouble();
				String[] respuesta1C = modelo.reqFunc1C(ID1C, hora1C);
			
			case 9:
				view.printMessage("Ingrese el ID de la zona de llegada");
				int id2C = lector.nextInt();
				view.printMessage("Ingrese el limite bajo de la hora");
				double LimBajo2C = lector.nextInt();
				view.printMessage("Ingrese el limite alto de la hora");
				double LimAlto2C = lector.nextDouble();
				
				String[] respuesta2C = modelo.reqFunc2C(id2C, LimBajo2C, LimAlto2C);
				
			case 10:
				view.printMessage("Ingrese la cantidad de resultados que desea mostrar");
				short N3C = (short) lector.nextDouble();
				String[] respuesta3C = modelo.reqFunc3C(N3C);
			/**	
			case 11:
				view.printMessage("");
				short N = lector.nextInt();
				String[] respuesta4C = modelo.reqFunc4C(N);
			*/
			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
				
			}
		}
		lector.close();

	}	
}
