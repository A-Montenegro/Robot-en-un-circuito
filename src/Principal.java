import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Clase con el método main, también contiene el código necesario para realizar las 
 * operaciones correspondientes a los parámetros de entrada introducidos.
* @author Alberto Martínez Montenegro
*/
public class Principal {
	private static long tiempo_de_incio;
	
	/**
	 * Método principal, realiza las 
	 * operaciones correspondientes a los parámetros de entrada introducidos.
	 * @param args Los argumentos opcionales y obliatorios según lo que se desea hacer.
	*/
	public static void main(String args[]) throws FileNotFoundException, IOException  {
		tiempo_de_incio = System.currentTimeMillis();
		String fichero_entrada="";
		String fichero_salida="";
		Entrada entrada;
		Robot r;
		switch (args.length) {
		case 3:
			if(args[0].equals("-h") || args[1].equals("-h") || args[2].equals("-h")) muestra_ayuda();
			if(!(args[0].equals("-t") || args[1].equals("-t") || args[2].equals("-t"))) muestra_ayuda();
			fichero_entrada=args[1];
			fichero_salida=args[2];
			entrada=new Entrada(fichero_entrada);
			r=new Robot(entrada.get_datos_entrada());
			if(r.get_n()*r.get_m()>1024) { //Si la matriz es muy grande omitimos la traza, ya que sería de casi imposible visualización.
				System.out.println("La matriz correspondiente a los datos de entrada es demasiado grande como para generar la traza, la traza se omitirá.\n");
				if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false); //Si se detecta la sintaxis especial para pruebas, se ejecuta el algoritmo 9 veces extra.
				r.dijkstra(false);
				generar_fichero_salida(r.recorrido(),fichero_salida);
			}
			else {
				if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false);
				r.dijkstra(true);
				generar_fichero_salida(r.recorrido()+r.get_traza().imprimir_traza(),fichero_salida);
			}
		break;
		case 2:
			if(args[0].equals("-h") || args[1].equals("-h") || args[1].equals("-t")) muestra_ayuda();
			if(args[0].equals("-t")){
				fichero_entrada=args[1];
				entrada=new Entrada(fichero_entrada);
				r=new Robot(entrada.get_datos_entrada());
				if(r.get_n()*r.get_m()>1024) {//Si la matriz es muy grande omitimos la traza, ya que sería de casi imposible visualización.
					System.out.println("La matriz correspondiente a los datos de entrada es demasiado grande como para generar la traza, la traza se omitirá.\n");
					if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false);
					r.dijkstra(false);
					if(fichero_entrada.length()>24) {
						if(!fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) System.out.println(r.recorrido());
					}
					else {
						System.out.println(r.recorrido());
					}
				}
				else {
					if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false);
					r.dijkstra(true);
					if(fichero_entrada.length()>24) {
						if(!fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) System.out.println(r.recorrido()+r.get_traza().imprimir_traza());
					}
					else {
						System.out.println(r.recorrido()+r.get_traza().imprimir_traza());
					}
				}
			}
			else {
				fichero_entrada=args[0];
				fichero_salida=args[1];
				entrada=new Entrada(fichero_entrada);
				r=new Robot(entrada.get_datos_entrada());
				if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false);
				r.dijkstra(false);
				generar_fichero_salida(r.recorrido(),fichero_salida);
			}
		break;
		case 1:
			if(args[0].equals("-h") || args[0].equals("-t")) muestra_ayuda();
			fichero_entrada=args[0];
			entrada=new Entrada(fichero_entrada);
			r=new Robot(entrada.get_datos_entrada());
			if(fichero_entrada.length()>24) if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) for(int i=0;i<9;i++) r.dijkstra(false);
			r.dijkstra(false);
			if(fichero_entrada.length()>24) {
				if(!fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) System.out.println(r.recorrido());
			}
			else {
				System.out.println(r.recorrido());
			}
		break;
		default:
			muestra_ayuda();
		break;
		}
		if(fichero_entrada.length()>24) {
			if(fichero_entrada.substring(0, 22).equals("prueba_preconfigurada_")) {
				System.out.println("\r\n\r\nProceso completado, tiempo total de ejecución: "+(System.currentTimeMillis()-tiempo_de_incio)/10+" milisegundos.");// Si se ha usado la sintaxis especial de pruebas, se divide el tiempo entre 10
			}
			else {
				System.out.println("\r\n\r\nProceso completado, tiempo total de ejecución: "+(System.currentTimeMillis()-tiempo_de_incio)+" milisegundos.");
			}
		}
		else {
			System.out.println("\r\n\r\nProceso completado, tiempo total de ejecución: "+(System.currentTimeMillis()-tiempo_de_incio)+" milisegundos.");
		}
	}
	
	/**
	 * Método estático que simplemente visualiza el texto de ayuda
	*/
	public static void muestra_ayuda() {
		System.out.println("SINTAXIS\r\n"
				+ "robot [-t] [-h] [fichero_entrada] [fichero_salida]\r\n"
				+ "-t                        Traza la aplicación del algoritmo a los datos.\r\n"
				+ "-h                        Muestra esta ayuda.\r\n"
				+ "fichero_entrada           Nombre del fichero de entrada.\r\n"
				+ "fichero_salida            Nombre del fichero de salida.\r\n");
		System.exit(0);
	}
	
	/**
	 * Método estático que crea un fichero de texto con los datos que se le pasen como parámetro
	 * @param cadena_salida El texto que contendrá el fichero de texto
	 * @param fichero_salida El nombre con el que será generado el fichero.
	*/
	public static void generar_fichero_salida(String cadena_salida,String fichero_salida) throws IOException {
		Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero_salida)));
	    w.write(cadena_salida);
	    w.write("\r\n\r\nProceso completado, tiempo total de ejecución: "+(System.currentTimeMillis()-tiempo_de_incio)+" milisegundos.");
	    w.close();
	}	
}
