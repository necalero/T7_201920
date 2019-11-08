package model.logic;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;



import model.data_structures.Geometry;
import model.data_structures.HashTableLinearProbing;
import model.data_structures.MaxPQ;
import model.data_structures.Nodo;
import model.data_structures.NodoRedVial;
import model.data_structures.Properties;
import model.data_structures.UBERTrip;
import model.data_structures.ZonaUber;
import model.data_structures.BSTRojoNegro.ArbolesRYN;
import model.data_structures.BSTRojoNegro.NodoBSTRojoNegro;



/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private HashTableLinearProbing datosHora;
	private HashTableLinearProbing datosDia;
	private HashTableLinearProbing datosMes;
	private HashTableLinearProbing datosRedVial;
	private ArbolesRYN zonasUberRN;
	private HashTableLinearProbing zonasUberHash;



	//-----------------------------------------------------------------

	/**
	 * Constructor del modelo del mundo
	 */
	public MVCModelo()
	{
	}



	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 * @throws IOException 
	 * @throws NoExisteException 
	 */
	public int[] cargar() throws IOException, NoExisteException
	{	


		try {
			/**
			 * Se crea un arreglo de enteros para reportar 
			 * 1. El número de viajes que se cargaron de cada archivo CSV
			 * 2. El número de zonas que se cargaron del archivo JSON
			 * 3. El número de nodos (esquinas) de la malla vial del archivo TXT
			 */
			int[] resultados = new int[3];

			//Se utilizan metodos auxiliares para cargar los archivos de cada tipo
			int cantidadViajesCargados = 0; 
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-1-WeeklyAggregate.csv",1,"weekly");
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-2-WeeklyAggregate.csv",2,"weekly");
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-1-All-MonthlyAggregate.csv",1,"monthly");
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-2-All-MonthlyAggregate.csv",2,"monthly");
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-1-All-HourlyAggregate.csv",1,"hourly");
			cantidadViajesCargados += cargarCSV("./data/bogota-cadastral-2018-2-All-HourlyAggregate.csv",2,"hourly");

			int cantidadZonasCargadas = cargarJSON("./data/bogota-cadastral.json");
			cantidadZonasCargadas = cargarJSON("./data/bogota-cadastral.json");
			int cantidadNodosCargados = cargarTXT("./data/Nodes_of_red_vial-wgs84_shp.txt");

			resultados[0] = cantidadViajesCargados;
			resultados[1] = cantidadZonasCargadas;
			resultados[2] = cantidadNodosCargados;

			return resultados;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No sirvió :(");
			e.printStackTrace();

		}
		return null;


	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int cargarCSV(String ruta, int trimestre, String tipoDate) throws IOException
	{
		int cantidadViajesCargados = 0;
		CSVReader reader;
		File arc = new File(ruta);
		FileReader fr = new FileReader(arc);
		reader = new CSVReader(fr);
		reader.readNext();
		String [] nextLine=reader.readNext();

		while (nextLine != null) 
		{
			String key = trimestre+"-"+nextLine[0]+"-"+nextLine[1];
			if(tipoDate.equals("weekly"))
			{
				datosDia.put(key, (Comparable) new Nodo(new UBERTrip(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4],nextLine[5], nextLine[6], tipoDate)));
			}
			else if(tipoDate.equals("hourly"))
			{
				datosHora.put(key, (Comparable) new Nodo(new UBERTrip(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4],nextLine[5], nextLine[6], tipoDate)));
			}
			else if(tipoDate.equals("monthly"))
			{
				datosDia.put(key, (Comparable) new Nodo(new UBERTrip(nextLine[0],nextLine[1],nextLine[2],nextLine[3],nextLine[4],nextLine[5], nextLine[6], tipoDate)));
			}

			nextLine = reader.readNext();
			cantidadViajesCargados++;
		}
		reader.close();


		return cantidadViajesCargados;
	}

	@SuppressWarnings("unchecked")
	public int cargarJSON(String ruta) throws FileNotFoundException
	{
		JsonReader reader;
		try
		{
			Gson gson = new Gson();
			reader = new JsonReader(new FileReader(ruta));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonObject jobj = elem.getAsJsonObject();
			JsonArray todas = jobj.getAsJsonArray("features");
			zonasUberRN = new ArbolesRYN<>();
			//zonasUber = new ZonaUber[todas.size()];
			for(int i = 0; i< todas.size(); i++)
			{
				JsonObject actual = todas.get(i).getAsJsonObject();
				String typeFeature = actual.getAsJsonObject("type").getAsString();
				JsonObject geometry = actual.getAsJsonObject("geometry");
				String typeGeom = geometry.getAsJsonObject("type").getAsString();
				JsonArray coordinatesJSON = geometry.getAsJsonArray("coordinates");
				String rawNumbers = coordinatesJSON.getAsString().replace("[", "").replace("]", "");
				String[] coordinates = rawNumbers.split(",");
				Geometry nuevaGeometry = new Geometry(typeGeom, coordinates);
				JsonObject propertiesJSON = actual.getAsJsonObject("properties");
				Properties properties = gson.fromJson(propertiesJSON, Properties.class);
				String key = actual.getAsJsonObject("properties").getAsJsonObject("DISPLAY_NAME").getAsString();
				zonasUberHash.put(key, (Comparable) new ZonaUber(typeFeature, nuevaGeometry, properties));
				zonasUberRN.put(key, new ZonaUber(typeFeature, nuevaGeometry, properties));


			}
			return zonasUberRN.size();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int cargarTXT(String ruta) throws IOException 
	{
		FileReader fr = new FileReader(ruta);
		BufferedReader br = new BufferedReader(fr);
		String linea= br.readLine();
		int contador = 0;
		while(linea!=null)
		{
			String[] partes = linea.split(",");
			NodoRedVial nuevo = new NodoRedVial(partes[0], partes[1], partes[2]);
			Nodo nuevoNodo = new Nodo<NodoRedVial>(nuevo);
			String Key = partes[0];
			datosRedVial.put(Key, (Comparable) nuevoNodo);
			contador++;
			linea = br.readLine();
		}
		br.close();
		return contador;
	}

	/**
	 * Obtener las N letras más frecuentes por las que comienza el nombre de una zona
	 */
	@SuppressWarnings("unchecked")

	public String reqFunc1A(short N)
	{
		Nodo[] nodosZonas = zonasUberHash.darData();
		MaxPQ recurrenciaLetras = new MaxPQ<>();
		for(Nodo nodo: nodosZonas)
		{
			ZonaUber actual = (ZonaUber) nodo.darItem();
			char letraPrincipal = actual.darProperties().darScanombre().charAt(0);
			if(recurrenciaLetras.contains(letraPrincipal))
			{
				recurrenciaLetras.setPriority((int)(recurrenciaLetras.darPrioridad(letraPrincipal))+1, letraPrincipal);
			}
			else
			{
				recurrenciaLetras.insert(1, letraPrincipal);
			}
		}

		String[] masFrecuentes = recurrenciaLetras.maxValues(N);
		MaxPQ zonasLetras = new MaxPQ<>(masFrecuentes.length);

		for(Nodo nodoActual: nodosZonas)
		{
			ZonaUber actual = (ZonaUber) nodoActual.darItem();
			String nomActual = actual.darProperties().darScanombre();
			for(String letra: masFrecuentes)
			{
				if(((String)(nomActual.charAt(0)+"")).equals(letra))
				{
					zonasLetras.insert(nomActual.charAt(0), nomActual);
				}
			}
		}

		String respuesta = "Las "+N+" letras mas recurrentes son: ";
		for(int i = 0; i<masFrecuentes.length;i++)
		{
			respuesta+=" "+masFrecuentes[i];
			respuesta+=" Las zonas que empiezan por "+masFrecuentes[i]+" son:";
			for(int j = 0; j<zonasLetras.size();j++)
			{
				String x = ((String) zonasLetras.darValues()[j]).charAt(0)+"";
				if(x.equals(masFrecuentes[i]))
				{
					respuesta+=" "+zonasLetras.darValues()[j];
				}
			}


		}

		return respuesta;
	}
	/**
	 * Buscar los nodos que delimitan las zonas por Localización Geográfica (latitud, longitud)
	 */
	public String reqFunc2A(double pLatitud, double pLongitud)
	{
		DecimalFormat df = new DecimalFormat("#.###");
		double latShort = Double.parseDouble(df.format(pLatitud));
		double longShort = Double.parseDouble(df.format(pLongitud));
		Iterable<String> keys = zonasUberRN.keys();
		ArbolesRYN resultados = new ArbolesRYN<>();
		for(String i: keys)
		{
			NodoBSTRojoNegro actualNodo = (NodoBSTRojoNegro) zonasUberRN.darValor(i);
			ZonaUber actual = (ZonaUber) actualNodo.darValue();

			for(int j = 0; j<actual.darGeometry().darCoordinatesSize(); j++)
			{

				double latActualShort = Double.parseDouble(df.format(actual.darGeometry().darCoordinates((short)j)[0]));
				double longActualShort = Double.parseDouble(df.format(actual.darGeometry().darCoordinates((short)j)[1]));
				if(latActualShort==latShort||longActualShort==longShort)
				{
					resultados.put(latActualShort, actualNodo);
				}
			}

		}
		int numeroNodos = resultados.size();
		String respuesta= "El numero de nodos retornados es "+numeroNodos;
		Iterable<NodoBSTRojoNegro> keysR = resultados.keys();
		for(NodoBSTRojoNegro nodo: keysR)
		{
			ZonaUber actual = (ZonaUber) nodo.darValue();
			respuesta+=" Latitud: "+actual.darGeometry().darCoordinates((short) 0)[0]+", Longitud: "+actual.darGeometry().darCoordinates((short) 0)[1];
			respuesta+=" Nombre de la zona a la que pertenece: " + actual.darProperties().darScanombre()+". ";
		}
		return respuesta;
	}
	/**
	 * Buscar los tiempos promedio de viaje que están en un rango y que son del primer trimestre del 2018.
	 */
	public String reqFunc3A(double pMin, double pMax, short N)
	{
		return null;
	}

	/**
	 * Buscar los N zonas que están más al norte.
	 */
	public String reqFunc1B(short N)
	{
		String ayuda = null;
		Nodo[] nodosZonas = zonasUberHash.darData();
		MaxPQ ZonasNorte = new MaxPQ<>();
		for(int i =0; i< nodosZonas.length; i++)
		{
			Nodo nodo =nodosZonas[i];
			ZonaUber actual = (ZonaUber) nodo.darItem();
			ZonaUber siguiente = (ZonaUber) nodo.darSiguiente().darItem();
			if(actual.darGeometry().darCoordinates(N).length<siguiente.darGeometry().darCoordinates(N).length)
			{
				ZonasNorte.insert(1, siguiente);
				if(actual.darGeometry().darCoordinates(N).length>siguiente.darGeometry().darCoordinates(N).length)	
				{
					ZonasNorte.insert(1, actual);
				}
					if(ZonasNorte.contains(actual))
					{
						ZonasNorte.setPriority((int)(ZonasNorte.darPrioridad(actual))+1, actual);
					}
					else
					{
						ZonasNorte.insert(1, actual);
					}
			}
		}

		String[] masNorte = ZonasNorte.maxValues(N);
		MaxPQ zonasN = new MaxPQ<>(masNorte.length);
		for(Nodo nodoActual: nodosZonas)
		{
			ZonaUber actual = (ZonaUber) nodoActual.darItem();
			String nomActual = actual.darProperties().darScanombre();
			String cordActual = actual.darGeometry().darCoordinates(N).toString();
			for(String corde: masNorte)
			{
				if(((String)(nomActual.charAt(0)+"")).equals(corde))
				{
					ayuda = nomActual+ "," +cordActual; 
					zonasN.insert(ayuda.charAt(0), ayuda);
				}
			}
		}

		String partes[] = ayuda.split(",");
		String nombre = partes[0];
		String cordenada = partes[1];
		String respuesta = "Las "+N+" zonas más al norte son: ";
		for(int i = 0; i<masNorte.length;i++)
		{
			respuesta+=" "+masNorte[i];
			respuesta+=" Las zonas del norte "+" son:"+nombre+","+ "y sus cordenadas son:"+ cordenada;
			for(int j = 0; j<zonasN.size();j++)
			{
				String x = ((String) zonasN.darValues()[j]).charAt(0)+"";
				if(x.equals(masNorte[i]))
				{
				masNorte[i] = respuesta;
				}
			}
		}
		String respuestaFinal="";
		for(String corde: masNorte)
		{
			respuestaFinal+=corde+" ";
		}
		 

		return respuestaFinal;
	}
	/**
	 * Buscar nodos de la malla vial por Localización Geográfica (latitud, longitud).
	 */
	public String reqFunc2B(double pLatitud, double pLongitud)
	{
		int n = 0;
		String[] resultados = new String[n];
		DecimalFormat df = new DecimalFormat("#.###");
		double latShort = Double.parseDouble(df.format(pLatitud));
		double longShort = Double.parseDouble(df.format(pLongitud));
		Iterable<String> keys = zonasUberRN.keys();
		ArbolesRYN arbol = new ArbolesRYN<>();
		for(String i: keys)
		{
			NodoBSTRojoNegro actualNodo = (NodoBSTRojoNegro) zonasUberRN.darValor(i);
			ZonaUber actual = (ZonaUber) actualNodo.darValue();
			for(int j = 0; j<actual.darGeometry().darCoordinatesSize(); j++)
			{

				double latActualShort = Double.parseDouble(df.format(actual.darGeometry().darCoordinates((short)j)[0]));
				double longActualShort = Double.parseDouble(df.format(actual.darGeometry().darCoordinates((short)j)[1]));
			
				if(latActualShort==latShort||longActualShort==longShort)
				{
					
					arbol.put(latActualShort, actualNodo);
					if(arbol.contains(i))
					{
						resultados[n] = i;
					}
				}
			}
		}
		n= arbol.height();
		String respuestaFinal = "";
		for(String resultadoIndividual : resultados)
		{
			respuestaFinal+=resultadoIndividual+" ";
		}
		return respuestaFinal;
	}
	/**
	 * Buscar los tiempos de espera que tienen una desviación estándar en un rango dado y que son del primer trimestre del 2018
	 */
	public String reqFunc3B(double pLimiteBajoDesviacionEstandar, double pLimiteAltoDesviacionEstandar)
	{
		return null;
	}

	/**
	 * Retornar todos los tiempos de viaje promedio que salen de una zona dada y a una hora dada
	 */
	public String reqFunc1C(int pIdZonaSalida, double pHora)
	{
		return null;
	}
	/**
	 * Retornar todos los tiempos de viaje que llegan de una zona dada y en un rango de horas
	 */
	public String reqFunc2C(int idZonaLlegada, double pHoraMin, double pHoraMax)
	{
		return null;
	}
	/**
	 * Obtener las N zonas priorizadas por la mayor cantidad de nodos que definen su frontera.
	 */
	public String reqFunc3C(short N)
	{
		return null;
	}
	/**
	 * Gráfica ASCII - Porcentaje de datos faltantes para el primer semestre 2018.
	 */
	public String reqFunc4C(int N)
	{
		return null;
	}




}