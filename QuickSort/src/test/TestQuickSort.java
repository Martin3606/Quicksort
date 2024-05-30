package test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import datos.Funciones;
import datos.QuickSortMutliThreading;

public class TestQuickSort {
	
	public static void main(String args[]) {
		
		// Tamaño del vector
		int n = 1000000;
		// Cantidad de veces que se ejecutarán los metodos
		int repeticiones = 10;
		// Vector original
		int array[] = Funciones.generarArrayAleatorio(n, -1000, 1000);
		// Variables para calcular el tiempo tardado de cada metodo de ordenamiento
		double inicio;
		double fin;
		// Variables para calcular el tiempo tardado
		double totalC = 0;
		double totalS = 0;
		
		for(int i = 0; i < repeticiones; i++) {
			// Tiempo de inicio en milisegundos
			inicio = System.nanoTime() / 1000000;
			// Generacion del vector a ordenar
			int arrConcurrente[] = Arrays.copyOf(array, array.length);
			
			// Forkjoin ThreadPool para mantener
			// la creacion de los hilos según los recursos
			ForkJoinPool pool = ForkJoinPool.commonPool();
			
			// Comienza el primer hilo del fork join pool
			// en el rango de 0 a n-1
			pool.invoke(new QuickSortMutliThreading(0, n - 1, arrConcurrente));
			// Tiempo final en milisegundos
			fin = System.nanoTime() / 1000000 - inicio;
			// Para promediar la velocidad de los algoritmos
			totalC += fin;
			System.out.print("Soy QuickShort CONCURRENTE y me tardé: " + fin + " milisegundos\n");
		}
		
		System.out.println();
		
		for(int i = 0; i < repeticiones; i++) {
			// Tiempo de inicio en milisegundos
			inicio = System.nanoTime() / 1000000;
			// Generacion del vector a ordenar
			int arrayCopiaSeleccion[] = Arrays.copyOf(array, array.length);
			// Ejecucicion del metodo de ordenamiento secuencial
			Funciones.quickSort(arrayCopiaSeleccion, 0, arrayCopiaSeleccion.length - 1);
			// Tiempo final en milisegundos
			fin = System.nanoTime() / 1000000 - inicio;
			// Para promediar la velocidad de los algoritmos
			totalS += fin;
			System.out.print("Soy QuickShort SECUENCIAL y  me tardé: " + fin + " milisegundos\n");
		}
		// Cálculo de tiempos promedio 
		totalC /= repeticiones;
		totalS /= repeticiones;
		
		System.out.println("\nEn promedio el algoritmo CONCURRENTE se ha tardado: " + totalC + " milisegundos");
		System.out.println("En promedio el algoritmo SECUENCIAL se ha tardado: " + totalS + " milisegundos");
		
		if(totalC > totalS) {
			System.out.println("							GANÓ SECUENCIAL");
		}else
			System.out.println("							GANÓ CONCURRENTE");
		
		//en 10.000 gana concurrente muy pocas veces, 1 de cada 5000 veces
		
	}
	
}
