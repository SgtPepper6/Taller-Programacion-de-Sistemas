import java.io.*;
import java.util.*;

/*** Errores ***/
class Errores{
	int errores[][] =  {{1,-5,-5,-5,-1},
						{2,6,7,-6,600},
						{3,13,10,-6,600},
						{4,14,12,-6,600},
						{20,15,5,-6,600},
						{-1,-1,-1,-6,600},
						{13,13,-4,-6,600},
						{8,-3,-2,-6,600},
						{9,-3,-2,-6,600},
						{5,-3,-2,-6,600},
						{11,-3,-2,-6,600},
						{5,-3,-2,-6,600},
						{5,-3,-2,-6,600},
						{14,14,-4,-6,600},
						{15,15,-4,-6,600},
						{16,16,-4,-6,600},
						{17,17,-4,-6,600},
						{18,18,-4,-6,600},
						{-1,-1,-1,-1,600},
						{-1,-1,-1,-1,-1},
						{16,16,-1,-6,600}};
	int estado, entrada, cont;
	String descrip, aux;
	char arreglo[];
	boolean continuar;
	
	public Errores(){
		estado = 0;
		cont = 0;
		continuar = true;
	}
	
	public String Error(String cadena){
		aux = cadena.toLowerCase();
		arreglo = aux.toCharArray();
		cont = 0;
		continuar = true;
		estado = 0;
		
		while((arreglo.length > cont) && continuar){
			switch(arreglo[cont]){
			//Letra
				case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g': case 'h': case 'i':
				case 'j': case 'k': case 'l': case 'm': case 'o': case 'p': case 'q': case 'r': case 's':
				case 't': case 'u': case 'v': case 'w': case 'x': case 'y': case 'z': case 'n':
					
					entrada = 0;
					break;
			//Numero, "_"
				case '0': case '1': case '2': case '3': case '4':
				case '5': case '6': case '7': case '8': case '9': case '_':
					
					entrada = 1;
					break;
			//Punto		
				case '.':
					
					entrada = 2;
					break;
			//Caracter
				case '!': case '|': case '"': case '#': case '$': case '%': case '&': case '/': case '(':
				case ')': case '=': case '¿': case '?': case '¡': case '´': case '*': case '+': case '~':
				case '{': case '}': case '[': case ']': case '`': case ',': case ';': case ':': case '-':
				case '¬': case '°': case '^': case '\'': case '¨': case '\\':
					
					entrada = 3;
					break;
					
			//Default
				default:
					entrada = 4;
					break;
			}//SWITCH <Entradas>
			
			cont++;
			
			estado = errores[estado][entrada];
			
			if(estado < 0){
				continuar = false;
			}
		}//While <Cadena>
		
		if(estado > 0){
			entrada = 4;
			
			estado = errores[estado][entrada];
		}
		
		switch(estado){
			case -1:
				descrip = "Fuera de rango";
				break;
			case -2:
				descrip = "Incluye 2 puntos";
				break;
			case -3:
				descrip = "Caracter inválido en CODOP"; 
				break;
			case -4:
				descrip = "Caracter inválido en Etiqueta";
				break;
			case -5:
				descrip = "Inicio con caracter diferente a una letra";
				break;
			case -6:
				descrip = "Caracter inválido";
				break;
			case 600:
				break;
		}
		
		return descrip;
	}
}

/*** Evaluar ***/
class Evaluar{
	//----------->
	String etq, codop, oper;
	boolean betq, bcodop, boper;
	String ini_linea;
	
	char orden[] = new char[3];
	
	//Combinaciones validas
	int combinaciones[][] = {{1,2,3},{1,2,0},{0,2,3},{0,2,0}};
	
	/*** Constructor ***/
	public Evaluar(){
		etq = codop = oper = null;
		betq = bcodop = boper = false;
	}
	
	/*** Eliminar comentarios ***/
	public String Comentarios(String linea){
		
		if(linea.contains(";")){
			linea = linea.substring(0, linea.indexOf(";"));
		}//IF ";"
		
		return linea;
	}//COMENTARIOS
	
	/*** Evaluar Etiqueta ***/
	public boolean Etiqueta(String cadena){
		
		betq = cadena.matches("([^[a-zA-Z]+][\\w]{0,7})");
		
		return betq;
	}//ETIQUETA
	
	/*** Evaluar CODOP ***/
	public boolean Codop(String cadena){
		
		bcodop = cadena.matches("(^[a-zA-Z]{1,4}[[.]?])|(^[a-zA-Z]{1,5})|((^[a-zA-Z]{1,3}[.][a-zA-Z]?){1,5})|((^[a-zA-Z]{1,2}[.][a-zA-Z]{1,2}){1,5})|((^[a-zA-Z]{1}[.][a-zA-Z]{1,3}){1,5})");
		
		return bcodop;
	}//CODOP
	
	/*** Evaluar Operando ***/
	public boolean Operando(String cadena){
		
		boper = cadena.matches("(.+)");
		
		return boper;
	}//CODOP
}//EVALUAR

/*** Flujo ***/
class Flujo{
	//Entrada
	Scanner sc = new Scanner(System.in);
	
	//Lectura
	File arch = null;
	FileReader fr = null;
	BufferedReader br = null;
	
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
	
	//Objeto evaluar
	Evaluar objeto;
	Errores obj_error;
	StringTokenizer tokens;
	String str_end;
	boolean end;
	
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
	public void Lectura(){
		
		try{
			
			existe_inst = new File("Inst.txt").exists();
			existe_error = new File("Error.txt").exists();
			
			if(!existe_inst){//Crear archivo INST si este no existe
				arch2 = new File("Inst.txt");
				try{
					arch2.createNewFile();
					
				}catch(Exception e){
					System.out.println("ERROR: Archivo");
				}
			}
			
			if(!existe_error){//Crear archivo ERROR si este no existe
				arch3 = new File("Error.txt");
				try{
					arch3.createNewFile();
					
				}catch(Exception e){
					System.out.println("ERROR: Archivo");
				}
			}
			
			existe_inst = new File("Inst.txt").exists();
			existe_error = new File("Error.txt").exists();
			
			if(existe_inst){//Cabecera del archivo INST
				arch2 = new File("Inst.txt");
				fw = new FileWriter(arch2);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				pw.write(" LINEA\tETQ\tCODOP\t\tOPER\r");
				bw.append("\n-------------------------------------------------------------------\r\n");
				
				pw.close();
				bw.close();
			}
			
			if(existe_inst){//Cabecera del archivo ERROR
				arch3 = new File("Error.txt");
				fw2 = new FileWriter(arch3);
				bw2 = new BufferedWriter(fw2);
				pw2 = new PrintWriter(bw2);
				
				pw2.write(" LINEA\tCAMPO\tERROR\r");
				bw2.append("\n-------------------------------------------------------------------\r\n");
				
				pw2.close();
				bw2.close();
			}
			
			cont = 0;
			
			/** Esencia **/
			while(((linea=br.readLine())!=null) && !end){//Lectura del archivo linea por linea
					
				cont++;
				str_end = null;
				end = false;
				
				System.out.println(linea);
				if(linea.length()!=0){//Distinta de cero para evitar posibles errores
				
					linea=objeto.Comentarios(linea);//Obtiene la cadena sin comentarios
				
					tokens = new StringTokenizer(linea);//Separar linea en tokens para evaluar
					
				}
				
				/** Tres tokens **/
				if(tokens.countTokens() == 3){
					//Primer caracter de la linea espacio || tabulacion
					if(linea.startsWith("\t") || linea.startsWith(" ")){
						///------------------------------------------>Escribir en archivo ERROR
						
						arch3 = new File("Error.txt");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						bw2.append(cont + "\tNULL" + "\tEspacio o tabulación como primer caracter\r\n");
						
						pw2.close();
						bw2.close();
					}
					else{
						//Etiqueta
						objeto.betq = objeto.Etiqueta((objeto.etq = tokens.nextToken()));
						
						if(objeto.betq){//Instruccion
							objeto.orden[0] = '1';
							
						}else{//Error
							objeto.orden[0] = '6';
							
							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.etq));
							linea_error = obj_error.Error(objeto.etq);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);
							
							bw2.append(cont + "\tETQ" + "\t" + linea_error + "\r\n");
							
							pw2.close();
							bw2.close();
						}
						
						//CODOP
						objeto.bcodop = objeto.Codop((objeto.codop = tokens.nextToken()));
						
						if(objeto.bcodop){//Instruccion
							objeto.orden[1] = '2';
							
							str_end = objeto.codop;
							str_end = str_end.toLowerCase();
							
							if(str_end.equals("end"))
								end = true;
							
						}else{//Error
							objeto.orden[1] = '6';
							
							linea_error = obj_error.Error(objeto.codop);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);

							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							if(obj_error.estado == 600){
								
								bw2.append(cont + "\tCODOP" + "\tFuera de rango\r\n");
								//System.out.println("------------------->");
								//System.out.println("--------------------------------------------->>" /*+ obj_error.Error(objeto.codop)*/ + "------>> Rango");
							}
							else{
								bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
							}
							
							pw2.close();
							bw2.close();
						}
						
						//Operando
						objeto.boper = objeto.Operando((objeto.oper = tokens.nextToken()));
						
						if(objeto.boper){//Instruccion
							objeto.orden[2] = '3';
							
						}else{//Error
							objeto.orden[2] = '6';
						}
						
						aux = String.valueOf(objeto.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
						
						if(objeto.bcodop && objeto.betq && objeto.boper && (aux.equals("123")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
							
							//Re-escribir en archivo
							arch2 = new File("Inst.txt");
							fw = new FileWriter(arch2,true);
							bw = new BufferedWriter(fw);
							pw = new PrintWriter(bw);
							
							bw.append(String.valueOf(cont) + "\t" + objeto.etq + "\t" + objeto.codop + "\t\t" + objeto.oper + "\r\n");
							
							pw.close();
							bw.close();
							
						}//IF agregar INST
						else if(end){//ERROR aparece END junto un Operando
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);
							
							bw2.append(cont + "\tCODOP" + "\tDirectiva END con operando\r\n");
							
							pw2.close();
							bw2.close();
							
							end = false;
							
						}//ELSE-IF agregar ERROR <END>
					}
				}//IF tokens.countTokens == 3
				
				/** Dos tokens **/
				if(tokens.countTokens() == 2){
					//Primer caracter de la linea espacio || tabulacion
					if(linea.startsWith("\t") || linea.startsWith(" ")){
						
						//Comienzo de la linea NO caracter
						objeto.orden[0] = '0';
						objeto.betq = false;
						
						//CODOP
						objeto.bcodop = objeto.Codop((objeto.codop = tokens.nextToken()));
						
						if(objeto.bcodop){//Instruccion
							objeto.orden[1] = '2';
								
							str_end =  objeto.codop;
							str_end = str_end.toLowerCase();
								
							if(str_end.equals("end"))
								end = true;
							
						}else{
							objeto.orden[1] = '6';
							
							linea_error = obj_error.Error(objeto.codop);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);

							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							if(obj_error.estado == 600){
								
								bw2.append(cont + "\tCODOP" + "\tFuera de rango\r\n");
								//System.out.println("------------------->");
								//System.out.println("--------------------------------------------->>" /*+ obj_error.Error(objeto.codop)*/ + "------>> Rango");
							}
							else{
								bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
							}
							
							pw2.close();
							bw2.close();
						}
						
						//Operando
						objeto.boper = objeto.Operando((objeto.oper = tokens.nextToken()));
						
						if(objeto.boper){
							objeto.orden[2] = '3';
							
						}else{
							objeto.orden[2] = '6';
						}
						
						aux = String.valueOf(objeto.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
						
						if(!objeto.betq && objeto.bcodop && objeto.boper && (aux.equals("023")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
							//Re-escribir en archivo
							arch2 = new File("Inst.txt");
							fw = new FileWriter(arch2,true);
							bw = new BufferedWriter(fw);
							pw = new PrintWriter(bw);
							
							bw.append(String.valueOf(cont) + "\tNULL" + "\t" + objeto.codop + "\t\t" + objeto.oper + "\r\n");
							
							pw.close();
							bw.close();
						}//IF agregar INST
						else if(end){
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);
							
							bw2.append(cont + "\tCODOP" + "\tDirectiva END con operando\r\n");
							
							pw2.close();
							bw2.close();
							
							end = false;
						}//ELSE-IF agregar ERROR <END>
					}
					else{
						//Etiqueta-------------------------------------------------------------------------------------------------------------------
						objeto.betq = objeto.Etiqueta((objeto.etq = tokens.nextToken()));
						
						if(objeto.betq){
							objeto.orden[0] = '1';
							
						}else{
							objeto.orden[0] = '6';
							
							linea_error = obj_error.Error(objeto.codop);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);

							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							bw2.append(cont + "\tETQ" + "\t" + linea_error + "\r\n");
							
							pw2.close();
							bw2.close();
							
							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.etq));
						}
						
						//CODOP
						objeto.bcodop = objeto.Codop((objeto.codop = tokens.nextToken()));
						
						if(objeto.bcodop){
							objeto.orden[1] = '2';
							
							str_end =  objeto.codop;
							str_end = str_end.toLowerCase();
								
							if(str_end.equals("end"))
								end = true;
							
						}else{
							objeto.orden[1] = '6';
							
							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							linea_error = obj_error.Error(objeto.codop);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);

							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							if(obj_error.estado == 600){
								
								bw2.append(cont + "\tCODOP" + "\tFuera de rango\r\n");
								//System.out.println("------------------->");
								//System.out.println("--------------------------------------------->>" /*+ obj_error.Error(objeto.codop)*/ + "------>> Rango");
							}
							else{
								bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
							}
							
							pw2.close();
							bw2.close();
						}
						
						//Comienzo de la linea caracter
						//Operando
						objeto.orden[2] = '0';
						objeto.boper = false;
						
						aux = String.valueOf(objeto.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
						
						if(objeto.betq && objeto.bcodop && !objeto.boper && (aux.equals("120")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
							//Re-escribir en archivo
							arch2 = new File("Inst.txt");
							fw = new FileWriter(arch2,true);
							bw = new BufferedWriter(fw);
							pw = new PrintWriter(bw);
							
							bw.append(String.valueOf(cont) + "\t" + objeto.etq + "\t" + objeto.codop + "\t\tNULL" + "\r\n");
							
							pw.close();
							bw.close();
						}
					}
				}//IF tokens.countTokens == 2
				
				/** Un tokens **/
				if(tokens.countTokens() == 1){
					//Primer caracter de la linea espacio || tabulacion
					if(linea.startsWith("\t") || linea.startsWith(" ")){
						
						//Unico token
						objeto.orden[0] = '0';
						objeto.betq = false;
						
						objeto.orden[2] = '0';
						objeto.boper = false;
						
						//CODOP
						objeto.bcodop = objeto.Codop((objeto.codop = tokens.nextToken()));
						
						if(objeto.bcodop){
							objeto.orden[1] = '2';
							
							str_end =  objeto.codop;
							str_end = str_end.toLowerCase();
								
							if(str_end.equals("end"))
								end = true;
							
						}else{
							objeto.orden[1] = '6';
							
							linea_error = obj_error.Error(objeto.codop);
							
							arch3 = new File("Error.txt");
							fw2 = new FileWriter(arch3,true);
							bw2 = new BufferedWriter(fw2);
							pw2 = new PrintWriter(bw2);

							//System.out.println("--------------------------------------------->>" + obj_error.Error(objeto.codop));
							
							if(obj_error.estado == 600){
								
								bw2.append(cont + "\tCODOP" + "\tFuera de rango\r\n");
								//System.out.println("------------------->");
								//System.out.println("--------------------------------------------->>" /*+ obj_error.Error(objeto.codop)*/ + "------>> Rango");
							}
							else{
								bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
							}
							
							pw2.close();
							bw2.close();
						}
						
						aux = String.valueOf(objeto.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
						
						if(!objeto.betq && objeto.bcodop && !objeto.boper && (aux.equals("020")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
							//Re-escribir en archivo
							arch2 = new File("Inst.txt");
							fw = new FileWriter(arch2,true);
							bw = new BufferedWriter(fw);
							pw = new PrintWriter(bw);
							
							bw.append(String.valueOf(cont) + "\tNULL" + "\t" + objeto.codop + "\t\tNULL" + "\r\n");
							
							pw.close();
							bw.close();
						}
					}
				}//IF tokens.countTokens == 1
				
			}
		}catch(Exception e){
			System.out.println("ERROR: Archivo no encontrado!");
		}
	}//LECTURA
	
}//FLUJO

public class Prc1 {
	
	//PRINCIPAL
	public static void main(String [] args){
		//Entrada
		Scanner sc = new Scanner(System.in);
		String str, ruta;
		
		//System.out.println("->Ruta: ");
		//ruta = sc.nextLine();
		ruta = "Ensamblador.txt";
		
		//Objeto
		Flujo objeto = new Flujo(ruta);
		
		objeto.existe = objeto.Existe(ruta);
		
		System.out.println("->Existe: " + objeto.existe);
		
		if(objeto.existe){
			objeto.Lectura();
			System.out.println("->Dentro del IF lectura");
		}
	}//MAIN
}//PRC1
