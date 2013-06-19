package creative.fire.aop.bytecode.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import creative.fire.aop.Task;

public class Process {
	public static void main(String[] args) throws Exception {
		final String taskClassName = "creative.fire.aop.Task";
		CtClass ctClass = ClassPool.getDefault().get(taskClassName);
		String methodName = "go";

		//  get the method information (throws exception if method with
		//  given name is not declared directly by this class, returns
		//  arbitrary choice if more than one with the given name)
		CtMethod method = ctClass.getDeclaredMethod(methodName);

		//  rename old method to synthetic name, then duplicate the
		//  method with original name for use as interceptor
		String newMethodName = methodName + "$impl";
		method.setName(newMethodName);
		CtMethod newMethod = CtNewMethod.copy(method, methodName, ctClass, null);

		//  start the body text generation by saving the start time
		//  to a local variable, then call the timed method; the
		//  actual code generated needs to depend on whether the
		//  timed method returns a value
		String type = method.getReturnType().getName();
		StringBuffer body = new StringBuffer();
		body.append("{\nlong start = System.currentTimeMillis();\n");
		if (!"void".equals(type)) {
			body.append(type + " result = ");
		}
		body.append(newMethodName + "($$);\n");

		//  finish body text generation with call to print the timing
		//  information, and return saved value (if not void)
		body.append("System.out.println(\"Call to method " + methodName + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
		if (!"void".equals(type)) {
			body.append("return result;\n");
		}
		body.append("}");

		//  replace the body of the interceptor method with generated
		//  code block and add it to class
		newMethod.setBody(body.toString());
		ctClass.addMethod(newMethod);
		Task t = (Task) ctClass.toClass().newInstance();
		t.go();
	}
}
