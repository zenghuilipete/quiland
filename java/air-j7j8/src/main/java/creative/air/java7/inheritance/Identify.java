package creative.air.java7.inheritance;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class Identify {
	public static void main(String[] args) {
		Identify identify = new Identify();
		identify.test();
	}

	private void test() {
		Duck aDuck = new Duck("Donald", "Eider");
		if (aDuck instanceof Animal) {
			System.out.println("It's an Animal");
		}
		if (aDuck instanceof Duck) {
			System.out.println("It's a Duck");
		}

		/* cannot pass the compile phase  */
		/* if (aDuck.getClass() == Animal.class) {
		System.out.println("It's an Animal");
		}     */

		if (aDuck.getClass() == Duck.class) {
			System.out.println("It's a Duck");
		}

		Animal aPet = aDuck;
		((Duck) aPet).layEgg();
	}

	class Animal {
		public Animal(String aType) {
			type = new String(aType);
		}

		@Override
		public String toString() {
			return "This is a " + type;
		}

		// Dummy method to be implemented in the derived classes
		public void sound() {
		}

		private String type;
	}

	class Duck extends Animal {
		public Duck(String aName) {
			super("Duck"); // Call the base constructor
			name = aName; // Supplied name
			breed = "Unknown"; // Default breed value
		}

		public Duck(String aName, String aBreed) {
			super("Duck"); // Call the base constructor
			name = aName; // Supplied name
			breed = aBreed; // Supplied breed
		}

		public void layEgg() {
			System.out.println("Egg laid");
		}

		// Return a String full of a duck's details
		@Override
		public String toString() {
			return super.toString() + "\nIt's " + name + " the " + breed;
		}

		// A quacking method
		@Override
		public void sound() {
			System.out.println("Quack quackquack");
		}

		protected String name; // Duck name
		protected String breed; // Duck breed
	}
}
