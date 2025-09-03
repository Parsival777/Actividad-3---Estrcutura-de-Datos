package Actividad3;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SubsetSumInteractivo {

    // Método recursivo para verificar si existe un subconjunto que sume el objetivo
    public static boolean subsetSum(int[] conjunto, int objetivo, int indice) {
        // Caso base: si el objetivo es 0, se encontró una solución
        if (objetivo == 0)
            return true;
        // Si se recorrieron todos los elementos y no se encontró solución
        if (indice >= conjunto.length)
            return false;
        // Si el objetivo se vuelve negativo, no es una solución válida
        if (objetivo < 0)
            return false;

        // Llamadas recursivas: incluir el elemento actual o no incluirlo
        return subsetSum(conjunto, objetivo - conjunto[indice], indice + 1) ||
                subsetSum(conjunto, objetivo, indice + 1);
    }

    // Método recursivo para encontrar todos los subconjuntos que sumen el objetivo
    public static void encontrarSubconjuntos(int[] conjunto, int objetivo, int indice,
            List<Integer> subconjuntoActual, List<List<Integer>> resultados) {
        // Si se alcanza el objetivo, agregar el subconjunto a los resultados
        if (objetivo == 0) {
            resultados.add(new ArrayList<>(subconjuntoActual));
            return;
        }
        // Si se recorrieron todos los elementos o el objetivo es negativo, terminar
        if (indice >= conjunto.length || objetivo < 0)
            return;

        // Incluir el elemento actual en el subconjunto
        subconjuntoActual.add(conjunto[indice]);
        encontrarSubconjuntos(conjunto, objetivo - conjunto[indice], indice + 1, subconjuntoActual, resultados);
        // Retroceder: quitar el último elemento agregado (backtracking)
        subconjuntoActual.remove(subconjuntoActual.size() - 1);

        // No incluir el elemento actual en el subconjunto
        encontrarSubconjuntos(conjunto, objetivo, indice + 1, subconjuntoActual, resultados);
    }

    // Método recursivo para leer los números del conjunto
    public static void leerConjuntoRecursivo(Scanner scanner, int[] conjunto, int indice) {
        // Caso base: si se leyeron todos los elementos
        if (indice >= conjunto.length)
            return;

        System.out.print("Número " + (indice + 1) + ": ");

        // Verificar si la entrada es un número entero
        if (scanner.hasNextInt()) {
            int numero = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            conjunto[indice] = numero;
            // Llamada recursiva para leer el siguiente número
            leerConjuntoRecursivo(scanner, conjunto, indice + 1);
        } else {
            // Manejar entrada inválida
            System.out.println("Error: Debe ingresar un número entero válido.");
            scanner.nextLine(); // Limpiar entrada inválida
            // Reintentar leer el mismo índice
            leerConjuntoRecursivo(scanner, conjunto, indice);
        }
    }

    // Método para crear y llenar el array del conjunto
    public static int[] leerConjunto(Scanner scanner, int cantidad) {
        int[] conjunto = new int[cantidad];
        System.out.println("Ingrese los " + cantidad + " números del conjunto:");
        leerConjuntoRecursivo(scanner, conjunto, 0);
        return conjunto;
    }

    // Método recursivo para leer la cantidad de números del conjunto
    public static int leerCantidad(Scanner scanner) {
        System.out.print("¿Cuántos números desea ingresar en el conjunto? ");

        if (scanner.hasNextInt()) {
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            // Validar que la cantidad sea positiva
            if (cantidad <= 0) {
                System.out.println("Error: La cantidad debe ser un número mayor a 0.");
                return leerCantidad(scanner); // Reintentar
            }
            return cantidad;
        } else {
            // Manejar entrada inválida
            System.out.println("Error: Debe ingresar un número entero válido.");
            scanner.nextLine(); // Limpiar entrada inválida
            return leerCantidad(scanner); // Reintentar
        }
    }

    // Método recursivo para leer el valor objetivo
    public static int leerObjetivo(Scanner scanner) {
        System.out.print("Ingrese el valor objetivo a sumar: ");

        if (scanner.hasNextInt()) {
            int objetivo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return objetivo;
        } else {
            // Manejar entrada inválida
            System.out.println("Error: Debe ingresar un número entero válido.");
            scanner.nextLine(); // Limpiar entrada inválida
            return leerObjetivo(scanner); // Reintentar
        }
    }

    // Método para mostrar los resultados de la búsqueda
    public static void mostrarResultados(int[] conjunto, int objetivo, List<List<Integer>> resultados) {
        System.out.println("\n=== RESULTADOS ===");
        System.out.println("Conjunto: " + java.util.Arrays.toString(conjunto));
        System.out.println("Objetivo: " + objetivo);

        // Verificar si se encontraron soluciones
        if (resultados.isEmpty()) {
            System.out.println("No existe ningún subconjunto que sume " + objetivo);
        } else {
            System.out.println("Se encontraron " + resultados.size() + " subconjunto(s):");
            mostrarResultadosRecursivo(resultados, 0);
        }
    }

    // Método recursivo para mostrar todos los subconjuntos encontrados
    public static void mostrarResultadosRecursivo(List<List<Integer>> resultados, int indice) {
        // Caso base: si se mostraron todos los subconjuntos
        if (indice >= resultados.size())
            return;

        List<Integer> subconjunto = resultados.get(indice);
        // Calcular la suma del subconjunto actual
        int suma = calcularSumaRecursivo(subconjunto, 0, 0);
        // Mostrar el subconjunto y su suma
        System.out.println((indice + 1) + ". " + subconjunto + " = " + suma);
        // Llamada recursiva para mostrar el siguiente subconjunto
        mostrarResultadosRecursivo(resultados, indice + 1);
    }

    // Método recursivo para calcular la suma de una lista
    public static int calcularSumaRecursivo(List<Integer> lista, int indice, int sumaAcumulada) {
        // Caso base: si se recorrieron todos los elementos
        if (indice >= lista.size())
            return sumaAcumulada;
        // Llamada recursiva sumando el elemento actual
        return calcularSumaRecursivo(lista, indice + 1, sumaAcumulada + lista.get(indice));
    }

    // Método recursivo para preguntar si se desea continuar
    public static boolean preguntarContinuar(Scanner scanner) {
        System.out.print("\n¿Desea resolver otro problema? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        if (respuesta.equals("s")) {
            return true;
        } else if (respuesta.equals("n")) {
            return false;
        } else {
            // Manejar entrada inválida
            System.out.println("Error: Opción no válida. Por favor, ingrese 's' para sí o 'n' para no.");
            return preguntarContinuar(scanner); // Reintentar
        }
    }

    // Método principal que ejecuta el programa
    public static void ejecutarPrograma(Scanner scanner) {
        System.out.println("\n=== SUBSET SUM INTERACTIVO ===");
        // Leer la cantidad de números
        int cantidad = leerCantidad(scanner);
        // Leer los números del conjunto
        int[] conjunto = leerConjunto(scanner, cantidad);
        // Leer el valor objetivo
        int objetivo = leerObjetivo(scanner);

        // Lista para almacenar los subconjuntos que suman el objetivo
        List<List<Integer>> resultados = new ArrayList<>();
        // Encontrar todos los subconjuntos válidos
        encontrarSubconjuntos(conjunto, objetivo, 0, new ArrayList<>(), resultados);
        // Mostrar los resultados
        mostrarResultados(conjunto, objetivo, resultados);
    }

    // Método principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Ejecutar el programa una vez
        ejecutarPrograma(scanner);

        // Preguntar si se desea continuar y ejecutar nuevamente si es necesario
        while (preguntarContinuar(scanner)) {
            ejecutarPrograma(scanner);
        }

        System.out.println("¡Gracias por usar el programa!");
        scanner.close();
    }
}