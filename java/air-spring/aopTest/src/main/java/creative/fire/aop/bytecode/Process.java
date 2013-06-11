package creative.fire.aop.bytecode;

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
		String newMethodName = methodName + "$impl";

		CtMethod method = ctClass.getDeclaredMethod(methodName);
		method.setName(newMethodName);
		CtMethod newMethod = CtNewMethod.copy(method, methodName, ctClass, null);

		StringBuilder body = new StringBuilder();
		body.append("{\nlong start = System.currentTimeMillis();\n");
		//调用原有代码，类似于method();($$)表示所有的参数
		body.append(newMethodName + "($$);\n");
		body.append("System.out.println(\"Call to method " + methodName + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
		body.append("}");
		//替换新方法
		newMethod.setBody(body.toString());
		//增加新方法
		ctClass.addMethod(newMethod);
		Task t = (Task) ctClass.toClass().newInstance();
		t.go();
	}
}
