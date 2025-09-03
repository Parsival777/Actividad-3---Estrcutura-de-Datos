package Actividad3;

import java.util.Scanner;

public class FibonacciRecursivo {
    private static final int LIMITE_MAXIMO = 44; // Límite máximo permitido para evitar problemas de rendimiento

    // Método recursivo para calcular el término n de la serie de Fibonacci
    public static int fibonacci(int n) {
        if (n <= 0) // Caso base: si n es 0 o menor, retorna 0
            return 0;
        if (n == 1) // Caso base: si n es 1, retorna 1
            return 1;
        // Llamada recursiva para calcular el término actual
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Método recursivo para mostrar la serie de Fibonacci hasta el término n
    public static void mostrarSerie(int n, int current) {
        if (current <= n) { // Mientras el término actual sea menor o igual a n
            System.out.println("F(" + current + ") = " + fibonacci(current)); // Imprime el término actual
            mostrarSerie(n, current + 1); // Llama al siguiente término
        }
    }

    // Método para leer la entrada del usuario
    public static int leerEntrada(Scanner scanner) {
        System.out.println("=== SERIE DE FIBONACCI RECURSIVA ===");
        System.out.println("NOTA: Por limitaciones de rendimiento, el máximo permitido es " + LIMITE_MAXIMO + ".");
        // Solicita al usuario el número de términos de Fibonacci que desea calcular
        return leerNumero(scanner,
                "Ingrese el número de términos de Fibonacci que desea calcular (0-" + LIMITE_MAXIMO + "): ");
    }

    // Método para leer un número entero válido del usuario
    public static int leerNumero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);

        if (scanner.hasNextInt()) { // Verifica si la entrada es un número entero
            int numero = scanner.nextInt();
            scanner.nextLine(); // Limpia el buffer

            if (numero < 0) { // Verifica si el número es negativo
                System.out.println("Error: Debe ingresar un número no negativo.");
                return leerNumero(scanner, "Por favor, ingrese un número válido (0-" + LIMITE_MAXIMO + "): ");
            }
            if (numero > LIMITE_MAXIMO) { // Verifica si el número excede el límite máximo
                System.out.println("Error: El número ingresado excede el límite máximo de " + LIMITE_MAXIMO + ".");
                return leerNumero(scanner, "Por favor, ingrese un número válido (0-" + LIMITE_MAXIMO + "): ");
            }
            return numero; // Retorna el número válido
        } else { // Si la entrada no es un número entero
            System.out.println("Error: Debe ingresar un número entero válido.");
            scanner.nextLine(); // Limpia el buffer
            return leerNumero(scanner, "Por favor, ingrese un número entero: ");
        }
    }

    // Método para preguntar al usuario si desea continuar calculando otra serie
    public static void preguntarContinuar(Scanner scanner) {
        System.out.print("\n¿Desea calcular otra serie? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase(); // Lee la respuesta y la convierte a minúsculas

        if (respuesta.equals("s")) { // Si la respuesta es "s", reinicia el proceso
            procesarFibonacci(scanner);
        } else if (respuesta.equals("n")) { // Si la respuesta es "n", finaliza el programa
            System.out.println("¡Gracias por usar el programa!");
        } else { // Si la respuesta no es válida, solicita nuevamente
            System.out.println("Error: Opción no válida.");
            preguntarContinuar(scanner);
        }
    }

    // Método principal para procesar la serie de Fibonacci
    public static void procesarFibonacci(Scanner scanner) {
        int n = leerEntrada(scanner); // Lee el número de términos de Fibonacci
        System.out.println("\nSerie de Fibonacci hasta F(" + n + "):");
        mostrarSerie(n, 0); // Muestra la serie desde el término 0
        preguntarContinuar(scanner); // Pregunta si desea continuar
    }

    // Método principal del programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Crea un objeto Scanner para leer la entrada del usuario
        procesarFibonacci(scanner); // Inicia el proceso de cálculo de Fibonacci
        scanner.close(); // Cierra el objeto Scanner
    }
}