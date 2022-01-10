package threads;

import java.awt.geom.*;
import java.util.*;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/*
 * Video 168 Threads: Aplicacion Monotarea que crea una pelota que rebota por los 
 * borders de una lamina. vamos convertirlo en multitarea, que use varios hilos de ejecucion
 * Video 169 Inturrumpir la ejecucion de un hilo
 * Video 170 Interrupcion de varios hilos
 */

public class usoThreads2 {

	public static void main(String[] args) {
		
		JFrame marco1 = new MarcoRebote1();
		marco1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco1.setVisible(true);
	}

}

//Threads
class PelotaHilos1 implements Runnable{
	
	public PelotaHilos1 (Pelota1 unaPelota, Component unComponente) {//pelota y lamina
		pelota=unaPelota;
		componente=unComponente;
	}
	
	@Override
	public void run() {
		
		System.out.println("Estado del hilo al comenzar: "+Thread.currentThread().isInterrupted());
		//while(!Thread.interrupted()) {//Mientras no haya sido interrumpido el hilo haz ...
		while(!Thread.currentThread().isInterrupted()) {// Interrumpe el hilo actual
			pelota.mueve_pelota(componente.getBounds());
			componente.paint(componente.getGraphics());//repinta la lamina
		}
		System.out.println("Estado del hilo al finalizar: "+Thread.currentThread().isInterrupted());
	}
	
	private Pelota1 pelota;
	private Component componente;
}
//Movimiento de la pelota
class Pelota1 {
	//Mueve la pelota invirtiendo posicion si bota con los limites
	public void mueve_pelota(Rectangle2D limites) {//recibe las dimensiones de nuestra lamina
		x+=dx;
		y+=dy;
		
		if (x<limites.getMinX()) {
			x=limites.getMinX();
			dx=-dx;//invertimos la x
		}
		
		if(x + TAMX>=limites.getMaxX()) {
			x=limites.getMaxX() -TAMX;
			dx =-dx;
		}
		
		if (y<limites.getMinY()) {
			y=limites.getMinY();
			dy=-dy; // invertimos la Y
		}
		
		if(y + TAMY>=limites.getMaxY()) {
			y=limites.getMaxY() -TAMY;
			dy =-dy;
		}
	}
	
	//Forma de la pelota en su posicion inicial
	public Ellipse2D getShape() {
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
	}
	
	private static final int TAMX=15;
	private static final int TAMY=15;
	private double x=0;
	private double y=0;
	private double dx=1;
	private double dy=1;
}

//Lamina que dibuja las pelotas
class LaminaPelota1 extends JPanel{
	//A�adimos pelota a la lamina
	public void add(Pelota1 b) {
		pelotas1.add(b);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		for(Pelota1 b:pelotas1) {
			g2.fill(b.getShape());
		}
	}
	
	private ArrayList<Pelota1> pelotas1= new ArrayList<Pelota1>(); 
}

//Marco
class MarcoRebote1 extends JFrame{
	public MarcoRebote1() {
		setBounds(600,300,400,350);
		setTitle("Rebotes");
		lamina=new LaminaPelota1();
		add(lamina,BorderLayout.CENTER);
		JPanel laminaBotones1=new JPanel();
		//boton comenzar
		ponerBoton(laminaBotones1,"Dale!",new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				comienza_el_juego();
			}
		});
		//Boton salir
		ponerBoton(laminaBotones1,"Salir",new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				System.exit(0);
			}
		});
		//Boton detener
		ponerBoton(laminaBotones1,"Detener",new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				detener();;
			}
		});
		add(laminaBotones1,BorderLayout.SOUTH);
	}
	
	//ponemos botones
	public void ponerBoton(Container c,String titulo,ActionListener oyente) {
		JButton boton=new JButton(titulo);
		c.add(boton);
		boton.addActionListener(oyente);
	}
	
	//A�ade pelota y la bota 3000 veces
	public void comienza_el_juego() {
		Pelota1 pelota=new Pelota1();
		lamina.add(pelota);
		//Multitarea
		Runnable r= new PelotaHilos1(pelota,lamina);
		t= new Thread(r);
		t.start();
		
		//MonoTarea
//		for(int i=1;i<3000;i++) {
//			pelota.mueve_pelota(lamina.getBounds());
//			lamina.paint(lamina.getGraphics());//repinta la lamina
//			try {
//				Thread.sleep(4);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			
//		}
		
	}
	//Detener hilo
	public void detener() {
		//t.stop(); //Obsoleto
		//Cuando hacemos una peticion de interrupcion lanza una interrupteException
		t.interrupt();
	}
	private LaminaPelota1 lamina;
	private Thread t;
}
