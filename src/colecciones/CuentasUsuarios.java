package colecciones;

import java.util.*;

/* 
 * Video 180 Uso de colecciones
 * Aplicacion Banco que usa una coleccion para almacenar los clientes
 * Los getter y setter los generamos directamente con source-> Generate GyS
 * Coleccion que no repita clientes, muchas operaciones de agregar y borrar, muchas solo lectura
 * En principio no necesitamos ordenacion para este ejemplo.
 * Video 181: Equals() y HasCode()
 * Video 182: Rescribiendo Equals() y HasCode()
 * Video 183: Iteradores
 */

public class CuentasUsuarios {

	public static void main(String[] args) {
		Cliente cl1 = new Cliente("Antonio Banderas","00001",200000);
		Cliente cl2 = new Cliente("Rafael Nadal","00002",250000);
		Cliente cl3 = new Cliente("Penelope Cruz","00003",300000);
		Cliente cl4 = new Cliente("Julio Iglesias","00004",500000);
		//No lo agrega, duplicado tras sobrescribir equals() y hashCode()
		Cliente cl5 = new Cliente("Antonio Banderas","00001",200000);
		
		//Setes Interfaces, tenemos que implementar la clase que la implemente 
		//en este caso nos conviene HashSet (R�pida, no duplicados, no ordenaci�n, y sin acceso aleatorio)
		Set <Cliente> clientesBanco = new HashSet<Cliente>();
		//El metodo add gestiona los duplicados
		clientesBanco.add(cl1);
		clientesBanco.add(cl2);
		clientesBanco.add(cl3);
		clientesBanco.add(cl4);
		clientesBanco.add(cl5);
		
		//Recorrer con bucle for each la coleccion, la imprime y elimina uno de ellos
		for (Cliente cliente: clientesBanco) {

			if (cliente.getNombre().equals("Julio Iglesias")) {
				//clientesBanco.remove(cliente); //Lanza una excepcion, no se puede eliminar en un bucle
			}
			System.out.println(cliente.getNombre()+" "
			+cliente.getN_cuenta()+" "+cliente.getSaldo());
			
		}
		
		//recorrer una coleccion con Iterator,  e imprime los nombre
		Iterator<Cliente> it= clientesBanco.iterator();
		while(it.hasNext()) {//Mientras halla elementos siguientes
			//Hasta que no vayamos al siguiente elemento , no podemo saber el nombre del anterior
			//Primero hay que saltar
			String nombre_cliente = it.next().getNombre();
			System.out.println(nombre_cliente);
			
		}
		
		Iterator<Cliente> it2= clientesBanco.iterator();//Creamos otro iterator el anterior ya no sirve
		while(it2.hasNext()) {
		// Creamos una copia del objeto y podemos tratar los diferentes campos con el
		Cliente c = it2.next();
		System.out.println(c.getNombre().concat(" "+c.getN_cuenta().concat(" "+c.getSaldo())));
		}
	}

}
