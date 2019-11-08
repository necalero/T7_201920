package model.data_structures.Grafos;

public class GrafoNoDirigido<V, K>
{
	/**
	 * Crea un grafo No dirigido de tama�o V v�rtices y sin arcos
	 * @param V
	 */
	public GrafoNoDirigido(int V)
	{
		
	}
	
	/**
	 * N�mero de v�rtices
	 * @return
	 */
	public int V()
	{
		
	}
	
	/**
	 * N�mero de arcos. Cada arco No dirigido debe contarse una �nica vez.
	 * @return
	 */
	public int E()
	{
		
	}
	
	/**
	 * Adiciona el arco No dirigido entre el v�rtice IdVertexIni y el v�rtice IdVertexFin.
	 * El arco tiene el costo cost
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	public void addEdge(K idVertexIni, K idVertexFin, double cost)
	{
		
	}
	
	/**
	 * Obtener la informaci�n de un v�rtice. Si el v�rtice no existe retorna null
	 * @param idVertex
	 * @return
	 */
	public V getInfoVertex(K idVertex)
	{
		
	}
	
	/**
	 * Modificar la informaci�n del v�rtice idVertex
	 * @param idVertex
	 * @param infoVertex
	 */
	public void setInfoVertex(K idVertex, V infoVertex)
	{
		
	}
	
	/**
	 * Obtener el costo de un arco, si el arco no existe, retorna -1
	 * @param idVertexIni
	 * @param idVertexFin
	 * @return
	 */
	public double getCostArc(K idVertexIni, K idVertexFin)
	{
		
	}
	
	/**
	 * Modificar el costo del arco entre los v�rtices idVertexIni e idVertexFin
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	public void setCostArc(K idVertexIni, K idVertexFin, double cost)
	{
		
	}
	
	/**
	 * Adiciona un v�rtice con un Id �nico. El v�rtice tiene la informaci�n InfoVertex.
	 * @param idVertex
	 * @param infoVertex
	 */
	public void addVertex(K idVertex, V infoVertex)
	{
		
	}
	
	/**
	 * Retorna los identificadores de los v�rtices adyacentes a idVertex.
	 * @param idVertex
	 * @return
	 */
	public Iterable <K> adj (K idVertex)
	{
		
	}
	
	/**
	 * Desmarca todos los v�rtices del grafo
	 */
	public void uncheck()
	{
		
	}
	
	/**
	 * Ejecuta la b�squeda de profundidad sobre el grafo con el v�rtice s como origen.
	 * Los v�rtices resultado de la b�squeda quedan marcados y deben tener informaci�n 
	 * que pertenecen a una misma componente conectada.
	 * @param s
	 */
	public void dfs(K s)
	{
		
	}
	
	/**
	 * Obtiene la cantidad de componentes conectados del grafo. Cada v�rtice debe quedar
	 * marcado y debe reconocer a cu�l componente conectada pertenece. En caso de que el
	 * grafo est� vac�o, retorna 0.
	 * @return
	 */
	public int cc()
	{
		
	}
	
	/**
	 * Obtiene los v�rtices alcanzados a partir del v�rtice idVertex despu�s de la ejecuci�n
	 * de los metodos dfs(K) y cc().
	 * @param idVertex
	 * @return
	 */
	public Iterable<K> getCC(K idVertex)
	{
		
	}
	
}
