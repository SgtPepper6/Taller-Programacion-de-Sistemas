package Practica1;

import java.util.*;


///*** Clase Principal ***///
public class Prc1 {	
	//PRINCIPAL
	public static void main(String [] args){
		//Entrada
		Scanner sc = new Scanner(System.in);
		String str, ruta;
		
		System.out.println("->Ruta: ");
		ruta = sc.nextLine();
		//ruta = "C:\\Users\\borrega\\Documents\\CUCEI\\5to\\Taller de Programación de Sistemas\\Programas\\Practica_1\\Ensamblador.txt";
		
		if(!ruta.endsWith(".asm")){
			System.out.println("ERROR: Verifica la ruta.\n\tLa extensión debe ser .asm");
		}
		else{
		
			//Objeto
			Flujo objeto = new Flujo(ruta);
			
			objeto.existe = objeto.Existe(ruta);
			
			System.out.println("->Existe: " + objeto.existe);
			
			if(objeto.existe){
				str = ruta;
				str = str.substring(0, (str.length()-4));
				System.out.println(str);
				ruta = str;
				
				objeto.Lectura(ruta);
				System.out.println("->Dentro del IF lectura");
			}else{
				System.out.println("ERROR: La ruta " + ruta + " no es válida.");
			}
		}
	}//MAIN
}//PRC1
