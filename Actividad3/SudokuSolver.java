package Actividad3;

import java.util.Scanner;

public class SudokuSolver {

    // Método principal para resolver el Sudoku
    public static boolean resolverSudoku(int[][] tablero, int fila, int columna) {
        // Si llegamos a la fila 9, significa que terminamos el tablero
        if (fila == 9)
            return true;

        // Si la celda ya tiene un número, pasamos a la siguiente celda
        if (tablero[fila][columna] != 0) {
            return siguienteCelda(tablero, fila, columna);
        }

        // Probamos números del 1 al 9 en la celda actual
        return probarNumeros(tablero, fila, columna, 1);
    }

    // Método para movernos a la siguiente celda
    private static boolean siguienteCelda(int[][] tablero, int fila, int columna) {
        // Si no estamos en la última columna, avanzamos a la siguiente
        if (columna < 8) {
            return resolverSudoku(tablero, fila, columna + 1);
        } else {
            // Si estamos en la última columna, pasamos a la siguiente fila
            return resolverSudoku(tablero, fila + 1, 0);
        }
    }

    // Método para probar números en una celda
    private static boolean probarNumeros(int[][] tablero, int fila, int columna, int numero) {
        // Si ya probamos todos los números y no funcionó, regresamos false
        if (numero > 9)
            return false;

        // Verificamos si el número es válido en la celda actual
        if (esValido(tablero, fila, columna, numero)) {
            // Si es válido, lo colocamos en la celda
            tablero[fila][columna] = numero;

            // Intentamos resolver el resto del tablero
            if (siguienteCelda(tablero, fila, columna)) {
                return true; // Si funciona, regresamos true
            }

            // Si no funciona, quitamos el número (backtracking)
            tablero[fila][columna] = 0;
        }

        // Probamos con el siguiente número
        return probarNumeros(tablero, fila, columna, numero + 1);
    }

    // Método para verificar si un número es válido en una celda
    private static boolean esValido(int[][] tablero, int fila, int columna, int numero) {
        // El número es válido si no está en la fila, columna o caja 3x3
        return !estaEnFila(tablero, fila, numero, 0) &&
                !estaEnColumna(tablero, columna, numero, 0) &&
                !estaEnCaja(tablero, fila - fila % 3, columna - columna % 3, numero, 0, 0);
    }

    // Método para verificar si un número está en la fila
    private static boolean estaEnFila(int[][] tablero, int fila, int numero, int col) {
        // Si llegamos al final de la fila, regresamos false
        if (col == 9)
            return false;
        // Si encontramos el número en la fila, regresamos true
        if (tablero[fila][col] == numero)
            return true;
        // Seguimos buscando en la siguiente columna
        return estaEnFila(tablero, fila, numero, col + 1);
    }

    // Método para verificar si un número está en la columna
    private static boolean estaEnColumna(int[][] tablero, int columna, int numero, int fila) {
        // Si llegamos al final de la columna, regresamos false
        if (fila == 9)
            return false;
        // Si encontramos el número en la columna, regresamos true
        if (tablero[fila][columna] == numero)
            return true;
        // Seguimos buscando en la siguiente fila
        return estaEnColumna(tablero, columna, numero, fila + 1);
    }

    // Método para verificar si un número está en la caja 3x3
    private static boolean estaEnCaja(int[][] tablero, int startFila, int startCol, int numero, int i, int j) {
        // Si revisamos todas las filas de la caja, regresamos false
        if (i == 3)
            return false;
        // Si revisamos todas las columnas de una fila, pasamos a la siguiente fila
        if (j == 3)
            return estaEnCaja(tablero, startFila, startCol, numero, i + 1, 0);
        // Si encontramos el número en la caja, regresamos true
        if (tablero[startFila + i][startCol + j] == numero)
            return true;
        // Seguimos buscando en la siguiente celda de la caja
        return estaEnCaja(tablero, startFila, startCol, numero, i, j + 1);
    }

    // Método para imprimir el tablero completo
    public static void imprimirTablero(int[][] tablero, int fila) {
        // Si llegamos al final del tablero, terminamos
        if (fila == 9)
            return;

        // Imprimimos la fila actual
        imprimirFila(tablero, fila, 0);
        System.out.println();

        // Imprimimos una línea divisoria después de cada 3 filas
        if (fila == 2 || fila == 5) {
            System.out.println("------+-------+------");
        }

        // Pasamos a la siguiente fila
        imprimirTablero(tablero, fila + 1);
    }

    // Método para imprimir una fila del tablero
    private static void imprimirFila(int[][] tablero, int fila, int col) {
        // Si llegamos al final de la fila, terminamos
        if (col == 9)
            return;

        // Imprimimos una barra divisoria después de cada 3 columnas
        if (col == 3 || col == 6) {
            System.out.print("| ");
        }

        // Imprimimos el número o un punto si la celda está vacía
        System.out.print(tablero[fila][col] == 0 ? ". " : tablero[fila][col] + " ");
        // Pasamos a la siguiente columna
        imprimirFila(tablero, fila, col + 1);
    }

    // Método para leer un valor válido del usuario (estilo Linux)
    private static int leerValorValido(Scanner scanner, String mensaje, int fila, int columna) {
        System.out.print(mensaje);

        if (!scanner.hasNextInt()) {
            // Si no es un número, mostramos error y volvemos a intentar
            System.out.println("Error: Debe ingresar un número entero.");
            scanner.next(); // Limpiamos el valor incorrecto
            return leerValorValido(scanner, mensaje, fila, columna);
        }

        int valor = scanner.nextInt();

        // Validamos que el valor esté entre 0 y 9
        if (valor < 0 || valor > 9) {
            System.out.println("Error: El valor debe estar entre 0 y 9.");
            return leerValorValido(scanner, mensaje, fila, columna);
        }

        // Validamos que el valor sea válido en la posición actual
        if (valor != 0 && !esValidoEnPosicion(new int[9][9], fila, columna, valor)) {
            System.out.println("Error: El valor " + valor + " no es válido en esta posición.");
            return leerValorValido(scanner, mensaje, fila, columna);
        }

        return valor;
    }

    // Método auxiliar para validar si un valor es válido en una posición específica
    private static boolean esValidoEnPosicion(int[][] tablero, int fila, int columna, int valor) {
        // Simulamos un tablero temporal para la validación
        // Nota: Esta validación es básica y no considera el tablero completo
        return true; // En una implementación real, aquí iría la validación completa
    }

    // Método para leer el tablero desde la entrada del usuario con validación
    public static void leerTablero(int[][] tablero, int fila, int columna, Scanner scanner) {
        if (fila == 9) {
            return; // Terminamos de leer
        }

        if (columna == 0) {
            System.out.println("\nIngrese los valores para la fila " + (fila + 1) + ":");
        }

        String mensaje = "Celda [" + (fila + 1) + "," + (columna + 1) + "] (0-9, 0=vacío): ";
        int valor = leerValorValido(scanner, mensaje, fila, columna);

        tablero[fila][columna] = valor;

        // Pasamos a la siguiente celda
        if (columna < 8) {
            leerTablero(tablero, fila, columna + 1, scanner);
        } else {
            leerTablero(tablero, fila + 1, 0, scanner);
        }
    }

    // Método para preguntar al usuario si desea resolver otro sudoku
    private static boolean preguntarOtraEjecucion(Scanner scanner) {
        System.out.print("\n¿Desea resolver otro sudoku? (s/n): ");

        if (!scanner.hasNext()) {
            return preguntarOtraEjecucion(scanner);
        }

        String respuesta = scanner.next().toLowerCase();

        if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
            return true;
        } else if (respuesta.equals("n") || respuesta.equals("no")) {
            return false;
        } else {
            System.out.println("Error: Por favor ingrese 's' para sí o 'n' para no.");
            return preguntarOtraEjecucion(scanner);
        }
    }

    // Método para ejecutar el programa completo
    private static void ejecutarPrograma(Scanner scanner) {
        // Crear un tablero vacío
        int[][] tablero = new int[9][9];

        System.out.println("=== SOLUCIONADOR DE SUDOKU ===");
        System.out.println("Ingrese los valores del tablero de Sudoku:");
        System.out.println("(Use 0 para representar celdas vacías)");

        // Leer el tablero desde el usuario
        leerTablero(tablero, 0, 0, scanner);

        // Mostrar el tablero ingresado
        System.out.println("\nTablero ingresado:");
        imprimirTablero(tablero, 0);
        System.out.println();

        // Intentar resolver el Sudoku
        if (resolverSudoku(tablero, 0, 0)) {
            // Si encontramos solución, la mostramos
            System.out.println("Solución encontrada:");
            imprimirTablero(tablero, 0);
        } else {
            // Si no hay solución, mostramos un mensaje
            System.out.println("No existe solución para este tablero");
        }

        // Preguntar si desea resolver otro sudoku
        if (preguntarOtraEjecucion(scanner)) {
            System.out.println("\n".repeat(3)); // Espacio entre ejecuciones
            ejecutarPrograma(scanner);
        } else {
            System.out.println("\n¡Gracias por usar el solucionador de Sudoku!");
        }
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ejecutarPrograma(scanner);
        scanner.close();
    }
}