package creative.air.datastructure.map;

/**
 * 
 * @author Eric Han feuyeux@gmail.com 16/09/2012
 * @since 0.0.1
 * @version 0.0.1
 */
public class StringInt {

	private String str;
	private int value;

	public StringInt(int value) {
		this.value = value;
	}

	// equals相等时，要求hashCode必须相等
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() == obj.getClass()) {
			StringInt other = (StringInt) obj;
			return this.value == other.value && this.value == other.value;
		}
		return false;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		int hashCode = 31 * value + (str == null ? 0 : str.hashCode());
		return hashCode;
	}

	@Override
	public String toString() {
		return "" + this.value;
	}
}
