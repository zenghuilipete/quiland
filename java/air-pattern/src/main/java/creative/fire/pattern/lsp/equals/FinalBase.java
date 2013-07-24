package creative.fire.pattern.lsp.equals;

/**
 * LSP[equals test]
 * Final Class
 * 
 * @author feuyeux@gmail.com
 */
public final class FinalBase {
	final int x;
	final int y;

	public FinalBase(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || !(obj instanceof FinalBase)) {
			return false;
		}

		FinalBase other = (FinalBase) obj;
		return x == other.x && y == other.y;

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
