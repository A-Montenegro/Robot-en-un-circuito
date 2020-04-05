/**
 * Clase que contiene los atributos de un Nodo, y el m�todo que permite el c�lculo de distancias
* @author Alberto Mart�nez Montenegro
*/
public class Nodo {
	private int id_nodo;
	private int fila;
	private int columna;
	private int energia_consumida;
	
	/**
	 * Constructor de la clase Nodo
	 * @param id_nodo	 		-El identificador �nico del nodo, se corresponde con su posici�n en el grafo.
	 * @param fila    			-La fila en d�nde se encuentra el nodo en la matriz.
	 * @param columna 			-La columna en d�nde se encuentra el nodo en la matriz.
	 * @param energ�a_consumida -Un n�mero entero que simboliza la energ�a que consume el robot al pasar por ese nodo del grafo.
	*/
	public Nodo(int id_nodo,int fila,int columna,int energia_consumida) {
		this.id_nodo=id_nodo;
		this.fila=fila;
		this.columna=columna;
		this.energia_consumida=energia_consumida;
	}
	
	/**
	 *  M�todo que devuelve el identicador �nico del nodo
	*/
	public int get_id_nodo() {
		return id_nodo;
	}
	
	/**
	 *  M�todo que devuelve el la fila en la que est� del nodo
	*/
	public int get_fila() {
		return fila;
	}
	
	/**
	 *  M�todo que devuelve el la columna en la que est� del nodo
	*/
	public int get_columna() {
		return columna;
	}
	
	/**
	 *  M�todo que devuelve el la energ�a consumida al pasar por el nodo
	*/
	public int get_energia_consumida() {
		return energia_consumida;
	}
	
	/**
	 *  El m�todo distancia devuelve un n�mero entero que simboliza la energ�a consumida al desplazarse desde el propio nodo hasta la nodo2.
	 *  Devuelve -1 si la distancia es infinita.
	 *  @param  	nodo2	-Un objeto tipo Nodo, el propio nodo es el nodo de partida y el que se pasa como par�metro el de llegada.
	 *  @return  			-un n�mero entero que simboliza la energ�a consumida al desplazarse desde la casilla1 a la casilla2, si es -1, la distancia es infinita.
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
