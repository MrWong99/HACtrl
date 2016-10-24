package asserters;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MassertAdvanced<T> extends Massert<T> {

	private T toUse;

	private Object toReference;

	private ArrayList<Run> toRun;

	public MassertAdvanced(Class<T> clazz) {
		super(clazz);
		toRun = new ArrayList<>();
	}

	public void init(Object toReference, Object... params) {
		if (params == null) {
			params = new Object[0];
		}
		toUse = createTestObject(params);
		this.toReference = toReference;
	}

	public void addRun(String methodName, Object... params) {
		toRun.add(new Run(methodName, params));
	}

	public void doRun() {
		for (Run run : toRun) {
			assertMethodIsCorrect(toUse, getExpectedResult(run.methodName, run.params), run.params, run.methodName);
		}
	}

	public void testFields() {
		for (Field field : toReference.getClass().getDeclaredFields()) {
			assertFieldIs(field.getName(), field.getType());
		}
	}

	private Object getExpectedResult(String methodName, Object... params) {
		Method m = null;
		if (params == null) {
			params = new Object[0];
		}
		Class<?>[] parameterTypes = new Class<?>[params.length];
		for (int i = 0; i < params.length; i++) {
			parameterTypes[i] = params[i].getClass();
		}
		Object res = null;
		try {
			m = toReference.getClass().getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if (m != null) {
			m.setAccessible(true);
			try {
				res = m.invoke(toReference, params);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				if (e instanceof InvocationTargetException) {
					return e.getCause();
				} else {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	private class Run {
		public String methodName;

		public Object[] params;

		public Run(String methodName, Object[] params) {
			super();
			this.methodName = methodName;
			this.params = params;
		}
	}
}
