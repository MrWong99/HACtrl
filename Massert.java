package asserters;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Massert<T> {

	private Class<?> toCheck;

	public Massert(Class<T> toCheck) {
		this.toCheck = toCheck;
	}

	/**
	 * @param initArgs
	 *            The objects that should be passed to the constructor.
	 * @return A new object of the given class with the given objects in
	 *         constructor.
	 */
	public Object createTestObject(Object... initArgs) {
		Object res = null;
		Class<?>[] paramTypes = new Class<?>[initArgs.length];
		for(int i = 0; i < initArgs.length; i++) {
			paramTypes[i] = initArgs[i].getClass();
		}
		try {
			res = toCheck.getConstructor(paramTypes).newInstance(initArgs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("Object of class " + toCheck.getName() + " could not be instantiated with "
					+ initArgs.toString() + ":");
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @return A new object of the given class with the default constructor.
	 */
	public Object createTestObject() {
		return createTestObject(new Object[0]);
	}

	/**
	 * Asserts that a Field exists with given name and class type.
	 * 
	 * @param fieldName
	 *            The name of the field.
	 * @param expectedClass
	 *            The class type of the field.
	 */
	public void assertFieldIs(String fieldName, Class<?> expectedClass) {
		try {
			Field fDatum = toCheck.getDeclaredField(fieldName);
			System.out.println("Class " + toCheck.getName() + " has Field '" + fieldName + "' as public? "
					+ fDatum.isAccessible());

			if (fDatum.getType().equals(expectedClass)) {
				System.out.println("Class " + toCheck.getName() + " has Field '" + fieldName + "' of type "
						+ expectedClass.getName() + " as expected.");
			} else {
				System.err.println("Class " + toCheck.getName() + " has Field '" + fieldName + "' of WRONG type "
						+ fDatum.getType().getName());
			}
		} catch (NoSuchFieldException | SecurityException e) {
			System.err.println("Class " + toCheck.getName() + " has Field '" + fieldName + "' not correct.");
			e.printStackTrace();
		}
	}

	/**
	 * Checks if a method behaves like expected for an object with given
	 * arguments.
	 * 
	 * @param toUse
	 *            The object to be used for method invocation.
	 * @param expectedResult
	 *            The expected resulting object.
	 * @param args
	 *            The arguments that should be passed to the method.
	 * @param methodName
	 *            The name of the method.
	 * @param parameterTypes
	 *            The parameter class types that are declared for this method-
	 */
	public void assertMethodIsCorrect(Object toUse, Object expectedResult, Object[] args, String methodName,
			Class<?>... parameterTypes) {
		Method m = null;
		try {
			m = toCheck.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("Method " + methodName + " of Class " + toCheck.getName() + " not found:");
			e.printStackTrace();
		}
		System.out.println("Method " + methodName + " of Class " + toCheck.getName() + " public? " + m.isAccessible());
		m.setAccessible(true);
		try {
			Object res = m.invoke(toUse, args);
			if (res.getClass().getName().equals(expectedResult.getClass().getName())) {
				System.out.println("Return type from Method " + methodName + " of Class " + toCheck.getName()
						+ " was of type " + res.getClass().getName() + " as expected.");
			} else {
				System.err.println("Return type from Method " + methodName + " of Class " + toCheck.getName()
						+ " was of type " + res.getClass().getName() + " which is WRONG!");
			}
			if (res.equals(expectedResult)) {
				System.out.println("Return value from Method " + methodName + " of Class " + toCheck.getName() + " is "
						+ res.toString() + " as expected: " + expectedResult.toString());
			} else {
				System.err.println("Return value from Method " + methodName + " of Class " + toCheck.getName() + " is "
						+ res.toString() + " which is WRONG: " + expectedResult.toString());
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Method " + methodName + " of Class " + toCheck.getName() + " could not be invoked.");
			e.printStackTrace();
		}
	}
}
