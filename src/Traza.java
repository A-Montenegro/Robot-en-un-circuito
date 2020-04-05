import java.util.ArrayList;
/**
* 
* Clase que almacena la estructura y los datos que componen la traza
* @author: Alberto Martínez Montenegro
*/
public class Traza {
	private ArrayList<Integer> paso;
	private ArrayList<Integer> v;
	private ArrayList<int[]> c;
	private ArrayList<String[]> especial;
	private ArrayList<String[]> predecesor;
	
	/**
	 *  Constructor de la clase Traza, incializa los vectores.
	*/
	public Traza(){
		paso=new ArrayList<Integer>();
		v=new ArrayList<Integer>();
		c=new ArrayList<int[]>();
		especial=new ArrayList<String[]>();
		predecesor=new ArrayList<String[]>();
	}
	
	/**
	 *  Método que añade un ArrayList de enteros con los pasos del algoritmo
	*/
	public ArrayList<Integer>  get_paso(){
		return paso;
	}
	
	/**
	 *  Método que añade un ArrayList de enteros con los valores del nodo v
	*/
	public ArrayList<Integer>  get_v(){
		return v;
	}
	
	/**
	 *  Método que añade un ArrayList de arrays de enteros con los valores del vector c
	*/
	public ArrayList<int[]> get_c(){
		return c;
	}
	
	/**
	 *  Método que añade un ArrayList de arrays de Strings con los valores del vector especial
	*/
	public ArrayList<String[]> get_especial(){
		
		return especial;
	}
	
	/**
	 *  Método que añade un ArrayList de arrays de Strings con los valores del vector predecesor
	*/
	public ArrayList<String[]> get_predecesor(){
		return predecesor;
	}
	
	/**
	 *  Método que añade un elemento al vector paso
	*/
	public void add_paso(int paso){
		this.paso.add(paso);
	}
	
	/**
	 *  Método que añade un elemento al vector predecesor de la traza
	*/
	public void add_v(int v){
		this.v.add(v);
	}
	
	/**
	 *  Método que añade un elemento al vector c de la traza
	*/
	public void add_c(int[] c){
		int[] aux= new int[c.length];
		for(int z=0;z<c.length;z++) {
				aux[z]=c[z];	
		}
			this.c.add(aux);
	}
	
	/**
	 *  Método que añade un elemento al vector especial de la traza
	*/
	public void add_especial(int[] especial,int v){
		String[] aux= new String[especial.length];
		for(int z=0;z<especial.length;z++) {
			switch(especial[z]) {
			case -1:
				aux[z]="INF";
			break;
			case 0:
				aux[z]="-";
				if(z==v-1) aux[z]="["+aux[z]+"]";
			break;
			default:
				if(z==v-1) {
					aux[z]="["+String.valueOf(especial[z]+"]");	
				}
				else {
					aux[z]=String.valueOf(especial[z]);	
				}
			break;
			}
		}
			this.especial.add(aux);
	}
	
	/**
	 *  Método que añade un elemento al vector predecesor de la traza
	*/
	public void add_predecesor(int[] predecesor,int v){
		String[] aux= new String[predecesor.length];
		for(int z=0;z<predecesor.length;z++) {
			switch(predecesor[z]){
				case -2:
					aux[z]="-";
				break;
				case -1:
					aux[z]="INF";
				break;
				default:
					if(z==v-1) {
						aux[z]="["+String.valueOf(predecesor[z]+"]");	
					}
					else {
						aux[z]=String.valueOf(predecesor[z]);
					}
				break;
			}
		}
		this.predecesor.add(aux);
	}
	
	/**
	 *  Método que devuelve la cadena de texto que contiene la información de la traza que queremos visualizar.
	 *  @return 	-Una cadena de texto que contiene la información de la traza que queremos visualizar.
	*/
	public String imprimir_traza() {
		String cadena="\r\n\r\n----------------------------------------------------------------------------\r\n\r\nTRAZA:\r\n";
		for(int x=0;x<paso.size();x++) {
			String cadena_v="";
			String cadena_c="";
			String cadena_especial="";
			String cadena_predecesor="";
			for(int i=0;i<c.get(x).length;i++) if(c.get(x)[i]!=0) cadena_c=cadena_c+" "+c.get(x)[i];
			for(int i=0;i<especial.get(x).length;i++) cadena_especial=cadena_especial+" "+especial.get(x)[i];
			for(int i=0;i<predecesor.get(x).length;i++) cadena_predecesor=cadena_predecesor+" "+predecesor.get(x)[i];
			if(v.get(x)>=0) {
				cadena_v=""+v.get(x);
			}
			else {
				cadena_v="-";
			}
			cadena=cadena+("\r\nPaso:"+x+"\r\nv:"+cadena_v+"\r\nC:                   {"+cadena_c+" }\r\nespecial[1..."+especial.get(x).length+"]:   "+cadena_especial+ "\r\npredecesor[1..."+predecesor.get(x).length+"]: "+cadena_predecesor+"\r\n\r\n");
		}
		return cadena+("\r\n\r\nLeyenda:\r\nINF: Distancia infinita.\r\n-: Distancia al nodo de inicio/Predecesor del nodo de inicio\r\n[_]: Distancia al nodo v/Predecesor del nodo v.");
		}
}
