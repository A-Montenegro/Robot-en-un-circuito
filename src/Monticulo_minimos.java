/**
* 
* Clase que implementa un montículo de mínimos.
* Se ha construído a partir del pseudocódigo del libro base, por lo tanto cualquier información sobre su funcionamiento puede consularse en él.
* @author Alberto Martínez Montenegro
*/
public class Monticulo_minimos{
	private Nodo[] nodos;
	private int c;
	private int max;
	private int[] especial;
	
	public Monticulo_minimos(int elementos_maximos,int[] especial) {
		c=0;
		max=elementos_maximos+1;
		nodos=new Nodo[max];
		this.especial=especial;
	}
	public boolean es_vacio() {
		if(c==0) return true;
		return false;
	}
	public Nodo primero() {
		if(c==0) return null;
		return nodos[1];
	}
	public Nodo obtener_cima() {
		Nodo e;
		if(c!=0) {
			e=nodos[1];
			nodos[1]=nodos[c];
			c--;
			hundir(1);
			return e;
		}
		else {
			return null;
		}
	}
	public void insertar(Nodo e) {
		if(c==max) {
			System.out.println("Error de desbordamiento al ejecutar el proceso");
			System.exit(0);
		}
		else {
			c++;
			nodos[c]=e;
			flotar(c);		
		}
	}
	public void flotar(int i) {
		int padre=i/2;
		Nodo aux;
		while(i>1 && especial[nodos[padre].get_id_nodo()-1]>=especial[nodos[i].get_id_nodo()-1]) {
			aux=nodos[padre];
			nodos[padre]=nodos[i];
			nodos[i]=aux;
			i=padre;
			padre=i/2;
		}
	}
	public void hundir(int i){
		int hi;
		int hd;
		int p;
		Nodo aux;
		do{
			hi=2*i;
			hd=2*i+1;
			p=i;
			if(hd<=c) {
				if(especial[nodos[hd].get_id_nodo()-1]<=especial[nodos[i].get_id_nodo()-1]) {
					i=hd;	
				}
			}
			if(hi<=c ) {
				if(especial[nodos[hi].get_id_nodo()-1]<=especial[nodos[i].get_id_nodo()-1]) {
					i=hi;
				}
			}
			aux=nodos[p];
			nodos[p]=nodos[i];
			nodos[i]=aux;
		}while(p!=i);
	}
	
}
