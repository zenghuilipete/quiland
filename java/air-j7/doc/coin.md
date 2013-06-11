## JSR334 Coin##


### 1. switch ###
- Character(char)
- Byte(byte)
- Short(short)
- Integer(int)
- enum
- **String**

### 2. literal ###
	int x2 = 0B1100_1111;

### 3. try-catch-finally ###
	try {
		method();
	} catch (AirLogicException  | AirStreamException e) {
		e.getLocalizedMessage();
	}

### 4. try-catch-resources ###
	try (BufferedReader reader=new BufferedReader(new FileReader("me.xml"))){
		String line;
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
	} catch (Exception e) {
		System.err.println(e);
	}

### 5. @SafeVarargs ###

	@SafeVarargs
	public final <T> T useVarargs(T... args) {
		return args.length > 0 ? args[0] : null;
	}

### 6. Generic Types ###
	Map<HashO, String> map = new HashMap<>();