package datos;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class QuickSortMutliThreading extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	int start, end;
	int[] arr;

	// Constructor QuickSort
	public QuickSortMutliThreading(int start, int end, int[] arr) {
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	private int partition(int start, int end, int[] arr) {

		int i = start, j = end;

		// Elegir un pivote aleatorio
		int pivoted = new Random().nextInt(j - i) + i;

		// Intercambiar el pivote con
		// el último elemento del array

		int t = arr[j];
		arr[j] = arr[pivoted];
		arr[pivoted] = t;
		j--;

		// Empieza a particionar
		while (i <= j) {

			if (arr[i] <= arr[end]) {
				i++;
				continue;
			}

			if (arr[j] >= arr[end]) {
				j--;
				continue;
			}

			t = arr[j];
			arr[j] = arr[i];
			arr[i] = t;
			j--;
			i++;
		}

		// Cambiar el pivote
		// a su correcta ubicacion
		t = arr[j + 1];
		arr[j + 1] = arr[end];
		arr[end] = t;
		return j + 1;
	}

	@Override
	protected Integer compute() {
		// Si es suficientemente pequeño, no se hace nada
		if (start >= end)
			return null;

		// Realiza la particion
		int p = partition(start, end, arr);
		// Dividir el vector
		// Se crean 2 subproblemas
		QuickSortMutliThreading left = new QuickSortMutliThreading(start, p - 1, arr);

		QuickSortMutliThreading right = new QuickSortMutliThreading(p + 1, end, arr);

		// El subvector izquierdo lo realiza el hilo fork
		left.fork();
		// El subvector derecho lo realiza el hilo actual (compute)
		right.compute();

		// Esperar hasta que el hilo izquierdo se complete
		left.join();

		// No hace falta devolver nada
		return null;
	}

}
