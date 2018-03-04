package com.compiladores.practicauno;


import java.util.Scanner;

public class Teclado{
	
	private Scanner scanner;
	private double numDouble;
	private int numInteger;
        private String cadena;
	
	public Teclado(){
            scanner = new Scanner(System.in);
	}
	
	public int getEntero() {
            numInteger = scanner.nextInt();
            return numInteger;
	}

	public double getDouble() {	
            numDouble = scanner.nextDouble();
            return numDouble;
	}
        
        public String getCadena(){
            cadena = scanner.next();
            return cadena;
        }
		
	protected void finalize(){
		scanner = null;
                cadena = null;
		System.gc();
	}
		
		
}