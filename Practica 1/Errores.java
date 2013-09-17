package Practica1;

import java.util.*;
import java.io.*;

/** Errores Parte II **/
class Errores{

	//Matriz
	int errores[][] =  {{1,-5,-5,-5,-6},
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
	StringTokenizer tokens;
	
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
		entrada = 0;
		
		//Entrada según caracter
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
			
			estado = errores[estado][entrada];//Cambio de estados
			
			if(estado < 0){
				continuar = false;
			}
		}//While <Cadena>
		
		if(estado > 0){
			entrada = 4;
			
			estado = errores[estado][entrada];
		}
		
		//Descripción de errores
		switch(estado){
			case -1:
				descrip = "Fuera de rango.";
				break;
			case -2:
				descrip = "Incluye más de un punto.";
				break;
			case -3:
				descrip = "Caracter inválido en CODOP."; 
				break;
			case -4:
				descrip = "Caracter inválido en Etiqueta.";
				break;
			case -5:
				descrip = "Inicio con caracter diferente a una letra.";
				break;
			case -6:
				descrip = "Caracter inválido.";
				break;
			case 600:
				break;
		}
		
		return descrip;	
	}

	public boolean Error_I(String linea, int cont, String ruta){
		//Error
		String cadena_error;
		Evaluar objeto_evaluar = new Evaluar();
		Errores objeto_errores = new Errores();
		
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
		
		//Variables
		String str_end;
		boolean end = false;
		boolean existe, existe_inst, existe_error, aux_error;
		String aux, linea_error;
		
		try{
			/****** TOKENS ******/
			tokens = new StringTokenizer(linea);//Separar linea en tokens para evaluar
			
			//**De acuerdo al número de tokens se distinguen las líneas
			/** Tres tokens **/
			if(tokens.countTokens() == 3){
				//Primer caracter de la linea espacio || tabulacion
				if(linea.startsWith("\t") || linea.startsWith(" ")){
					
					arch3 = new File(ruta + ".err");
					fw2 = new FileWriter(arch3,true);
					bw2 = new BufferedWriter(fw2);
					pw2 = new PrintWriter(bw2);
					
					bw2.append(cont + "\tNULL" + "\tEspacio o tabulación como primer caracter.\r\n");
					
					pw2.close();
					bw2.close();
				}
				else{
					/**---------->Etiqueta      **/
					objeto_evaluar.betq = objeto_evaluar.Etiqueta((objeto_evaluar.etq = tokens.nextToken()));
					
					if(objeto_evaluar.betq){//Instruccion
						objeto_evaluar.orden[0] = '1';
						
						
					}else{//Error
						objeto_evaluar.orden[0] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.etq);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						if(objeto_errores.estado == -3){//Al devolver -3 indica error en CODOP, por la existencia de un punto
							bw2.append(cont + "\tETQ" + "\tCaracter inválido en Etiqueta.\r\n");
						}
						else{
							bw2.append(cont + "\tETQ" + "\t" + linea_error + "\r\n");
						}							
						
						pw2.close();
						bw2.close();
					}
					
					/**---------->CODOP      **/
					objeto_evaluar.bcodop = objeto_evaluar.Codop((objeto_evaluar.codop = tokens.nextToken()));
					
					if(objeto_evaluar.bcodop){//Instruccion
						objeto_evaluar.orden[1] = '2';
						
						str_end = objeto_evaluar.codop;
						str_end = str_end.toLowerCase();
						
						if(str_end.equals("end"))
							end = true;
						
					}else{//Error
						objeto_evaluar.orden[1] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.codop);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
	
						//Al culminar en el estado 600, el codop es inválida si cuenta con números o "_"
						if((objeto_errores.estado == 600) || objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9")){
							
							//Al ser caracteres de Etiqueta
							if(objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9"))
								bw2.append(cont + "\tCODOP" + "\tCaracter inválido en CODOP.\r\n");
							else if(objeto_errores.estado == 600)
								bw2.append(cont + "\tCODOP" + "\tNúmero de caracteres inválido.\r\n");
						}
						else{
							bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
						}
						
						pw2.close();
						bw2.close();
						
					}
					
					/**---------->Operando      **/ 
					objeto_evaluar.boper = objeto_evaluar.Operando((objeto_evaluar.oper = tokens.nextToken()));
					
					if(objeto_evaluar.boper){//Instruccion
						objeto_evaluar.orden[2] = '3';
						
					}else{//Error
						objeto_evaluar.orden[2] = '6';
					}
					
					aux = String.valueOf(objeto_evaluar.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
					
					if(objeto_evaluar.bcodop && objeto_evaluar.betq && objeto_evaluar.boper && (aux.equals("123")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
						
						//Re-escribir en archivo
						arch2 = new File(ruta + ".inst");
						fw = new FileWriter(arch2,true);
						bw = new BufferedWriter(fw);
						pw = new PrintWriter(bw);
						
						bw.append(String.valueOf(cont) + "\t" + objeto_evaluar.etq + "\t" + objeto_evaluar.codop + "\t\t" + objeto_evaluar.oper + "\r\n");
						
						pw.close();
						bw.close();
						
					}//IF agregar INST
					else if(end){//ERROR aparece END junto un Operando
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						bw2.append(cont + "\tCODOP" + "\tDirectiva END con operando.\r\n");
						
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
					objeto_evaluar.orden[0] = '0';
					objeto_evaluar.betq = false;
					
					/**---------->CODOP      **/
					objeto_evaluar.bcodop = objeto_evaluar.Codop((objeto_evaluar.codop = tokens.nextToken()));
					
					if(objeto_evaluar.bcodop){//Instruccion
						objeto_evaluar.orden[1] = '2';
							
						str_end =  objeto_evaluar.codop;
						str_end = str_end.toLowerCase();
							
						if(str_end.equals("end"))
							end = true;
					}else{//Error
						objeto_evaluar.orden[1] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.codop);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						//Al culminar en el estado 600, el codop es inválida si cuenta con números o "_"
						if((objeto_errores.estado == 600) || objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9")){//Al contener el caracter _
							
							//Al ser caracteres de Etiqueta
							if(objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6")
								 || objeto_evaluar.codop.contains("7") || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9"))
								bw2.append(cont + "\tCODOP" + "\tCaracter inválido en CODOP.\r\n");
							else if(objeto_errores.estado == 600)
								bw2.append(cont + "\tCODOP" + "\tNúmero de caracteres inválido.\r\n");
						}
						
						else{
							bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
						}
						
						pw2.close();
						bw2.close();
					}
					
					/**---------->Operando      **/
					objeto_evaluar.boper = objeto_evaluar.Operando((objeto_evaluar.oper = tokens.nextToken()));
					
					if(objeto_evaluar.boper){
						objeto_evaluar.orden[2] = '3';
						
					}else{
						objeto_evaluar.orden[2] = '6';
					}
					
					aux = String.valueOf(objeto_evaluar.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
					
					if(!objeto_evaluar.betq && objeto_evaluar.bcodop && objeto_evaluar.boper && (aux.equals("023")) && !end){//Si todo está correcto, agregar al archivo de Intruccciones
						//Re-escribir en archivo
						arch2 = new File(ruta + ".inst");
						fw = new FileWriter(arch2,true);
						bw = new BufferedWriter(fw);
						pw = new PrintWriter(bw);
						
						bw.append(String.valueOf(cont) + "\tNULL" + "\t" + objeto_evaluar.codop + "\t\t" + objeto_evaluar.oper + "\r\n");
						
						pw.close();
						bw.close();
					}//IF agregar INST
					else if(end){
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						bw2.append(cont + "\tCODOP" + "\tDirectiva END con operando.\r\n");
						
						pw2.close();
						bw2.close();
						
						end = false;
					}//ELSE-IF agregar ERROR <END>
				}
				else{
					/**---------->Etiqueta      **/
					objeto_evaluar.betq = objeto_evaluar.Etiqueta((objeto_evaluar.etq = tokens.nextToken()));
					
					if(objeto_evaluar.betq){
						objeto_evaluar.orden[0] = '1';
						
					}else{
						objeto_evaluar.orden[0] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.etq);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						if(objeto_errores.estado == -3){//Al devolver -3 indica error en CODOP, por la existencia de un punto
							
							bw2.append(cont + "\tETQ" + "\tCaracter inválido en Etiqueta.\r\n");
						}
						else{
							bw2.append(cont + "\tETQ" + "\t" + linea_error + "\r\n");
						}
						
						pw2.close();
						bw2.close();
					}
					
					/**---------->CODOP      **/
					objeto_evaluar.bcodop = objeto_evaluar.Codop((objeto_evaluar.codop = tokens.nextToken()));
					
					if(objeto_evaluar.bcodop){
						objeto_evaluar.orden[1] = '2';
						
						str_end =  objeto_evaluar.codop;
						str_end = str_end.toLowerCase();
							
						if(str_end.equals("end"))
							end = true;
						
					}else{
						objeto_evaluar.orden[1] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.codop);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						//Al culminar en el estado 600, el codop es inválida si cuenta con números o "_"
						if((objeto_errores.estado == 600) || objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1")
								|| objeto_evaluar.codop.contains("2") || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5")
								|| objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7") || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9")){//Al contener el caracter _
							
							//Al ser caracteres de Etiqueta
							if(objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9"))
								bw2.append(cont + "\tCODOP" + "\tCaracter inválido en CODOP.\r\n");
							else if(objeto_errores.estado == 600)
								bw2.append(cont + "\tCODOP" + "\tFuera de rango.\r\n");
						}
						
						else{
							bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
						}
						
						pw2.close();
						bw2.close();
					}
					
					//Comienzo de la linea caracter
					//Operando
					objeto_evaluar.orden[2] = '0';
					objeto_evaluar.boper = false;
					
					aux = String.valueOf(objeto_evaluar.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
					
					if(objeto_evaluar.betq && objeto_evaluar.bcodop && !objeto_evaluar.boper && (aux.equals("120"))){//Si todo está correcto, agregar al archivo de Intruccciones
						//Re-escribir en archivo
						arch2 = new File(ruta + ".inst");
						fw = new FileWriter(arch2,true);
						bw = new BufferedWriter(fw);
						pw = new PrintWriter(bw);
						
						bw.append(String.valueOf(cont) + "\t" + objeto_evaluar.etq + "\t" + objeto_evaluar.codop + "\t\tNULL" + "\r\n");
						
						pw.close();
						bw.close();
					}
				}
			}//IF tokens.countTokens == 2
			
			/** Un token **/
			if(tokens.countTokens() == 1){
				//Primer caracter de la linea espacio || tabulacion
				if(linea.startsWith("\t") || linea.startsWith(" ")){
					
					//Unico token
					objeto_evaluar.orden[0] = '0';
					objeto_evaluar.betq = false;
					
					objeto_evaluar.orden[2] = '0';
					objeto_evaluar.boper = false;
					
					/**---------->CODOP      **/
					objeto_evaluar.bcodop = objeto_evaluar.Codop((objeto_evaluar.codop = tokens.nextToken()));
					
					if(objeto_evaluar.bcodop){
						objeto_evaluar.orden[1] = '2';
						
						str_end =  objeto_evaluar.codop;
						str_end = str_end.toLowerCase();
							
						if(str_end.equals("end"))
							end = true;
						
					}else{
						objeto_evaluar.orden[1] = '6';
						
						linea_error = objeto_errores.Error(objeto_evaluar.codop);
						
						arch3 = new File(ruta + ".err");
						fw2 = new FileWriter(arch3,true);
						bw2 = new BufferedWriter(fw2);
						pw2 = new PrintWriter(bw2);
						
						//Al culminar en el estado 600, el codop es inválida si cuenta con números o "_"
						if((objeto_errores.estado == 600) || objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9")){//Al contener el caracter _
							
							//Al ser caracteres de Etiqueta
							if(objeto_evaluar.codop.contains("_") || objeto_evaluar.codop.contains("0") || objeto_evaluar.codop.contains("1") || objeto_evaluar.codop.contains("2")
								 || objeto_evaluar.codop.contains("3") || objeto_evaluar.codop.contains("4") || objeto_evaluar.codop.contains("5") || objeto_evaluar.codop.contains("6") || objeto_evaluar.codop.contains("7")
								 || objeto_evaluar.codop.contains("8") || objeto_evaluar.codop.contains("9"))
								bw2.append(cont + "\tCODOP" + "\tCaracter inválido en CODOP.\r\n");
							else if(objeto_errores.estado == 600)
								bw2.append(cont + "\tCODOP" + "\tFuera de rango.\r\n");
						}
						else{
							bw2.append(cont + "\tCODOP" + "\t" + linea_error + "\r\n");
						}
						
						pw2.close();
						bw2.close();
					}
					
					aux = String.valueOf(objeto_evaluar.orden);//Pasan a String, con el fin de comparar con las combinaciones validas
					
					if(!objeto_evaluar.betq && objeto_evaluar.bcodop && !objeto_evaluar.boper && (aux.equals("020"))){//Si todo está correcto, agregar al archivo de Intruccciones
						//Re-escribir en archivo
						arch2 = new File(ruta + ".inst");
						fw = new FileWriter(arch2,true);
						bw = new BufferedWriter(fw);
						pw = new PrintWriter(bw);
						
						bw.append(String.valueOf(cont) + "\tNULL" + "\t" + objeto_evaluar.codop + "\t\tNULL" + "\r\n");
						
						pw.close();
						bw.close();
					}
				}
				else{
					//linea_error = objeto_errores.Error(objeto_evaluar.codop);
					
					//System.out.println("-------------------------------------------------------------->> " + objeto_evaluar.codop);
					
					arch3 = new File(ruta + ".err");
					fw2 = new FileWriter(arch3,true);
					bw2 = new BufferedWriter(fw2);
					pw2 = new PrintWriter(bw2);
					
					//System.out.println("-------------------------------------------------------------->> UN TOKEN");

					bw2.append(cont + "\tCODOP" + "\tEspacio o tabulación faltante antes de la sentencia. Debe ser CODOP.\r\n");
					
					pw2.close();
					bw2.close();
				}
			}//IF tokens.countTokens == 1
			
			if(tokens.countTokens() > 3){
				arch3 = new File(ruta + ".err");
				fw2 = new FileWriter(arch3,true);
				bw2 = new BufferedWriter(fw2);
				pw2 = new PrintWriter(bw2);

				bw2.append(cont + "\tNULL" + "\tNúmero de sentencias superior al aceptado.\r\n");
				
				pw2.close();
				bw2.close();
			}
			
			
		}catch(Exception e){
			System.out.println("ERROR: Archivo no encontrado! AQUI>>");
		}
		return end;
	}
}