package creative.air.java7.safeVarargs;

public class VariableLengthArgument {

	@SafeVarargs
	public final <T> T useVarargs(T... args) {
		return args.length > 0 ? args[0] : null;
	}
}
