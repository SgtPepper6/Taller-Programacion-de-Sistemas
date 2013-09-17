package Practica1;

import java.util.*;
import java.io.*;

/*** Flujo ***/
class Flujo{
	//Entrada
	Scanner sc = new Scanner(System.in);
	
	//Lectura
	File arch = null;
	FileReader fr = null;
	BufferedReader br = null;
	
	FileWriter fw = null;
	BufferedWriter bw = null;
	PrintWriter pw = null;
	
	//Objeto evaluar
	Evaluar objeto;
	Errores obj_error;
	StringTokenizer tokens;
	String str_end;
	boolean end;
	
	//Errores Objeto
	boolean existe, existe_inst, existe_error, aux_error;
	int cont;
	String linea, aux, linea_error;
	
	/*** Constructor ***/
	public Flujo(String ruta){
		try{
			//Existencia del archivo
			existe = new File(ruta).exists();
			
			if(existe){//Flujo
				arch = new File(ruta);
				fr = new FileReader(arch);
				br = new BufferedReader(fr);
				
				fw = new FileWriter(arch,true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				objeto = new Evaluar();
				obj_error = new Errores();
				existe = false;
				end = false;
			}
			
		}catch(Exception e){
			System.out.println("ERROR: Archivo no encontrado!");
		}
	}//CONSTRUCTOR
	
	/*** Existencia del archivo ***/
	public boolean Existe(String ruta){
		existe = new File(ruta).exists();
		
		return existe;
	}//EXISTENCIA
	
	/*** Lectura linea por linea ***/
	public void Lectura(String ruta){
		
		try{
			//Lectura
			File arch = null;
			FileReader fr = null;
			//BufferedReader br = null;
			
			//Escritura INST
			File arch2 = null;
			FileWriter fw = null;
			BufferedWriter bw = null;
			PrintWriter pw = null;
			
			//Escritura ERROR
			File arch3 = null;
			FileWriter fw2 = null;
			BufferedWriter bw2 = null;
			PrintWriter pw2 = null;
			
			//Existencia de los archivos de errores e instrucciones
			existe_inst = new File(ruta + ".inst").exists();
			existe_error = new File(ruta + ".err").exists();
			
			if(!existe_inst){//Crear archivo INST si este no existe
				arch2 = new File(ruta + ".inst");
				try{
					arch2.createNewFile();
					
				}catch(Exception e){
					System.out.println("ERROR: Archivo");
				}
			}
			
			
			if(!existe_error){//Crear archivo ERROR si este no existe
				arch3 = new File(ruta + ".err");
				try{
					arch3.createNewFile();
					
				}catch(Exception e){
					System.out.println("ERROR: Archivo");
				}
			}
			
			existe_inst = new File(ruta + ".inst").exists();
			existe_error = new File(ruta + ".err").exists();
			
			if(existe_inst){//Cabecera del archivo INST
				arch2 = new File(ruta + ".inst");
				fw = new FileWriter(arch2);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				
				pw.write(" LINEA\tETQ\tCODOP\t\tOPER\r");
				bw.append("\n-------------------------------------------------------------------\r\n");
				
				pw.close();
				bw.close();
			}
			
			if(existe_inst){//Cabecera del archivo ERROR
				arch3 = new File(ruta + ".err");
				fw2 = new FileWriter(arch3);
				bw2 = new BufferedWriter(fw2);
				pw2 = new PrintWriter(bw2);
				
				pw2.write(" LINEA\tCAMPO\tERROR\r");
				bw2.append("\n-----------------------------------------------------------------------------------\r\n");
				
				pw2.close();
				bw2.close();
			}
			
			
			cont = 0;
			end = false;
			
/** ------------------------ Esencia **///Creación de ambos archivos y evaluación de lineas
			while(((linea=br.readLine())!=null) && !end){//Lectura del archivo linea por linea
					
				cont++;
				str_end = null;
				
				
				System.out.println(cont+ " " + linea);
				if(linea.length()!=0){//Distinta de cero para evitar posibles errores
				
					linea=objeto.Comentarios(linea);//Obtiene la cadena sin comentarios
				
					//tokens = new StringTokenizer(linea);//Separar linea en tokens para evaluar
					
					end = obj_error.Error_I(linea, cont, ruta);
					
				}
			}
		}catch(Exception e){
			System.out.println("ERROR: Archivo no encontrado! AQUI FLUJO");
		}
	}//LECTURA
	
}//FLUJO