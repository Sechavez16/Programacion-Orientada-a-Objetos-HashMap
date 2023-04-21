/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.grupobancos.hashmap;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
public class GrupoBancosHashMap {
    
    public static void main(String[] args) {
        
        }
    }
//Clase usuario 
class Usuario{
    private int identificacion;
    private int balance;
    public Usuario(int identificacion, int balance ){
        this.identificacion=identificacion;
        this.balance=balance;
    }
    public void setBalance(int balance){
        this.balance=balance;
    }
    public int getIdentificacion(){
        return this.identificacion;
    }
}
//Clase Banco
class Banco{
    private String codBanco;
    private HashMap<Integer,Usuario> hmUsuarios = new HashMap();
    public Banco(String codBanco){
        this.codBanco=codBanco;
    }
    public String getCodBanco(){
        return this.codBanco;
    }
    public void addUsuario(Usuario usuario){
        //La clave de cada usuario va ser la identifiacion
        hmUsuarios.put(usuario.getIdentificacion(), usuario);//el metodo put añade clave y un valor 
    }
    public Usuario getUsuario(int Identificacion){
        return hmUsuarios.get(Identificacion);
    }
}
//class grupo bancos
class GrupoBancos extends Usuario{
    private String grupo;
    private HashMap<String, Banco> hmBancos= new HashMap();
    public GrupoBancos(int Identificacion, int Balance, String grupo){
        super(Identificacion, Balance);
        this.grupo=grupo; 
    }
    public void addBancos(Banco banco){
        hmBancos.put(banco.getCodBanco(), banco);//supongo que el key HashMap de los bancos es el codBanco de Banco
    }
    public Usuario getUsuario(int identificacion){
        //Este es el mas compliacdo por que no tiene el key de hmBancos
        // Pero leyendo la documentacion la funcion entrySet me devuelve el key y el valor por separado y necesitamos el valor que es el banco
              for (Map.Entry<String, Banco> entry : hmBancos.entrySet()){//Iterramos soobre banco con la funcion EntrySet() paar solo obtener el valor
                    Banco auxBanco=entry.getValue();
                    if(auxBanco.getUsuario(identificacion)!=null){//Si es diferente null es por que hay u usuraio con ese valor
                        return auxBanco.getUsuario(identificacion);//y lo retornamos
                    }
                }
        return null;

    }
    public Usuario getUsuario(String codBanco, int identificacion){
        return hmBancos.get(codBanco).getUsuario(identificacion);

    }
}
//class cajero
class Cajero extends GrupoBancos{
    private String codCajero;
    private HashMap<Integer,Integer> hmBilletes = new HashMap();
    private boolean txEnProgreso;
    private Usuario txUsuario;
    public Cajero(int Identificacion, int Balance, String grupo, String codCajero, HashMap<Integer,Integer> hmBilletes){
        super(Identificacion, Balance, grupo);
        this.codCajero=codCajero;
        this.hmBilletes=hmBilletes;
    }
    public boolean iniciarSesion(GrupoBancos grupoBancos, int identificacionUsuario){
        if(grupoBancos.getUsuario(identificacionUsuario)!=null){
            return true;
        }
        return false;
    }
    public boolean iniciarSesion(GrupoBancos grupoBancos, String codBanco, int identificacionUsuario){
        if(grupoBancos.getUsuario(codBanco, identificacionUsuario)!=null){
            return true;
        }
        return false;
    }
    public boolean retirarDinero(int valorRetirar){
        if(valorRetirar%10==0 && getTotalEfectivoDisponible()>=valorRetirar){//Validamos que sea multiplo de 10 y usamos la funcion getTotalDisponible
            return true;
        }
        return false;
    }
    private int getTotalEfectivoDisponible(){
        int b10=hmBilletes.get(10),b20=hmBilletes.get(20),b50=hmBilletes.get(50),b100=hmBilletes.get(100);//Declaramos y asignamos valor
        return (b10*10)+(b20*20)+(b50*50)+(b100*100);//Multiplicamos el numeor de billete de esa denominacion y los sumamos
    }
}