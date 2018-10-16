import java.util.HashSet;

import java.util.Iterator;

import java.util.Scanner;

import java.util.Set;



public class TelaTriangulo {



	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Informa o tipo do triangulo");

		System.out.println("Informa o valor do lado a");

		double a = sc.nextDouble();

		System.out.println("Informa o valor do lado b");

		double b = sc.nextDouble();

		System.out.println("Informa o valor do lado c");

		double c = sc.nextDouble();



		Set<String> propriedades = new HashSet<String>();

		double hipotenusa = a;

		if (hipotenusa < b) {

			hipotenusa = b;

		}

		if (hipotenusa < c) {

			hipotenusa = c;

		}

		if ((a + b + c - hipotenusa) > hipotenusa) {

			propriedades.add("Válido");

			if (a == b || b == c || c == a) {

				if (a == b && b == c) {

					propriedades.add("Equilátero");

				} else {

					propriedades.add("Isósceles");

				}

			}

			if (a != b && b != c && c != a) {

				propriedades.add("Escaleno");

			}

			if (((a * a + b * b + c * c) - hipotenusa * hipotenusa) == hipotenusa

					* hipotenusa) {

				propriedades.add("Retângulo");

			}

		} else {

			propriedades.add("Inválido");

		}

		

		System.out.println("Propriedades do triangulo ->");

		for (String valor : propriedades) {

			System.out.println(valor);

		}

	}

}