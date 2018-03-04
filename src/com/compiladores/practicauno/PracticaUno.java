
package com.compiladores.practicauno;

import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class PracticaUno {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<AFN> fi = new ArrayList<>();
        int option=-1;
        Teclado teclado = new Teclado();
        
        do{
            System.out.println("\nSelecciona una opcion. \n");
            System.out.println("1. Crea un AFN basico");
            System.out.println("2. Unir dos AFN");
            System.out.println("3. Concatenar dos AFN");
            System.out.println("4. Operacion cerradura (+)");
            System.out.println("5. Operacion cerradura (*)");
            System.out.println("6. Operacion opcional (?)");
            System.out.println("7. Validar cadena afn");
            System.out.println("8. Union para analisis lexico");
            System.out.println("9. Convertir a afd");
            System.out.println("10. Validar cadena afd");
            System.out.println("11. Salir");
            option = teclado.getEntero();
            int id1, id2;
            switch(option){
                case 1:
                    System.out.println("Crear un AFN Basico.");
                    System.out.print("Simbolo: ");
                    char s = teclado.getCadena().charAt(0);
                    AFN afn = new AFN(s);
                    fi.add(afn);
                    System.out.println("AFN basico creado. (ID: "+afn.idAutomata+")");
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException ie){
                        System.err.println(ie.getStackTrace());
                    }
                    break;
                case 2:
                    System.out.println("Unir dos AFN.");
                    
                    System.out.print("Teclea el id del primer AFN (AFN Destino): ");
                    id1 = teclado.getEntero();
                    System.out.print("Teclea el id del segundo AFN (AFN a unir): ");
                    id2 = teclado.getEntero();
                    
                    if(fi.size()>1)
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                for(int j=0; j<fi.size(); j++){
                                    if(fi.get(j).idAutomata==id2){
                                        fi.set(i, fi.get(i).unirAFN(fi.get(j)));
                                        System.out.println("Operación finalizada");
                                    }
                                }
                            } 
                        }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");                    
                    break;
                
                case 3:
                    System.out.println("Concatenar dos AFN.");
                    System.out.print("Teclea el id del primer AFN (AFN Destino): ");
                    id1 = teclado.getEntero();
                    System.out.print("Teclea el id del segundo AFN (AFN a unir): ");
                    id2 = teclado.getEntero();
                    
                    if(fi.size()>1)
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                for(int j=0; j<fi.size(); j++){
                                    if(fi.get(j).idAutomata==id2){
                                        fi.set(i, fi.get(i).concatenarAFN(fi.get(j)));
                                        System.out.println("Operación finalizada");
                                    }
                                }
                            } 
                        }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");                         
                    break;
                
                case 4:
                    System.out.println("Operacion Cerradura (+).");
                    System.out.print("Teclea el id del AFN: ");
                    id1 = teclado.getEntero();
                    if(fi.size()>0)
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                fi.set(i, fi.get(i).cerraduraPositiva());
                                System.out.println("Operación finalizada");
                            } 
                        }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");                       
                    break;
                    
                case 5:
                    System.out.println("Operacion Cerradura (*).");
                    System.out.print("Teclea el id del AFN: ");
                    id1 = teclado.getEntero();
                    if(fi.size()>0)
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                fi.set(i, fi.get(i).cerraduraKleene());
                                System.out.println("Operación finalizada");
                            } 
                        }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");  
                    break;
                
                case 6:
                    System.out.println("Operacion Opcional (?).");
                    System.out.print("Teclea el id del AFN: ");
                    id1 = teclado.getEntero();
                    if(fi.size()>0)
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                fi.set(i, fi.get(i).opcional());
                                System.out.println("Operación finalizada");
                            } 
                        }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");                      
                    break;

                case 7:
                    System.out.println("Validar cadena.");
                    System.out.print("Teclea la cadena a validar: ");
                    String cadena = teclado.getCadena();
                    System.out.print("Teclea el ID del automata: ");
                    id1 = teclado.getEntero();
                    if(fi.size()>0){
                        for(int i=0; i<fi.size(); i++){
                            if(fi.get(i).idAutomata == id1){
                                if(fi.get(i).validarCadena(cadena)){
                                    System.out.println("La cadena es aceptada por el automata.");
                                    break;
                                }
                                else{
                                    System.out.println("La cadena no es aceptada por el automata.");
                                    break;
                                }
                            }
                            else
                                if((i+1)==fi.size())
                                    System.out.println("Un automata con ese id no existe");
                        }
                    }
                    else
                        System.out.println("No hay suficientes AFN creados para realizar esta operacion.");                      
                    break;
                    
                case 8:
                    System.out.println("Union de AFN para analisis lexico.");
                    int l;
                    System.out.println("Cuantos AFN deseas unir?");
                    l = teclado.getEntero();
                    int[] idAfn = new int[l];
                    for(int i=0; i<l; i++){
                        System.out.println("Teclea el id del AFN "+i+": ");
                        idAfn[i] = teclado.getEntero();
                    }
                    AFN afnLex = new AFN();
                    afnLex = afnLex.unirAfnLex(fi, idAfn);
                    fi.add(afnLex);
                    System.out.println("AFN basico creado. (ID: "+afnLex.idAutomata+")");
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException ie){
                        System.err.println(ie.getStackTrace());
                    }
                    //fi.get(0).getAFD(fi.get(0));
                    break;
                
                case 9:
                    System.out.println("Hasta luego.");
                    //fi.get(0).getAFD(fi.get(0));
                    break;
                
                case 10:
                    System.out.println("Hasta luego.");
                    //fi.get(0).getAFD(fi.get(0));
                    break;
                
                case 11:
                    System.out.println("Hasta luego.");
                    //fi.get(0).getAFD(fi.get(0));
                    break;
                
                default:
                    System.out.println("Opcion invalida.");
                    
            }
            
        }while(option != 11);
        
    }
}
    

