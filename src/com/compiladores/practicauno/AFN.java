package com.compiladores.practicauno;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Alumno
 */
public class AFN {
    static int id;
    private int idAutomata;
    Estado estadoInicial;
    ArrayList<Character> alfabeto;
    ArrayList<Estado> estadoAceptacion;
    ArrayList<Estado> estados;
    
    public AFN(){
        id++;
        idAutomata = id;
        alfabeto = new ArrayList<>();
        alfabeto.clear();
        estadoAceptacion = new ArrayList<>();
        estadoAceptacion.clear();
        estados = new ArrayList<>();
        estados.clear();
        estadoInicial = null;
    }
    
    public AFN(char simbolo){

        id++;
        idAutomata = id;

        
        /* Cada automata tiene su conjunto de simbolos o alfabeto */
        alfabeto = new ArrayList<>();
        alfabeto.clear();
        alfabeto.add(simbolo);
        
        /* Cada automata esta conformado de un conjunto de estados */
        estados = new ArrayList<>();
        estados.clear();
        
        /* Cada automata cuenta con un conjunto de estados de estadoAceptacion */
        estadoAceptacion = new ArrayList<>();
        estadoAceptacion.clear();

        /* Cada automata cuenta con  al menos dos estados, el estado inicial y
        el estado de estadoAceptacion */
        Estado estadoFinal = new Estado();
        estadoFinal.estadoAceptacion = true;
        estadoAceptacion.add(estadoFinal);

        estadoInicial = new Estado();
        estadoInicial.addTransicion(simbolo, estadoFinal);
        
        
        /* Agregamos los nuevos estados a la lista de estados del automata */
        estados.add(estadoInicial);
        estados.add(estadoFinal);
    }
    
    public AFN(AFN afn){
        id++;
    
    }
    
    /*
        Definicion de operaciones
    */
    
    public AFN afnBasico(char s){
        
        estadoInicial = new Estado();
        Estado estadoFinal = new Estado();
        estadoFinal.estadoAceptacion = true;
        estadoInicial.addTransicion(s, estadoFinal);
        
        /* Validar que el usuario no meta caracteres duplicados al alfabeto */
        if(!inside(s)) alfabeto.add(s);
        
        estados.add(estadoInicial);
        estados.add(estadoFinal);
        
        estadoAceptacion.add(estadoFinal);        
        return this;
    }
    
    public AFN afnBasico(char simbMin, char simbMax){
        Estado estadoFinal;
        estadoInicial = new Estado();
        estadoFinal = new Estado();
        estadoFinal.estadoAceptacion = true;
        estadoInicial.addTransicion(simbMin, simbMax, estadoFinal);
        /* Validar que el usuario no meta caracteres duplicados al alfabeto */
        for(int i=simbMin; i<=simbMax; i++){
            if(!inside((char)i)) alfabeto.add((char)i);
        }
        estados.add(estadoInicial);
        estados.add(estadoFinal);
        estadoAceptacion.add(estadoFinal);        
        return this;
    }
    
    public AFN unirAFN(AFN f2){
        
        Estado nuevoInicio = new Estado();
        nuevoInicio.addTransicion(Epsilon.EPSILON, this.estadoInicial);
        nuevoInicio.addTransicion(Epsilon.EPSILON, f2.estadoInicial);
        
        Estado nuevoFin = new Estado();
        for(Estado e: this.estadoAceptacion){
            e.estadoAceptacion = false;
            e.addTransicion(Epsilon.EPSILON, nuevoFin);
        }
        this.estadoAceptacion.clear();
        for(Estado e: f2.estadoAceptacion){
            e.estadoAceptacion = false;
            e.addTransicion(Epsilon.EPSILON, nuevoFin);
        }
        f2.estadoAceptacion.clear();
        nuevoFin.estadoAceptacion = true;
        this.estadoAceptacion.add(nuevoFin);
        
        
        this.estadoInicial = nuevoInicio;
        this.estados.addAll(f2.estados);
        
        /* Validar que el usuario no meta caracteres duplicados al alfabeto */
        // Se puede utilizar la clase HashSet para optimizar ya que es poco eficiente este algoritmo */
        for(int i=0; i<f2.alfabeto.size();i++){
            if(!inside(f2.alfabeto.get(i))) this.alfabeto.add(f2.alfabeto.get(i));
        }
        
        this.estados.add(nuevoInicio);
        this.estados.add(nuevoFin);
//        f2 = null;
        return this;
    }
    
    public AFN concatenarAFN(AFN f2){
        
        
        /* 
            A cada estado de estadoAceptacion en f1 se anexan todas las trancisiones 
            del estado inicial de f2, idealmente solo hay un estado de acpt. en
            Thompson
        */
        
        for(Estado e: this.estadoAceptacion){
            for(Transicion t: f2.estadoInicial.transiciones){
                e.addTransicion(t.minSimb, t.maxSimb, t.estadoDestino);
            }
            e.estadoAceptacion = false;
        }
        this.estadoAceptacion.clear();
        this.estadoAceptacion.addAll(f2.estadoAceptacion);
        
        f2.estados.remove(f2.estadoInicial);

        /* Validar que el usuario no meta caracteres duplicados al alfabeto */
        // Se puede utilizar la clase HashSet para optimizar ya que es poco eficiente este algoritmo */
        for(int i=0; i<f2.alfabeto.size();i++){
            if(!inside(f2.alfabeto.get(i))) this.alfabeto.add(f2.alfabeto.get(i));
        }
        
        this.estados.addAll(f2.estados);
        return this;
    }
    
    public AFN cerraduraPositiva(){
        Estado nuevoInicio = new Estado();
        Estado nuevoFin = new Estado();
        
        nuevoInicio.addTransicion(Epsilon.EPSILON, estadoInicial);
        for(Estado e: estadoAceptacion){
            e.estadoAceptacion = false;
            e.addTransicion(Epsilon.EPSILON, nuevoFin);
            e.addTransicion(Epsilon.EPSILON, estadoInicial);
        }
        estadoAceptacion.clear();
        
        this.estadoInicial = nuevoInicio;
        
        estados.add(nuevoInicio);
        estados.add(nuevoFin);
        
        nuevoFin.estadoAceptacion = true;
        estadoAceptacion.add(nuevoFin);
        return this;
    }
    
    public AFN cerraduraKleene(){
        Estado nuevoInicio = new Estado();
        Estado nuevoFin = new Estado();
        
        nuevoInicio.addTransicion(Epsilon.EPSILON, estadoInicial);
        nuevoInicio.addTransicion(Epsilon.EPSILON, nuevoFin);
        for(Estado e: estadoAceptacion){
            e.estadoAceptacion = false;
            e.addTransicion(Epsilon.EPSILON, nuevoFin);
            e.addTransicion(Epsilon.EPSILON, estadoInicial);
        }
        estadoAceptacion.clear();
        
        this.estadoInicial = nuevoInicio;
        estados.add(nuevoInicio);
        estados.add(nuevoFin);
        
        nuevoFin.estadoAceptacion = true;
        estadoAceptacion.add(nuevoFin);
        
        return this;
    }
    
    public AFN opcional(){
        Estado nuevoInicio = new Estado();
        Estado nuevoFin = new Estado();
        
        nuevoInicio.addTransicion(Epsilon.EPSILON, estadoInicial);
        nuevoInicio.addTransicion(Epsilon.EPSILON, nuevoFin);
        for(Estado e: estadoAceptacion){
            e.estadoAceptacion = false;
            e.addTransicion(Epsilon.EPSILON, nuevoFin);
        }
        estadoAceptacion.clear();
        
        this.estadoInicial = nuevoInicio;
        estados.add(nuevoInicio);
        estados.add(nuevoFin);
        
        nuevoFin.estadoAceptacion = true;
        estadoAceptacion.add(nuevoFin);
        return this;
    }
    
    public boolean inside(Estado estado){
        for(int i=0; i<estados.size();i++){
            if(estados.get(i).equals(estado) ){
                return true;
            }
        }
        return false;
    }
    
    public static boolean inside(AFN afn, Estado estado){
        for(int i=0; i<afn.estados.size(); i++){
            if(afn.estados.get(i).equals(estado) ){
                return true;
            }
        }
        return false;
    }

    public boolean inside(char s){
        for(int i=0; i<alfabeto.size();i++){
            if(alfabeto.get(i) == s){
                return true;
            }
        }
        return false;
    }
    
    public static boolean inside(AFN f1, char s){
        for(int i=0; i<f1.alfabeto.size();i++){
            if(f1.alfabeto.get(i) == s){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Estado> cerraduraEpsilon(Estado e){
        ArrayList<Estado> C = new ArrayList();
        Stack<Estado> S = new Stack();
        Estado r;
        S.push(e);
        while(!S.empty()){
            r=S.pop();
            if(!r.isIncluded(C)){
                C.add(r);
            }
            for(Transicion i:r.transiciones){ /////////////// Checar todo el rango de simbolos
                if(i.minSimb==Epsilon.EPSILON||i.maxSimb==Epsilon.EPSILON){
                    S.push(i.estadoDestino);
                } //if
            }//for
        }//while
        return C;
    }
    
    public ArrayList<Estado> cerraduraEpsilon(ArrayList<Estado> E){
        ArrayList<Estado> C = new ArrayList();
            for(Estado e:E){
               C.addAll(cerraduraEpsilon(e));
            }//for
        return C;
    }
    
    public ArrayList<Estado> moverA(Estado e, char c){
        ArrayList<Estado> R = new ArrayList();
            for(Transicion t:e.transiciones){/////////////// Checar todo el rango de simbolos
               if(t.minSimb==c || t.maxSimb==c){
                    R.add(t.estadoDestino);
                } //if
            }//for
        return R;
    }
    
    public ArrayList<Estado> moverA(ArrayList<Estado> E, char c){
        ArrayList<Estado> R = new ArrayList();
            for(Estado e:E){
               R.addAll(moverA(e,c));
            }
        return R;
    }
            
    public ArrayList<Estado> irA(ArrayList<Estado> E, char c){
        ArrayList<Estado> R = new ArrayList();
            for(Estado e:E){
               R.addAll(moverA(e,c));
            }
        return cerraduraEpsilon(R);
    }
    
    public boolean validarCadena(String s){ ///////////////
//        ArrayList<Estado> R = new ArrayList();
        ArrayList<Estado> C = new ArrayList();
        int len = s.length(); 
        C=cerraduraEpsilon(this.estadoInicial);
        for(int i=0;i<len;i++){
            C=irA(C,s.charAt(i));
            if(C.isEmpty()){
                return false;
            }
        }
        for(Estado e:estadoAceptacion){
            if(e.isIncluded(C)){
                return true;
            }
        }
        return false;
    }
    
    /*
    public void verificarAnalizados(EstadoSi S0, int id, TablaTransiciones tabla, AFN f){
        EstadoSi S = new EstadoSi();
        boolean encontro = false;
        
        for (char E: f.alfabeto) {
            S.setEstados(irA(S0.getEstados(), E));
            if(S.getEstados() != null){
                for (EstadoSi edo:tabla.Estados){
                    if(edo.getEstados() == S.getEstados()){
                        encontro = true;
                        TransicionSi t = new TransicionSi(E, edo.Id);
                        S0.Transiciones.add(t);
                    }   
                }
                if(encontro == false){
                    id++;
                    S.Id=id;
                    for(Estado e: S.getEstados()){
                        if(e.estadoAceptacion==true){
                            S.setEstadoAceptacion(true);
                            S.setToken(e.token);
                        }
                    }
                    TransicionSi t = new TransicionSi(E,id);
                    S0.Transiciones.add(t);
                    tabla.Estados.add(S);
                    S.setEstados(null);
                }
            encontro=false;
            }
        }

        for (EstadoSi edo:tabla.Estados){
            if(edo.getEstados() == S0.getEstados()){
                edo.setAnalizado(true);    
                edo.Transiciones=S0.Transiciones;
            }
        }
        
        for(EstadoSi Es: tabla.Estados){
            if(Es.isAnalizado()==false){
                S0 = Es;
                verificarAnalizados(S0, id, tabla, f);
            }
        }
        
    }
    */
    /*
    public AFD convertirAFD(AFN f){
        int i=10, id = 0;
        boolean encontro = false;
        AFD afd = new AFD();
        TablaTransiciones tabla = new TablaTransiciones();
        EstadoSi S = new EstadoSi();
        EstadoSi S0 = new EstadoSi();
        
        tabla.alfabeto = f.alfabeto;
        S.estados=null;
        
        for(Estado E : f.estadoAceptacion){
            E.token= i;
            i+=10;
        }
        
        S0.estados = cerraduraEpsilon(f.estadoInicial);
        S0.Id = id;
        for(Estado e: S0.estados){
            if(e.estadoAceptacion==true){
                S0.estadoAceptacion=true;
                S0.token = e.token;
            }
        }
        tabla.Estados.add(S0);
        id++;
        
        verificarAnalizados(S0, id, tabla, f);
        
        int x,y,z;
        int fila = tabla.Estados.size();
        int columna = tabla.alfabeto.size(); 
        tabla.cuadro = new int[fila][columna];
        
        for(x=0;x<fila;x++){
            EstadoSi edo = tabla.Estados.get(x);
            for(y=0;y<edo.Transiciones.size();y++){
                TransicionSi t = edo.Transiciones.get(y);
                z=0;
                for(char c:tabla.alfabeto){
                    if(t.maxSimb == c){
                        tabla.cuadro[x][z]=t.estadoDestino;
                        z++;
                    }
                    else{
                        tabla.cuadro[x][z]= -1;
                        z++;
                    }
                }
            }
        }
        
        return afd;
    }
    */
    
    public AFD getAFD(AFN afn){
        
        AFD afd;
        int aux = 10;
        /* Asignacion de tokens */
        for(Estado estado: afn.estadoAceptacion){
            estado.setToken(aux);
            aux+=10;
        }
        
        /* 1. Se calcula la cerradura epsilon del estado inicial */
        EstadoSi s0 = new EstadoSi( cerraduraEpsilon(afn.estados) );
        
        ArrayList<EstadoSi> estadosSi = new ArrayList<>();
        estadosSi.add(s0);
        
        /* 2. Analizar s0 */
        //ArrayList<EstadoSi> estadosSi = analizarS0(s0);
        int oldSize= -1, contador=0;
        do{
            oldSize = estadosSi.size();
            estadosSi = analizarEstadoSi(estadosSi, contador++);
        }while( (contador+1) != estadosSi.size() );
        afd = new AFD(s0, estadosSi, afn.alfabeto);
        for(EstadoSi estadoSi :afd.estados){
            if(estadoSi.isEstadoAceptacion())
            System.out.println("estadoSi: "+estadoSi.getIdEstadoSi());
        }
        return afd;
    }
    
    public AFD getAFD(){
        
        AFD afd;
        int aux = 10;
        /* Asignacion de tokens */
        for(Estado estado: this.estadoAceptacion){
            estado.setToken(aux);
            aux+=10;
        }
        
        /* 1. Se calcula la cerradura epsilon del estado inicial */
        EstadoSi s0 = new EstadoSi( cerraduraEpsilon(this.estados) );
        
        ArrayList<EstadoSi> estadosSi = new ArrayList<>();
        estadosSi.add(s0);
        
        /* 2. Analizar s0 */
        //ArrayList<EstadoSi> estadosSi = analizarS0(s0);
        int oldSize= -1, contador=0;
        do{
            oldSize = estadosSi.size();
            estadosSi = analizarEstadoSi(estadosSi, contador++);
        }while( (contador+1) != estadosSi.size() );
        afd = new AFD(s0, estadosSi, this.alfabeto);
        for(EstadoSi estadoSi :afd.estados){
            if(estadoSi.isEstadoAceptacion()){
                System.out.println("estadoSi: "+estadoSi.getIdEstadoSi()+" token: "+estadoSi.getToken());
                afd.estadosAceptacion.add(estadoSi);
            }
        }
        return afd;
    }
    
    /**
     * 
     * @param estadosSi conjunto de estados Si
     * @param indice indice del elemento a ser analizado
     * @return la lista de estados resultante del analisis de s0
     */
    public ArrayList<EstadoSi> analizarEstadoSi(ArrayList<EstadoSi> estadosSi, int indice){

        for(Character c: this.alfabeto){
            EstadoSi estadoSi = new EstadoSi( irA(estadosSi.get(indice).getEstados(), c) );

            /* Validando que no existan estados iguales en esta lista */
            /*****Nota: hay que descartar el id como factor de comparacion */
            TransicionSi transicionSi;
            for(int i=0; i<estadosSi.size(); i++){
                if(estadosSi.get(i).equals(estadoSi)){
                    transicionSi = new TransicionSi(c, estadosSi.get(i));
                    estadosSi.get(indice).getTransiciones().add(transicionSi);
                    break;
                }
                if((i+1)==estadosSi.size()){
                    transicionSi = new TransicionSi(c, estadoSi);
                    estadosSi.get(indice).getTransiciones().add(transicionSi);
                    estadosSi.add(estadoSi);
                }
            }
        }
        estadosSi.get(indice).setAnalizado(true);
        return estadosSi;
    }
    
    /**
     * 
     * @param s0 etado inicial del automata finito determinista
     * @return la lista de estados resultante del analisis de s0
     */
    public ArrayList<EstadoSi> analizarS0(EstadoSi s0){
        ArrayList<EstadoSi> estadosSi = new ArrayList<>();
        estadosSi.add(s0);
        
            for(Character c: this.alfabeto){
                EstadoSi estadoSi = new EstadoSi( irA(s0.getEstados(), c) );
                
                /* Validando que no existan estados iguales en esta lista */
                /*****Nota: hay que descartar el id como factor de comparacion */
                for(int i=0; i<estadosSi.size(); i++){
                    if(!estadosSi.get(i).equals(estadoSi)){
                        estadosSi.add(estadoSi);
                    }
                }
            }
        return estadosSi;
    }

    public static AFN unirAfnLex(ArrayList<AFN> listaAfn, int[] idAfn){
        
        AFN afn = new AFN();
        Estado nuevoInicio = new Estado();
        afn.estadoInicial = nuevoInicio;
        afn.estados.add(nuevoInicio);
        for(int i=0; i<idAfn.length; i++){
            for(int j=0; j<listaAfn.size(); j++){
                if(listaAfn.get(j).idAutomata == idAfn[i])
                    afn = unirAfnLex(afn, listaAfn.get(j));
            }
        }
        return afn;
    }
    
    public static AFN unirAfnLex(AFN f1, AFN f2){
        
        
        //nuevoInicio.addTransicion(Epsilon.EPSILON, f1.estadoInicial);
        f1.estadoInicial.addTransicion(Epsilon.EPSILON, f2.estadoInicial);
        
        //f1.estadoInicial = nuevoInicio;
        for (Estado estado : f2.estados) {
            if( !inside(f1, estado) ) f1.estados.add(estado);
            f1.estados.addAll(f2.estados);
        }
        f1.estadoAceptacion.addAll(f2.estadoAceptacion);
        /* Validar que el usuario no meta caracteres duplicados al alfabeto */
        // Se puede utilizar la clase HashSet para optimizar ya que es poco eficiente este algoritmo */
        for(int i=0; i<f2.alfabeto.size();i++){
            if(!inside(f1, f2.alfabeto.get(i))) f1.alfabeto.add(f2.alfabeto.get(i));
        }
        //f1.estados.add(nuevoInicio);
        return f1;
    }    

    public int getIdAutomata() {
        return idAutomata;
    }
}
