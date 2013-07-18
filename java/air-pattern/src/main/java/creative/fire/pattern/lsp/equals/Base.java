package creative.fire.pattern.lsp.equals;

/**
 * LSP[equals test]
 * Base Class
 * 
 * @author feuyeux@gmail.com
 */
public class Base {
	final int x;
	final int y;

	public Base(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;

		Base other = (Base) obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public int hashCode() {
		return x ^ y;
	}

	@Override
	public String toString() {
		return "x=" + x + "y=" + y;
	}
}
