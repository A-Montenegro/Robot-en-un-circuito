/**
 * Clase que contiene los atributos de un Nodo, y el método que permite el cálculo de distancias
* @author Alberto Martínez Montenegro
*/
public class Nodo {
	private int id_nodo;
	private int fila;
	private int columna;
	private int energia_consumida;
	
	/**
	 * Constructor de la clase Nodo
	 * @param id_nodo	 		-El identificador único del nodo, se corresponde con su posición en el grafo.
	 * @param fila    			-La fila en dónde se encuentra el nodo en la matriz.
	 * @param columna 			-La columna en dónde se encuentra el nodo en la matriz.
	 * @param energía_consumida -Un número entero que simboliza la energía que consume el robot al pasar por ese nodo del grafo.
	*/
	public Nodo(int id_nodo,int fila,int columna,int energia_consumida) {
		this.id_nodo=id_nodo;
		this.fila=fila;
		this.columna=columna;
		this.energia_consumida=energia_consumida;
	}
	
	/**
	 *  Método que devuelve el identicador único del nodo
	*/
	public int get_id_nodo() {
		return id_nodo;
	}
	
	/**
	 *  Método que devuelve el la fila en la que está del nodo
	*/
	public int get_fila() {
		return fila;
	}
	
	/**
	 *  Método que devuelve el la columna en la que está del nodo
	*/
	public int get_columna() {
		return columna;
	}
	
	/**
	 *  Método que devuelve el la energía consumida al pasar por el nodo
	*/
	public int get_energia_consumida() {
		return energia_consumida;
	}
	
	/**
	 *  El método distancia devuelve un número entero que simboliza la energía consumida al desplazarse desde el propio nodo hasta la nodo2.
	 *  Devuelve -1 si la distancia es infinita.
	 *  @param  	nodo2	-Un objeto tipo Nodo, el propio nodo es el nodo de partida y el que se pasa como parámetro el de llegada.
	 *  @return  			-un número entero que simboliza la energía consumida al desplazarse desde la casilla1 a la casilla2, si es -1, la distancia es infinita.
	*/
	public int distancia(Nodo nodo2) {
		if (fila==nodo2.get_fila() && columna==nodo2.get_columna()) return 0;
		if(fila < nodo2.get_fila()+2 && fila > nodo2.get_fila()-2 && columna < nodo2.get_columna()+2 && columna > nodo2.get_columna()-2) {
			return nodo2.get_energia_consumida();
		}
		else {
			return -1; 
		}
	}
	
	
}
