import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Clase que simboliza al robot del enunciado de la práctica, contiene los campos necesarios para representar el grafo
 * por el cual se desplazará el robot y los vectores especial y predecesor que contendrán los valores que definiran el recorrido al aplicar el algoritmo de Dijkstra.
 * También contiene como campo un Traza en la que se almacerán los datos de la traza, si se ha especificado con el parámtro correcto.
* @author Alberto Martínez Montenegro
*/
public class Robot {
	private int n;
	private int m;
	private ArrayList<Nodo> grafo;
	private Nodo nodo_inicio;
	private Nodo nodo_salida;
	private int[] especial;
	private int[] predecesor;	
	private Traza traza;
	private Monticulo_minimos monticulo_minimos;
	
	/**
	 *  Constructor de la clase Robot.
	 *  Transforma los datos de entrada en un grafo, e inicializa los vectores necesarios.
	 *  @param 	entrada	-Un ArrayList de Strings, que se usará para componer el grafo.
	*/
	public Robot(ArrayList<String> entrada) {
		n=Integer.parseInt(entrada.get(0));
		m=Integer.parseInt(entrada.get(1));
		grafo=new ArrayList<Nodo>();
		traza=new Traza();
		int contador=2;
		//Construimos el grafo
			for(int i=1;i<=n;i++) {
				for(int z=1;z<=m;z++) {
					switch (entrada.get(contador)) {
						case "S":
							nodo_salida=new Nodo(contador-1,i,z,0);
							grafo.add(nodo_salida);						
						break;
						case "R":
							nodo_inicio=new Nodo(contador-1,i,z,0);
							grafo.add(nodo_inicio);						
						break;
						case "O":
							grafo.add(new Nodo(contador-1,i,z,-1)); // Asigno el número entero -1 para marcar los nodos que están a una distancia infinita
						break;
						default:
							grafo.add(new Nodo(contador-1,i,z,Integer.parseInt(entrada.get(contador))));
						break;
					}
					contador++;
				}
			}
		especial=new int [grafo.size()];
		predecesor=new int [grafo.size()];	
	}
	
	/**
	 *  Método que devuelve un entero con el número de filas
	*/ 
	public int get_n() {
		return n;
	}
	
	/**
	 *  Método que devuelve un entero con el número de columnas
	*/ 
	public int get_m() {
		return n;
	}
	
	/**
	 *  Método que devuelve la estructura traza, si se llama sin haber llamado previamente al método dijkstra, devolverá la estructura vacía.
	*/ 
	public Traza get_traza(){
		return traza;
	}
	
	/**
	 *  El método dijkstra se encarga de rellenar los valores de los vectores especial[] y predecesor[]
	 *  Si así se especifica con los parámetros, también se rellenará los datos de la estructura traza.
	 *  @param 	con_traza	-Un boolean, que será true si queremos imprimir la traza, y false si no la queremos imprimir.
	*/
	public void dijkstra(boolean con_traza) {
		ArrayList<Nodo> c=new ArrayList<Nodo>();
		monticulo_minimos=new Monticulo_minimos(n*m,especial);
		int contador=0;
		int v=-1;
		boolean nodo_salida_eliminado=false;
		Iterator<Nodo> it=grafo.iterator();
		while(it.hasNext()) {
			c.add(it.next());//Copiamos en c todos los nodos del grafo, ya que vamos a eliminar nodos de c, nos conviene tener un backup de todos los nodos en grafo
		}
		for(int i=0;i<c.size();i++) {
			Nodo nodo_i=c.get(i);
			especial[i]=nodo_inicio.distancia(nodo_i);
			if(nodo_inicio.distancia(nodo_i)>=0 && nodo_inicio!=nodo_i) monticulo_minimos.insertar(nodo_i);
			if(nodo_inicio.get_id_nodo()==i+1) {
				predecesor[i]=-2;
			}
			else {
				predecesor[i]=nodo_inicio.get_id_nodo();
			}
		}
		c.set(nodo_inicio.get_id_nodo()-1,null);
		while(!nodo_salida_eliminado){
			if(con_traza) generar_traza(contador,v,c);
			if(monticulo_minimos.es_vacio()) {
				System.out.println("El problema propuesto con los datos del fichero de entrada no tiene solución.\r\nEl robot no puede desplazarse hasta la casilla 'S'");
				System.exit(0);
			}
			v=monticulo_minimos.obtener_cima().get_id_nodo();
			Nodo nodo_v=c.get(v-1);
			c.set(nodo_v.get_id_nodo()-1,null);//Hago esto porque eliminar el nodo con .remove() incrementaría el coste.
			for(int x=0;x<8;x++) { // Hacemos 8 iteraciones, ya que a lo sumo, cada nodo puede tener 8 adyacentes.
				int candidato=obtener_candidatos(v,x,c); //Obtenemos uno de los 8 adyacentes posibles
				if(candidato==0) continue; //Si el adyacentes es 0, quiere decir que ese adyacente no existe (esto se debe a que el nodo estará en una zona periférica), o bien que ha sido eliminado.
				Nodo nodo_w=c.get(candidato-1);
				int w=nodo_w.get_id_nodo()-1;
				int distancia_antigua=especial[w];
				int distancia=nodo_v.distancia(nodo_w);
				if(distancia>=0) {
					int nueva_distancia=especial[v-1]+distancia;
					if(distancia_antigua>nueva_distancia || distancia_antigua<0) {
						especial[w]=nueva_distancia;
						predecesor[w]=v;
						monticulo_minimos.insertar(nodo_w);
					}
				}
			}
			contador++;
			if(nodo_v==nodo_salida) {
				nodo_salida_eliminado=true;
				if(con_traza) generar_traza(contador,v,c);
			}
		}
	}
	
	/**
	 *  El método obtener_candidatos devuelve la id_nodo de uno de los nodos adyacentes al nodo que tenga por id_nodo el parámetro v.
	 *  Se escogerá el nodo adyacente a devolver según el valor del parámetro x,en caso de que el nodo no exisa o haya sido eliminado, se devolverá 0.
	 *  @param 	v	-id_nodo del nodo que queremos conocer sus adyacentes.
	 *  @param 	x	-número entero entre 0 y 7 que nos indica la posición del adyacente.(8 posibles posiciones, 8 nodos a lo sumo que rodean al nodo v).
	 *  @param  c   -un ArrayList de nodos, que usaremos para saber si el nodo en cuestión está eliminado o no.
	 *  @return 	-un número entero que simboliza la id_nodo del nodo del nodo adyacente solicitado.
	*/
	public int obtener_candidatos(int v, int x,ArrayList<Nodo> c) {
		switch (x){
		case 0:
			if((v<=m || (v-1)%m==0)  || c.get(v-m-1-1)==null) return 0; //Devolvemos 0 si el nodo no existe, o si ya ha sido eliminado de c.
			return v-m-1; //Devolvemos la posición del nodo superior izquierdo.
		case 1:
			if((v<=m) || c.get(v-m-1)==null) return 0;
			return v-m; //Devolvemos la posición del nodo superior.
		case 2:
			if((v<=m || (v)%m==0) || c.get(v-m+1-1)==null) return 0;
			return v-m+1; //Devolvemos la posición del nodo superior derecho.
		case 3:
			if(((v-1)%m==0) || c.get(v-1-1)==null) return 0;
			return v-1; //Devolvemos la posición del nodo izquierdo.
		case 4:
			if(((v)%m==0) || c.get(v+1-1)==null) return 0;
			return v+1; //Devolvemos la posición del nodo derecho.
		case 5:
			if(((v>(m*n)-m) || (v-1)%m==0) || c.get(v+m-1-1)==null) return 0;
			return v+m-1; //Devolvemos la posición del nodo inferior izquierdo.
		case 6:
			if(((v>(m*n)-m)) || c.get(v+m-1)==null) return 0;
			return v+m; //Devolvemos la posición del nodo inferior.
		case 7:
			if(((v>(m*n)-m)  || (v)%m==0) || c.get(v+m+1-1)==null) return 0;
			return v+m+1;	//Devolvemos la posición del nodo inferior derecho.	
		default:
			return 0;
		}
	}
	
	/**
	 *  El método generar_traza va rellenando la estructura traza con los valores correspondientes.
	 *  @param 	contador	-Un entero con el que llevamos la cuenta de las iteraciones del bucle while del método dijkstra
	 *  @param 	v	        -Un entero que simboliza la posición del nodo escogido en esa iteración.
	 *  @param  grafo_c     -Un ArrayList de nodos que contiene el grafo c entero.
	*/
	public void generar_traza(int contador,int v,ArrayList<Nodo> grafo_c) {
			traza.add_paso(contador);
			traza.add_v(v);
			int[] c=new int[grafo_c.size()];
			for(int i=0;i<grafo_c.size();i++) {
				if (grafo_c.get(i)==null) continue;
				c[i]=grafo_c.get(i).get_id_nodo();
			}
			traza.add_c(c);
			traza.add_especial(especial,v);
			traza.add_predecesor(predecesor,v);
	}
	
	/**
	 *  El método recorrido devuelve una cadena de texto con la solución al problema.
	 *  Debe ser llamado tras haber llamado previamente al método dijkstra.
	 *  @return 	-Un String que contiene el texto con la solución al problema.
	 *  @pre 		-Debe ser llamado tras haber llamado previamente al método dijkstra.
	*/ 
	public String recorrido(){
		Stack<String> cadena_parcial=new Stack<String>();
		int id_nodo_predecesor=nodo_salida.get_id_nodo();
		cadena_parcial.push(",S["+grafo.get(id_nodo_predecesor-1).get_fila()+","+grafo.get(id_nodo_predecesor-1).get_columna()+"]");
		id_nodo_predecesor=predecesor[id_nodo_predecesor-1];
		while(id_nodo_predecesor!=nodo_inicio.get_id_nodo()) {
			cadena_parcial.push(",["+grafo.get(id_nodo_predecesor-1).get_fila()+","+grafo.get(id_nodo_predecesor-1).get_columna()+"]");
			id_nodo_predecesor=predecesor[id_nodo_predecesor-1];
		}
		cadena_parcial.push("R["+grafo.get(id_nodo_predecesor-1).get_fila()+","+grafo.get(id_nodo_predecesor-1).get_columna()+"]");
		String cadena_salida="";
		while(!cadena_parcial.isEmpty()){
			cadena_salida=cadena_salida+cadena_parcial.peek();
			cadena_parcial.pop();
		}
		cadena_salida=cadena_salida+"\r\n\r\nEnergía total consumida: "+especial[nodo_salida.get_id_nodo()-1];
		return(cadena_salida);
	}
}
