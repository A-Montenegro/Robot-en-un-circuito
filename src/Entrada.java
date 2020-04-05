import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Clase que se encarga de cargar los datos de entrada desde el fichero de texto,
 * y comprobar que sean correctos
* @author Alberto Martínez Montenegro
*/
public class Entrada {
	private ArrayList<String> datos_entrada;
	
	/**
	 * Constructor de la clase Entrada.
	 * Valida los datos del fichero de texto y los almacena en un ArrayList de Strings
	*/
	public Entrada(String archivo) throws IOException {
		  ArrayList<String> entrada = new ArrayList<String> () ;
		  if(archivo.length()>24) {
			  if(archivo.substring(0, 22).equals("prueba_preconfigurada_")) {
				  String[] partes=archivo.substring(22, archivo.length()).split("x");
				  if(partes.length==2 && es_entero(partes[0]) && es_entero(partes[1])) {
					  entrada=generar_matriz_preconfigurada(Integer.parseInt(partes[0]),Integer.parseInt(partes[1]));
					  datos_entrada=entrada;
					  return;
				  }
				  else {
					  System.out.println("Dimensiones de la prueba preconfigurada incorrectos");
					  System.exit(0);
				  }
			  }
		  }
	      String cadena;
	      int contador=-2;
	      int posicion_salida=0;
	      int filas;
	      int columnas;
	      boolean testigo_s=false;
	      boolean testigo_r=false;
	      boolean testigo_o=false;
	      FileReader f=null;
	      try{
	    	 f = new FileReader(archivo);
	      }
	      catch(IOException e) {
	    	  System.out.println("\nSe ha producido un error: "+e.getMessage());
	    	  System.exit(0);
	      }
	      BufferedReader b = new BufferedReader(f);
	      while((cadena = b.readLine())!=null) {
				switch (cadena) {
					case "S":
						if(testigo_s) {
							System.out.println("Datos de entrada incorrectos, hay mas de una 'S'");
							System.exit(0);
						}
						else {
							posicion_salida=contador;
							testigo_s=true;
							entrada.add(cadena);
						}
					break;
					case "R":
						if(testigo_r) {
							System.out.println("Datos de entrada incorrectos, hay mas de una 'R'");
							System.exit(0);
						}
						
						else {
							testigo_r=true;
							entrada.add(cadena);
						}
					break;
					case "O":
						testigo_o=true;
						entrada.add(cadena);
					break;
					default:	
						if(!es_entero(cadena)) {
							System.out.println("Datos de entrada incorrectos, alguno de los elementos introducidos no es ni 'S' ni 'R' ni 'O' ni un número entero");
							System.exit(0);
						}
						else {
							if(!(Integer.parseInt(cadena)>0)) {
								System.out.println("Datos de entrada incorrectos, alguno de los elementos numéricos introducidos no es mayor que 0");
								System.exit(0);
							}
							entrada.add(cadena);
						}
					break;
				}
				contador++;
		      }
		      if (!testigo_s || !testigo_r || !testigo_o) {
		    		System.out.println("Datos de entrada incorrectos, falta al menos una 'S' o una 'R' o una 'O'");
		    		System.exit(0);
		      }
		      if (!es_entero(entrada.get(0)) || !es_entero(entrada.get(1))) {
		    		System.out.println("Datos de entrada incorrectos, las dos primeras líneas deben ser números enteros.");
		    		System.exit(0);
		      }
		      filas=Integer.parseInt(entrada.get(0));
		      columnas=Integer.parseInt(entrada.get(1));
		      if(filas*columnas!=entrada.size()-2) {
		    		System.out.println("Datos de entrada incorrectos, el número de elmentos introducidos no coincide con los especificados para filas y columnas.");
		    		System.exit(0);
		      }
		      //En la siguiente línea, se comprueba que la posición del nodo de salida, forma parte de la periferia:
		      if(!(posicion_salida<columnas || posicion_salida>=(filas*columnas)-columnas || posicion_salida%columnas==0 || (posicion_salida+1)%columnas==0)) { 
					System.out.println("Datos de entrada incorrectos, 'S' no está en una posición periférica");
					System.exit(0);
		      }
		      b.close();
		      datos_entrada=entrada;
	}
	
	/**
	 * Método que devuelve verdadero si la cadena que se le pasa como parámetro puede ser trasnformada a entero.
	*/
	public boolean es_entero(String cadena) {
		try {
		    @SuppressWarnings("unused")
			int op1 = Integer.parseInt(cadena);
		    return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Método que devuelve un ArrayList de Strings con los datos de entrada.
	*/
	public ArrayList<String> get_datos_entrada(){
		return datos_entrada;
	}
	
	/**
	 * Método que devuelve un ArrayList de Strings con una matriz preconfigurada.
	 * Se usa para ejecutar las pruebas de rendimiento propuestas en la tutoría.
	*/
	public ArrayList<String> generar_matriz_preconfigurada(int fil, int col){
		ArrayList<String> matriz=new ArrayList<String>();
		matriz.add(String.valueOf(fil));
		matriz.add(String.valueOf(col));
		int randomNum;
		  for(int i=0;i<fil;i++) {
				for(int x=0;x<col;x++) {
					if(i==0 && x==0) {
						matriz.add("R");
					}
					else {
						if(i==0 && x==1) {
							matriz.add("O");
						}
						else {
							if(i==fil-1 && x==col-1) {
								matriz.add("S");
							}
							else {
								randomNum = 1 + (int)(Math.random() * 1000); 
								matriz.add(String.valueOf(randomNum));
							}
						}
					}
				}
		  }
		return matriz;
	}
}
