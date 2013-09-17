package Practica1;

/*** Evaluar ***/
public class Evaluar{
	//----------->
	String etq, codop, oper;
	boolean betq, bcodop, boper;
	String ini_linea;

	//Combinaciones validas
	int combinaciones[][] = {{1,2,3},{1,2,0},{0,2,3},{0,2,0}};
	char orden[] = new char[3];
	
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