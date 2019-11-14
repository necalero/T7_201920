package model.data_structures.Grafos;

public class Arco {

	 private Vertice origen;
     private Vertice destino;
     private double peso;

     public Arco(Vertice pOrigen, Vertice pDestino, double pPeso) {
         origen = pOrigen;
         destino = pDestino;
         peso = pPeso;
     }
     
     public double darPeso()
     {
    	 return peso;
     }
     public void setPeso(double pPeso)
     {
    	 peso = pPeso;
     }
     public Vertice darDestino()
     {
    	 return destino;
     }
}
