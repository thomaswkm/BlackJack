package com.mycompany.blackjack;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class BlackJack {

    public static Scanner teclado = new Scanner(System.in);
    public static Random rm = new Random();

    public static String[][] crearBaraja() {

        String[] pintas = new String[]{"Corazón", "Diamante", "Trébol", "Pica"};
        String[] numerosCartas = new String[]{"As", "Dos", "Tres", "Cuatro", "Cinco",
            "Seis", "Siete", "Ocho", "Nueve",
            "Diez", "Jota", "Quina", "Kaiser"};
        return new String[][]{pintas, numerosCartas};
    }

    public static HashMap<String, Integer> crearMapa(String[][] baraja) {

        HashMap<String, Integer> mapa = new HashMap();

        for (int i = 0; i < 10; i++) {

            mapa.put(baraja[1][i], i + 1);

        }

        for (int i = 0; i < 3; i++) {

            mapa.put(baraja[1][i + 10], 10);

        }

        return mapa;
    }

    public static int asociarValores(HashMap<String, Integer> mapa, String carta) {

        String[] numeroCarta = carta.split(" ");
        int valor = mapa.get(numeroCarta[0]);

        return valor;

    }

    public static void repartir(ArrayList<String> manoJugador, ArrayList<String> manoDealer, String[][] baraja) {

        for (int i = 0; i < 2; i++) {

            manoJugador.add(i, baraja[1][rm.nextInt(baraja[1].length)] + " de " + baraja[0][rm.nextInt(baraja[0].length)]);

        }

        for (int i = 0; i < 2; i++) {

            manoDealer.add(i, baraja[1][rm.nextInt(baraja[1].length)] + " de " + baraja[0][rm.nextInt(baraja[0].length)]);

        }

    }

    public static void mostrarCartas(ArrayList<String> mano) {

        System.out.print("Las cartas son: ");
        for (int i = 0; i < mano.size(); i++) {

            System.out.print(mano.get(i));
            if (i + 1 < mano.size()) {
                System.out.print(", ");
            }
        }
        System.out.println("");
    }

    public static void pedirCarta(String[][] baraja, ArrayList<String> manoJugador) {

        System.out.println("Agregando carta...");

        manoJugador.add(baraja[1][rm.nextInt(baraja[1].length)] + " de " + baraja[0][rm.nextInt(baraja[0].length)]);

    }

    public static int sumaCartas(ArrayList<String> mano, HashMap<String, Integer> mapa) {

        int suma = 0;

        for (String carta : mano) {

            suma += asociarValores(mapa, carta);

        }

        System.out.println("Tus cartas suman: " + suma);

        return suma;
    }

    public static int verificacion(ArrayList<String> mano, HashMap<String, Integer> mapa) {

        int suma = sumaCartas(mano, mapa);

        if (suma > 21) {

            System.out.println("Te pasaste de 21, tus cartas suman: " + suma);
            return suma;
        } else if (suma == 21) {
            System.out.println("Blackjack! : " + suma);
            return suma;
        } else {
            return suma;
        }

    }

    public static void bajarse(HashMap<String, Integer> mapa, ArrayList<String> manoJugador, ArrayList<String> manoDealer) {

        if (verificacion(manoJugador, mapa) > verificacion(manoDealer, mapa)) {

            System.out.println("Ganaste! ");

        } else {
            System.out.println("Perdiste! ");
        }

    }

    public static void turno(ArrayList<String> manoJugador, ArrayList<String> manoDealer, HashMap<String, Integer> mapa, String[][] baraja) {
        int respuestaUno = 1;
        int respuestaDos = 0;
        int suma = 0;

        do {
            System.out.println("¿Quieres pedir una carta? No(0) Si(1)");
            respuestaUno = teclado.nextInt();

            if (respuestaUno == 1) {
                pedirCarta(baraja, manoJugador);
                suma = +verificacion(manoJugador, mapa);
            }

            if (suma > 21) {
                System.out.println("Perdiste! ");
                break;
            };

        } while (respuestaUno == 1);

        if (suma < 21) {

            System.out.println("¿Quieres bajarte? No(0) Si(1)");
            respuestaDos = +teclado.nextInt();
            if (respuestaDos == 1) {

                bajarse(mapa, manoJugador, manoDealer);
            } else {

                turno(manoJugador, manoDealer, mapa, baraja);
            }

        }
    }

    public static void main(String[] args) {

        ArrayList<String> manoJugador = new ArrayList<>();
        ArrayList<String> manoDealer = new ArrayList<>();
        HashMap<String, Integer> mapa = new HashMap();
        String[][] baraja = crearBaraja();
        repartir(manoJugador, manoDealer, baraja);
        mapa = crearMapa(baraja);

        turno(manoJugador, manoDealer, mapa, baraja);

    }

}
